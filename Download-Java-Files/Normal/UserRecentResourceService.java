package org.pmiops.workbench.db.dao;

import org.pmiops.workbench.db.model.UserRecentResource;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public interface UserRecentResourceService {

  UserRecentResource updateNotebookEntry(long workspaceId, long userId, String notebookNameWithPath, Timestamp lastAccessDateTime);

  void updateCohortEntry(long workspaceId, long userId, long cohortId, Timestamp lastAccessDateTime);

  void updateConceptSetEntry(long workspaceId, long userId, long conceptSetId, Timestamp lastAccessDateTime);

  void deleteNotebookEntry(long workspaceId, long userId, String notebookName);

  List<UserRecentResource> findAllResourcesByUser(long userId);
}

