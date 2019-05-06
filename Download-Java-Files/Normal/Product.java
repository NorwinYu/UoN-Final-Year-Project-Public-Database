/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 * <p>
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.samples.store.domain.model.product;

import org.seedstack.business.domain.BaseAggregateRoot;
import org.seedstack.business.domain.Identity;
import org.seedstack.business.util.inmemory.InMemorySequenceGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Class Product.
 */
@Entity
public class Product extends BaseAggregateRoot<Long> {
    @Id
    @Identity(generator = InMemorySequenceGenerator.class)
    private Long id;
    private String designation;
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(length = 500)
    private String summary;
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(length = 10000)
    private String details;
    private String picture;
    private Double price;
    private Long categoryId;

    /**
     * Constructor.
     */
    private Product() {
        // A private constructor ensures that the product is created through its
        // factory so the identity generator is invoked just after creation
    }

    /**
     * Getter.
     *
     * @return Long.
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Constructor.
     *
     * @param id Long.
     */
    public Product(final long id) {
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
     * @return Double.
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
     * @return Long.
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * Setter.
     *
     * @param categoryId Long.
     */
    public void setCategoryId(final long categoryId) {
        this.categoryId = categoryId;
    }
}
