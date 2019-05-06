package org.orcid.core.manager.v3;

import org.orcid.jaxb.model.v3.release.notification.permission.NotificationPermission;

/**
 * 
 * @author Will Simpson
 * 
 */
public interface NotificationValidationManager {

    void validateNotificationPermission(NotificationPermission notification);

}
