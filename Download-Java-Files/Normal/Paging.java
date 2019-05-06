/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 * <p>
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.samples.store.interfaces.rest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

/**
 * Class.
 */
public class Paging {

    @QueryParam("pageIndex")
    @DefaultValue("1")
    private Long pageIndex;
    @QueryParam("pageSize")
    @DefaultValue("10")
    private Integer pageSize;

    /**
     * Getter.
     *
     * @return Long.
     */
    public Long getPageIndex() {
        return pageIndex;
    }

    /**
     * Setter.
     *
     * @param pageIndex Long.
     */
    public void setPageIndex(final Long pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * Getter.
     *
     * @return Integer.
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Setter.
     *
     * @param pageSize Integer.
     */
    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }
}
