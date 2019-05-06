/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinax.docker;

import org.ballerinalang.compiler.plugins.AbstractCompilerPlugin;
import org.ballerinalang.compiler.plugins.SupportedAnnotationPackages;
import org.ballerinalang.model.elements.PackageID;
import org.ballerinalang.model.tree.AnnotationAttachmentNode;
import org.ballerinalang.model.tree.PackageNode;
import org.ballerinalang.model.tree.ServiceNode;
import org.ballerinalang.model.tree.SimpleVariableNode;
import org.ballerinalang.util.diagnostic.Diagnostic;
import org.ballerinalang.util.diagnostic.DiagnosticLog;
import org.ballerinax.docker.exceptions.DockerPluginException;
import org.ballerinax.docker.models.DockerContext;
import org.ballerinax.docker.models.DockerDataHolder;
import org.ballerinax.docker.utils.DockerPluginUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.ballerinalang.compiler.tree.BLangPackage;
import org.wso2.ballerinalang.compiler.tree.BLangService;
import org.wso2.ballerinalang.compiler.tree.BLangSimpleVariable;
import org.wso2.ballerinalang.compiler.tree.expressions.BLangExpression;
import org.wso2.ballerinalang.compiler.tree.expressions.BLangTypeInit;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.ballerinax.docker.generator.DockerGenConstants.ARTIFACT_DIRECTORY;
import static org.ballerinax.docker.utils.DockerPluginUtils.extractBalxName;
import static org.ballerinax.docker.utils.DockerPluginUtils.printError;

/**
 * Compiler plugin to generate docker artifacts.
 */
@SupportedAnnotationPackages(
        value = "ballerinax/docker:0.0.0"
)
public class DockerPlugin extends AbstractCompilerPlugin {
    private static final Logger pluginLog = LoggerFactory.getLogger(DockerPlugin.class);
    private DockerAnnotationProcessor dockerAnnotationProcessor;
    private DiagnosticLog dlog;

    @Override
    public void init(DiagnosticLog diagnosticLog) {
        this.dlog = diagnosticLog;
        dockerAnnotationProcessor = new DockerAnnotationProcessor();
    }

    @Override
    public void process(PackageNode packageNode) {
        BLangPackage bPackage = (BLangPackage) packageNode;
        String pkgID = bPackage.packageID.toString();
        DockerContext.getInstance().addDataHolder(pkgID);
    }

    @Override
    public void process(ServiceNode serviceNode, List<AnnotationAttachmentNode> annotations) {
        DockerDataHolder dataHolder = DockerContext.getInstance().getDataHolder();
        dataHolder.setCanProcess(true);
        try {
            for (AnnotationAttachmentNode attachmentNode : annotations) {
                DockerAnnotation dockerAnnotation = DockerAnnotation.valueOf(attachmentNode.getAnnotationName()
                        .getValue());
                switch (dockerAnnotation) {
                    case Config:
                        dataHolder.setDockerModel(
                                dockerAnnotationProcessor.processConfigAnnotation(attachmentNode));
                        break;
                    case CopyFiles:
                        dataHolder.addExternalFiles(
                                dockerAnnotationProcessor.processCopyFileAnnotation(attachmentNode));
                        break;
                    default:
                        break;
                }
            }
            BLangService bService = (BLangService) serviceNode;
            for (BLangExpression attachedExpr : bService.getAttachedExprs()) {
                if (attachedExpr instanceof BLangTypeInit) {
                    BLangTypeInit bListener = (BLangTypeInit) attachedExpr;
                    try {
                        dataHolder.addPort(Integer.parseInt(bListener.argsExpr.get(0).toString()));
                    } catch (NumberFormatException e) {
                        throw new DockerPluginException("Unable to parse port of the listener: " +
                                                        bListener.argsExpr.get(0).toString());
                    }
                }
            }
        } catch (DockerPluginException e) {
            dlog.logDiagnostic(Diagnostic.Kind.ERROR, serviceNode.getPosition(), e.getMessage());
        }
    }

    @Override
    public void process(SimpleVariableNode variableNode, List<AnnotationAttachmentNode> annotations) {
        DockerDataHolder dataHolder = DockerContext.getInstance().getDataHolder();
        dataHolder.setCanProcess(true);
        try {
            for (AnnotationAttachmentNode attachmentNode : annotations) {
                DockerAnnotation dockerAnnotation = DockerAnnotation.valueOf(attachmentNode.getAnnotationName()
                        .getValue());
                switch (dockerAnnotation) {
                    case Config:
                        dataHolder.setDockerModel(
                                dockerAnnotationProcessor.processConfigAnnotation(attachmentNode));
                        break;
                    case CopyFiles:
                        dataHolder.addExternalFiles(
                                dockerAnnotationProcessor.processCopyFileAnnotation(attachmentNode));
                        break;
                    case Expose:
                        BLangTypeInit bListener = (BLangTypeInit) ((BLangSimpleVariable) variableNode).expr;
                        try {
                            dataHolder.addPort(Integer.parseInt(bListener.argsExpr.get(0).toString()));
                        } catch (NumberFormatException e) {
                            throw new DockerPluginException("Unable to parse port of the service: " +
                                                            bListener.argsExpr.get(0).toString());
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (DockerPluginException e) {
            dlog.logDiagnostic(Diagnostic.Kind.ERROR, variableNode.getPosition(), e.getMessage());
        }
    }

    @Override
    public void codeGenerated(PackageID packageID, Path binaryPath) {
        DockerContext.getInstance().setCurrentPackage(packageID.toString());
        if (DockerContext.getInstance().getDataHolder().isCanProcess()) {
            String filePath = binaryPath.toAbsolutePath().toString();
            String userDir = new File(filePath).getParentFile().getAbsolutePath();
            DockerAnnotationProcessor dockerAnnotationProcessor = new DockerAnnotationProcessor();
            Path targetPath = Paths.get(userDir).resolve(ARTIFACT_DIRECTORY);
            if (userDir.endsWith("target")) {
                //Compiling package therefore append balx file name to docker artifact dir path
                targetPath = Paths.get(userDir).resolve(extractBalxName(filePath));
            }
            try {
                DockerPluginUtils.deleteDirectory(targetPath);
                dockerAnnotationProcessor.processDockerModel(DockerContext.getInstance().getDataHolder(), filePath,
                        targetPath);
            } catch (DockerPluginException e) {
                printError(e.getMessage());
                pluginLog.error(e.getMessage(), e);
                try {
                    DockerPluginUtils.deleteDirectory(targetPath);
                } catch (DockerPluginException ignored) {
                    //ignored
                }
            }
        }
    }

    private enum DockerAnnotation {
        Config,
        CopyFiles,
        Expose
    }
}
