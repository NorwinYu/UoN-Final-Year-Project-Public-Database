/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 * <p>
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.samples.store.interfaces.rest.category;

import org.seedstack.business.assembler.AggregateId;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.samples.store.domain.model.category.Category;

/**
 * Class.
 */
@DtoOf(Category.class)
public class CategoryRepresentation {

    private Long id;
    private String name;
    private String urlImg;

    /**
     * Constructor.
     */
    public CategoryRepresentation() {
    }

    /**
     * Constructor.
     *
     * @param id     Long.
     * @param name   String.
     * @param urlImg String.
     */
    public CategoryRepresentation(final long id, final String name, final String urlImg) {
        this.id = id;
        this.name = name;
        this.urlImg = urlImg;
    }

    /**
     * Getter.
     *
     * @return Long.
     */
    @AggregateId
    public Long getId() {
        return id;
    }

    /**
     * Setter.
     *
     * @param id Long.
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * Getter.
     *
     * @return string.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter.
     *
     * @param name String.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Getter.
     *
     * @return String.
     */
    public String getUrlImg() {
        return urlImg;
    }

    /**
     * Setter.
     *
     * @param urlImg String.
     */
    public void setUrlImg(final String urlImg) {
        this.urlImg = urlImg;
    }
}
