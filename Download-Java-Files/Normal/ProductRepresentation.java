/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 * <p>
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.samples.store.interfaces.rest.product;

import org.seedstack.business.assembler.AggregateId;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.samples.store.domain.model.product.Product;

/**
 * Class.
 */
@DtoOf(Product.class)
public class ProductRepresentation {

    private Long id;
    private String designation;
    private String summary;
    private String details;
    private String picture;
    private Double price;
    private Long categoryId;

    /**
     * Constructor.
     */
    public ProductRepresentation() {
    }

    /**
     * Constructor.
     *
     * @param id          Long.
     * @param designation String.
     * @param summary     String.
     * @param details     String.
     * @param picture     String.
     * @param price       String.
     */
    @SuppressWarnings("checkstyle:ParameterNumber")
    public ProductRepresentation(final long id, final String designation, final String summary, final String details,
                                 final String picture, final Double price) {
        this.id = id;
        this.designation = designation;
        this.summary = summary;
        this.details = details;
        this.picture = picture;
        this.price = price;
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
     * @return String.
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * Setter.
     *
     * @param designation String.
     */
    public void setDesignation(final String designation) {
        this.designation = designation;
    }

    /**
     * Getter.
     *
     * @return String.
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Setter.
     *
     * @param summary String.
     */
    public void setSummary(final String summary) {
        this.summary = summary;
    }

    /**
     * Getter.
     *
     * @return String.
     */
    public String getDetails() {
        return details;
    }

    /**
     * Setter.
     *
     * @param details String.
     */
    public void setDetails(final String details) {
        this.details = details;
    }

    /**
     * Getter.
     *
     * @return String.
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Setter.
     *
     * @param picture String.
     */
    public void setPicture(final String picture) {
        this.picture = picture;
    }

    /**
     * Getter.
     *
     * @return Double
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Setter.
     *
     * @param price Double.
     */
    public void setPrice(final Double price) {
        this.price = price;
    }

    /**
     * Getter.
     *
     * @return Long
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * Setter.
     *
     * @param categoryId Long.
     */
    public void setCategoryId(final Long categoryId) {
        this.categoryId = categoryId;
    }
}
