/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 * <p>
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.samples.store.interfaces.data;

import org.seedstack.business.assembler.AggregateId;
import org.seedstack.business.assembler.DtoOf;
import org.seedstack.business.assembler.FactoryArgument;
import org.seedstack.business.data.DataSet;
import org.seedstack.samples.store.domain.model.customer.Customer;
import org.seedstack.samples.store.domain.model.customer.CustomerId;

/**
 * Class.
 */
@DtoOf(Customer.class)
@DataSet(group = "store", name = "customers")
public class CustomerDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String address;
    private String deliveryAddress;
    private String password;

    /**
     * Getter.
     *
     * @return String.
     */
    public String getId() {
        return id;
    }

    /**
     * Setter.
     *
     * @param id String.
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * Getter.
     *
     * @return CustomerID.
     */
    @AggregateId(index = 0)
    @FactoryArgument(index = 0)
    public CustomerId getCustomerId() {
        return new CustomerId(id);
    }

    /**
     * Getter.
     *
     * @return String.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter.
     *
     * @param firstName String.
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter.
     *
     * @return String.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter.
     *
     * @param lastName String.
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter.
     *
     * @return String.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter.
     *
     * @param address String.
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * Getter.
     *
     * @return String.
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * Setter.
     *
     * @param deliveryAddress String.
     */
    public void setDeliveryAddress(final String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * Getter.
     *
     * @return String.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter.
     *
     * @param password String.
     */
    public void setPassword(final String password) {
        this.password = password;
    }
}
