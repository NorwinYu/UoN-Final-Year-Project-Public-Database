/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 * <p>
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.samples.store.domain.model.customer;

import org.seedstack.business.domain.BaseValueObject;

import javax.persistence.Embeddable;

/**
 * Class Customer ID.
 */
@Embeddable
public class CustomerId extends BaseValueObject {

    private String value;

    /**
     * Constructor.
     */
    private CustomerId() {
        // for JPA
    }

    /**
     * Constructor.
     *
     * @param value String.
     */
    public CustomerId(final String value) {
        this.value = value;
    }

    /**
     * Getter.
     *
     * @return String.
     */
    public String getValue() {
        return value;
    }
}
