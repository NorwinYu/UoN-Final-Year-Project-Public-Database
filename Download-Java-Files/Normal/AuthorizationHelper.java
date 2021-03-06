package com.bakdata.conquery.models.auth;

import java.util.EnumSet;

import com.bakdata.conquery.io.xodus.MasterMetaStorage;
import com.bakdata.conquery.models.auth.permissions.Ability;
import com.bakdata.conquery.models.auth.permissions.ConqueryPermission;
import com.bakdata.conquery.models.auth.permissions.DatasetPermission;
import com.bakdata.conquery.models.auth.permissions.QueryPermission;
import com.bakdata.conquery.models.auth.subjects.PermissionOwner;
import com.bakdata.conquery.models.auth.subjects.User;
import com.bakdata.conquery.models.exceptions.JSONException;
import com.bakdata.conquery.models.identifiable.ids.specific.DatasetId;
import com.bakdata.conquery.models.identifiable.ids.specific.ManagedQueryId;

/**
 * Helper for easier and cleaner authorization.
 *
 */
public class AuthorizationHelper {
	
	// Dataset Instances
	/**
	 * Helper function for authorizing an ability on a dataset.
	 * @param user The subject that needs authorization.
	 * @param dataset The id of the object that needs to be checked.
	 * @param ability The kind of ability that is checked.
	 */
	public static void authorize(User user, DatasetId dataset, Ability ability) {
		authorize(user, dataset, EnumSet.of(ability));
	}
	
	/**
	 * Helper function for authorizing an ability on a dataset.
	 * @param user The subject that needs authorization.
	 * @param dataset The id of the object that needs to be checked.
	 * @param ability The kind of ability that is checked.
	 */
	public static void authorize(User user, DatasetId dataset, EnumSet<Ability> abilities) {
		user.checkPermission(new DatasetPermission(user.getId(), abilities, dataset));
	}
	
	// Query Instances
	/**
	 * Helper function for authorizing an ability on a query.
	 * @param user The subject that needs authorization.
	 * @param query The id of the object that needs to be checked.
	 * @param ability The kind of ability that is checked.
	 */
	public static void authorize(User user, ManagedQueryId query, Ability ability) {
		authorize(user, query, EnumSet.of(ability));
	}
	
	/**
	 * Helper function for authorizing an ability on a query.
	 * @param user The subject that needs authorization.
	 * @param query The id of the object that needs to be checked.
	 * @param ability The kind of ability that is checked.
	 */
	public static void authorize(User user, ManagedQueryId query, EnumSet<Ability> abilities) {
		user.checkPermission(new QueryPermission(user.getId(), abilities, query));
	}
	
	/**
	 * Utility function to add a permission to a subject (e.g {@link User}).
	 * @param owner The subject to own the new permission.
	 * @param permission The permission to add.
	 * @param storage A storage where the permission are added for persistence.
	 * @throws JSONException When the permission object could not be formed in to the appropriate JSON format.
	 */
	public static void addPermission(PermissionOwner<?> owner, ConqueryPermission permission, MasterMetaStorage storage) throws JSONException {
		owner.addPermission(storage, permission);
	}
	
	/**
	 * Utility function to remove a permission from a subject (e.g {@link User}).
	 * @param owner The subject to own the new permission.
	 * @param permission The permission to remove.
	 * @param storage A storage where the permission is removed from.
	 * @throws JSONException When the permission object could not be formed in to the appropriate JSON format.
	 */
	public static void removePermission(PermissionOwner<?> owner, ConqueryPermission permission, MasterMetaStorage storage) throws JSONException {
		owner.removePermission(storage, permission);
	}
}
