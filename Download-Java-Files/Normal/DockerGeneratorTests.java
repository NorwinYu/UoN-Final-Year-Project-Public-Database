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

package org.ballerinax.kubernetes.handlers;

import org.ballerinax.docker.generator.models.DockerModel;
import org.ballerinax.kubernetes.exceptions.KubernetesPluginException;
import org.ballerinax.kubernetes.models.KubernetesContext;
import org.ballerinax.kubernetes.models.KubernetesDataHolder;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import static org.ballerinax.docker.generator.DockerGenConstants.BALLERINA_BASE_IMAGE;

/**
 * Docker generator tests.
 */
public class DockerGeneratorTests {

    @Test
    public void testDockerGenerate() throws KubernetesPluginException {
        DockerModel dockerModel = new DockerModel();
        Set<Integer> ports = new HashSet<>();
        ports.add(9090);
        ports.add(9091);
        ports.add(9092);
        dockerModel.setPorts(ports);
        dockerModel.setService(true);
        dockerModel.setBalxFileName("hello.balx");
        dockerModel.setEnableDebug(true);
        dockerModel.setBaseImage(BALLERINA_BASE_IMAGE + ":latest");
        dockerModel.setDebugPort(5005);
        dockerModel.setBuildImage(false);
        KubernetesContext context = KubernetesContext.getInstance();
        KubernetesDataHolder dataHolder = context.getDataHolder();
        Path tempArtifactHolder = dataHolder.getArtifactOutputPath();
        dataHolder.setArtifactOutputPath(tempArtifactHolder.resolve("hello"));
        dataHolder.setDockerModel(dockerModel);
        new DockerHandler().createArtifacts();
        File dockerfile = new File("target/kubernetes/hello/docker/");
        dockerfile.mkdirs();
        dockerfile = new File("target/kubernetes/hello/docker/Dockerfile");
        dataHolder.setArtifactOutputPath(tempArtifactHolder);
        Assert.assertTrue(dockerfile.exists());
        dockerfile.deleteOnExit();
    }
}