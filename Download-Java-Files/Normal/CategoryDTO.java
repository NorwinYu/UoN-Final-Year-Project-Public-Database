/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 * <p>
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.samples.store.interfaces.data;

import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.data.DataSet;
import org.seedstack.samples.store.domain.model.category.Category;
import org.seedstack.samples.store.domain.model.product.Product;

import java.util.List;

/**
 * Class DTO.
 */
@DtoOf(Category.class)
@DataSet(group = "store", name = "categories")
public class CategoryDTO {
    private String name;
    private String urlImg;
    private List<ProductDTO> products;

    /**
     * Getter.
     *
     * @return String.
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

    /**
     * Getter.
     *
     * @return List ProductDTO.
     */
    public List<ProductDTO> getProducts() {
        return products;
    }

    /**
     * Setter.
     *
     * @param products List ProductDTO.
     */
    public void setProducts(final List<ProductDTO> products) {
        this.products = products;
    }

    /**
     * Static class.
     */
    @DtoOf(Product.class)
    public static class ProductDTO {
        private String designation;
        private String summary;
        private String details;
        private String picture;
        private Double price;
        private String category;

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
         * @return String.
         */
        public String getCategory() {
            return category;
        }

        /**
         * Setter.
         *
         * @param category String.
         */
        public void setCategory(final String category) {
            this.category = category;
        }
    }
}
