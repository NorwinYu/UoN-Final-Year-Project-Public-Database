package org.pmiops.workbench.api;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.pmiops.workbench.cohortreview.AnnotationQueryBuilder;
import org.pmiops.workbench.db.dao.CohortAnnotationDefinitionDao;
import org.pmiops.workbench.db.dao.CohortDao;
import org.pmiops.workbench.db.dao.WorkspaceService;
import org.pmiops.workbench.db.model.Cohort;
import org.pmiops.workbench.db.model.CohortAnnotationEnumValue;
import org.pmiops.workbench.db.model.Workspace;
import org.pmiops.workbench.exceptions.BadRequestException;
import org.pmiops.workbench.exceptions.ConflictException;
import org.pmiops.workbench.exceptions.NotFoundException;
import org.pmiops.workbench.model.CohortAnnotationDefinition;
import org.pmiops.workbench.model.CohortAnnotationDefinitionListResponse;
import org.pmiops.workbench.model.EmptyResponse;
import org.pmiops.workbench.model.ModifyCohortAnnotationDefinitionRequest;
import org.pmiops.workbench.model.WorkspaceAccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class CohortAnnotationDefinitionController implements CohortAnnotationDefinitionApiDelegate {

    private CohortAnnotationDefinitionDao cohortAnnotationDefinitionDao;
    private CohortDao cohortDao;
    private WorkspaceService workspaceService;

    /**
     * Converter function from backend representation (used with Hibernate) to
     * client representation (generated by Swagger).
     */
    private static final Function<org.pmiops.workbench.db.model.CohortAnnotationDefinition, CohortAnnotationDefinition>
            TO_CLIENT_COHORT_ANNOTATION_DEFINITION =
            new Function<org.pmiops.workbench.db.model.CohortAnnotationDefinition, CohortAnnotationDefinition>() {
                @Override
                public CohortAnnotationDefinition apply(org.pmiops.workbench.db.model.CohortAnnotationDefinition cohortAnnotationDefinition) {
                    List<String> enumValues = cohortAnnotationDefinition.getEnumValues() == null ? null :
                            cohortAnnotationDefinition.getEnumValues().stream().map(CohortAnnotationEnumValue::getName).collect(Collectors.toList());
                    return new CohortAnnotationDefinition()
                            .columnName(cohortAnnotationDefinition.getColumnName())
                            .cohortId(cohortAnnotationDefinition.getCohortId())
                            .annotationType(cohortAnnotationDefinition.getAnnotationTypeEnum())
                            .cohortAnnotationDefinitionId(cohortAnnotationDefinition.getCohortAnnotationDefinitionId())
                            .enumValues(enumValues);
                }
            };

    private static final Function<CohortAnnotationDefinition, org.pmiops.workbench.db.model.CohortAnnotationDefinition>
            FROM_CLIENT_COHORT_ANNOTATION_DEFINITION =
            new Function<CohortAnnotationDefinition, org.pmiops.workbench.db.model.CohortAnnotationDefinition>() {
                @Override
                public org.pmiops.workbench.db.model.CohortAnnotationDefinition apply(CohortAnnotationDefinition cohortAnnotationDefinition) {
                    org.pmiops.workbench.db.model.CohortAnnotationDefinition dbCohortAnnotationDefinition =
                            new org.pmiops.workbench.db.model.CohortAnnotationDefinition()
                                    .cohortId(cohortAnnotationDefinition.getCohortId())
                                    .columnName(cohortAnnotationDefinition.getColumnName())
                                    .annotationTypeEnum(cohortAnnotationDefinition.getAnnotationType());
                    List<CohortAnnotationEnumValue> enumValuesList = (cohortAnnotationDefinition.getEnumValues() == null) ? new ArrayList<>() :
                            IntStream.range(0, cohortAnnotationDefinition.getEnumValues().size())
                                    .mapToObj(i -> new CohortAnnotationEnumValue()
                                            .name(cohortAnnotationDefinition.getEnumValues().get(i)).order(i)
                                            .cohortAnnotationDefinition(dbCohortAnnotationDefinition))
                                    .collect(Collectors.toList());
                    for (CohortAnnotationEnumValue cohortAnnotationEnumValue : enumValuesList) {
                        dbCohortAnnotationDefinition.getEnumValues().add(cohortAnnotationEnumValue);
                    }
                    return dbCohortAnnotationDefinition;
                }
            };

    @Autowired
    CohortAnnotationDefinitionController(CohortAnnotationDefinitionDao cohortAnnotationDefinitionDao,
                                         CohortDao cohortDao,
                                         WorkspaceService workspaceService) {
        this.cohortAnnotationDefinitionDao = cohortAnnotationDefinitionDao;
        this.cohortDao = cohortDao;
        this.workspaceService = workspaceService;
    }

    private void validateColumnName(String columnName) {
        if (AnnotationQueryBuilder.RESERVED_COLUMNS.contains(columnName)) {
            throw new BadRequestException("Annotations are not allowed to be named " + columnName);
        } else if (columnName.toUpperCase().contains(AnnotationQueryBuilder.DESCENDING_PREFIX)) {
            throw new BadRequestException("Annotations are not allowed to contain " +
              AnnotationQueryBuilder.DESCENDING_PREFIX);
        }
    }

    @Override
    public ResponseEntity<CohortAnnotationDefinition> createCohortAnnotationDefinition(String workspaceNamespace,
                                                                                       String workspaceId,
                                                                                       Long cohortId,
                                                                                       CohortAnnotationDefinition request) {
        // This also enforces registered auth domain.
        workspaceService.enforceWorkspaceAccessLevel(workspaceNamespace, workspaceId, WorkspaceAccessLevel.WRITER);

        Cohort cohort = findCohort(cohortId);
        //this validates that the user is in the proper workspace
        validateMatchingWorkspace(workspaceNamespace, workspaceId, cohort.getWorkspaceId());
        request.setCohortId(cohortId);

        org.pmiops.workbench.db.model.CohortAnnotationDefinition cohortAnnotationDefinition =
                FROM_CLIENT_COHORT_ANNOTATION_DEFINITION.apply(request);
        validateColumnName(cohortAnnotationDefinition.getColumnName());

        org.pmiops.workbench.db.model.CohortAnnotationDefinition existingDefinition =
                cohortAnnotationDefinitionDao.findByCohortIdAndColumnName(
                        cohortId,
                        request.getColumnName());

        if (existingDefinition != null) {
            throw new ConflictException(String.format("Conflict: Cohort Annotation Definition name exists for: %s",
                    request.getColumnName()));
        }
        try {
            cohortAnnotationDefinition = cohortAnnotationDefinitionDao.save(cohortAnnotationDefinition);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Bad Request: " + ExceptionUtils.getRootCause(e).getMessage());
        }

        return ResponseEntity.ok(TO_CLIENT_COHORT_ANNOTATION_DEFINITION.apply(cohortAnnotationDefinition));
    }

    @Override
    public ResponseEntity<EmptyResponse> deleteCohortAnnotationDefinition(String workspaceNamespace,
                                                                          String workspaceId,
                                                                          Long cohortId,
                                                                          Long annotationDefinitionId) {
        // This also enforces registered auth domain.
        workspaceService.enforceWorkspaceAccessLevel(workspaceNamespace, workspaceId, WorkspaceAccessLevel.WRITER);

        Cohort cohort = findCohort(cohortId);
        //this validates that the user is in the proper workspace
        validateMatchingWorkspace(workspaceNamespace, workspaceId, cohort.getWorkspaceId());

        findCohortAnnotationDefinition(cohortId, annotationDefinitionId);

        cohortAnnotationDefinitionDao.delete(annotationDefinitionId);

        return ResponseEntity.ok(new EmptyResponse());
    }

    @Override
    public ResponseEntity<CohortAnnotationDefinition> getCohortAnnotationDefinition(String workspaceNamespace,
                                                                                    String workspaceId,
                                                                                    Long cohortId,
                                                                                    Long annotationDefinitionId) {
        // This also enforces registered auth domain.
        workspaceService.enforceWorkspaceAccessLevel(workspaceNamespace, workspaceId, WorkspaceAccessLevel.READER);

        Cohort cohort = findCohort(cohortId);
        //this validates that the user is in the proper workspace
        validateMatchingWorkspace(workspaceNamespace, workspaceId, cohort.getWorkspaceId());

        org.pmiops.workbench.db.model.CohortAnnotationDefinition cohortAnnotationDefinition =
                findCohortAnnotationDefinition(cohortId, annotationDefinitionId);

        return ResponseEntity.ok(TO_CLIENT_COHORT_ANNOTATION_DEFINITION.apply(cohortAnnotationDefinition));
    }

    @Override
    public ResponseEntity<CohortAnnotationDefinitionListResponse> getCohortAnnotationDefinitions(String workspaceNamespace,
                                                                                                 String workspaceId,
                                                                                                 Long cohortId) {
        // This also enforces registered auth domain.
        workspaceService.enforceWorkspaceAccessLevel(workspaceNamespace, workspaceId, WorkspaceAccessLevel.READER);

        Cohort cohort = findCohort(cohortId);
        //this validates that the user is in the proper workspace
        validateMatchingWorkspace(workspaceNamespace, workspaceId, cohort.getWorkspaceId());

        List<org.pmiops.workbench.db.model.CohortAnnotationDefinition> dbList =
                cohortAnnotationDefinitionDao.findByCohortId(cohortId);

        CohortAnnotationDefinitionListResponse responseList = new CohortAnnotationDefinitionListResponse();
        responseList.setItems(dbList.stream().map(TO_CLIENT_COHORT_ANNOTATION_DEFINITION).collect(Collectors.toList()));

        return ResponseEntity.ok(responseList);
    }

    @Override
    public ResponseEntity<CohortAnnotationDefinition>
    updateCohortAnnotationDefinition(String workspaceNamespace,
                                     String workspaceId,
                                     Long cohortId,
                                     Long annotationDefinitionId,
                                     ModifyCohortAnnotationDefinitionRequest modifyCohortAnnotationDefinitionRequest) {
        // This also enforces registered auth domain.
        workspaceService.enforceWorkspaceAccessLevel(workspaceNamespace, workspaceId, WorkspaceAccessLevel.WRITER);

        String columnName = modifyCohortAnnotationDefinitionRequest.getColumnName();
        validateColumnName(columnName);
        Cohort cohort = findCohort(cohortId);
        //this validates that the user is in the proper workspace
        validateMatchingWorkspace(workspaceNamespace, workspaceId, cohort.getWorkspaceId());

        org.pmiops.workbench.db.model.CohortAnnotationDefinition cohortAnnotationDefinition =
                findCohortAnnotationDefinition(cohortId, annotationDefinitionId);

        org.pmiops.workbench.db.model.CohortAnnotationDefinition existingDefinition =
                cohortAnnotationDefinitionDao.findByCohortIdAndColumnName(
                        cohortId,
                        columnName);

        if (existingDefinition != null) {
            throw new ConflictException(String.format("Conflict: Cohort Annotation Definition name exists for: %s",
                    columnName));
        }

        cohortAnnotationDefinition.columnName(columnName);
        try {
            cohortAnnotationDefinition = cohortAnnotationDefinitionDao.save(cohortAnnotationDefinition);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Bad Request: " + ExceptionUtils.getRootCause(e).getMessage());
        }

        return ResponseEntity.ok(TO_CLIENT_COHORT_ANNOTATION_DEFINITION.apply(cohortAnnotationDefinition));
    }

    private org.pmiops.workbench.db.model.CohortAnnotationDefinition findCohortAnnotationDefinition(Long cohortId, Long annotationDefinitionId) {
        org.pmiops.workbench.db.model.CohortAnnotationDefinition cohortAnnotationDefinition =
                cohortAnnotationDefinitionDao.findByCohortIdAndCohortAnnotationDefinitionId(cohortId, annotationDefinitionId);

        if (cohortAnnotationDefinition == null) {
            throw new NotFoundException(
                    String.format("Not Found: No Cohort Annotation Definition exists for annotationDefinitionId: %s",
                            annotationDefinitionId));
        }
        return cohortAnnotationDefinition;
    }

    private Cohort findCohort(long cohortId) {
        Cohort cohort = cohortDao.findOne(cohortId);
        if (cohort == null) {
            throw new NotFoundException(
                    String.format("Not Found: No Cohort exists for cohortId: %s", cohortId));
        }
        return cohort;
    }

    private void validateMatchingWorkspace(String workspaceNamespace, String workspaceName, long workspaceId) {
        Workspace workspace = workspaceService.getRequired(workspaceNamespace, workspaceName);
        if (workspace.getWorkspaceId() != workspaceId) {
            throw new NotFoundException(
                    String.format("Not Found: No workspace matching workspaceNamespace: %s, workspaceId: %s",
                            workspaceNamespace, workspaceName));
        }
    }
}