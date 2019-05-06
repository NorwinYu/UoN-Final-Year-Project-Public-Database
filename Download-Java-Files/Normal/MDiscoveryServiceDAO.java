/**
 * Copyright 2017 Pogeyan Technologies
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.pogeyan.cmis.api.data.services;

import java.util.List;

import com.pogeyan.cmis.api.data.IBaseObject;

public interface MDiscoveryServiceDAO {
	/**
	 * Returns List of MBaseObject Object values depending on change log
	 * token,maxItems,skipCount.
	 */
	public List<? extends IBaseObject> getLatestChanges(long changeLogToken, int maxItems, String[] mappedColumns,
			String orderBy, String filterExpression, MTypeManagerDAO typeManager, Boolean includeAcl,
			String[] principalIds);

	public long getLatestTokenChildrenSize(long latestChangeToken, String filterExpression, MTypeManagerDAO typeManager,
			Boolean includeAcl, String[] principalIds);
}
