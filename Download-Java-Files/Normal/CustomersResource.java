/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 * <p>
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.samples.store.interfaces.rest.customer;

import com.google.common.base.Strings;
import org.seedstack.business.assembler.dsl.FluentAssembler;
import org.seedstack.business.domain.AggregateExistsException;
import org.seedstack.business.domain.AggregateNotFoundException;
import org.seedstack.business.domain.Repository;
import org.seedstack.business.pagination.Page;
import org.seedstack.business.pagination.dsl.Paginator;
import org.seedstack.business.specification.Specification;
import org.seedstack.business.specification.dsl.SpecificationBuilder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.samples.store.domain.model.customer.Customer;
import org.seedstack.samples.store.domain.model.customer.CustomerId;
import org.seedstack.samples.store.interfaces.rest.Paging;
import org.seedstack.seed.transaction.Transactional;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.BeanParam;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Class.
 */
@Path("/customers")
@Transactional
@JpaUnit("store")
public class CustomersResource {

    @Inject
    private Repository<Customer, CustomerId> customerRepository;
    @Inject
    private FluentAssembler fluentAssembler;
    @Inject
    private SpecificationBuilder specificationBuilder;
    @Inject
    private Paginator paginator;
    @Context
    private UriInfo uriInfo;

    private static final String CUSTOMER = "Customer ";
    private static final String NOT_FOUND = " not found";

    /**
     * Method.
     *
     * @param searchString String.
     * @param paging       Paging.
     * @return Page.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Page<CustomerRepresentation> listCustomers(final @QueryParam("searchString") String searchString,
                                                      final @BeanParam Paging paging) {
        return fluentAssembler.assemble(paginator.paginate(customerRepository)
                .byPage(paging.getPageIndex())
                .ofSize(paging.getPageSize())
                .matching(buildFilteringSpecification(searchString)))
                .toPageOf(CustomerRepresentation.class);
    }

    /**
     * Method.
     *
     * @param searchString String.
     * @return Spec.
     */
    private Specification<Customer> buildFilteringSpecification(final String searchString) {
        if (!Strings.isNullOrEmpty(searchString)) {
            return specificationBuilder.of(Customer.class)
                    .property("firstName").matching("*" + searchString + "*")
                    .or()
                    .property("lastName").matching("*" + searchString + "*")
                    .build();
        } else {
            return specificationBuilder.of(Customer.class)
                    .all()
                    .build();
        }
    }

    /**
     * Getter.
     *
     * @param customerId String.
     * @return CustomerRepresentation.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{customerId}")
    public CustomerRepresentation getCustomer(final @PathParam("customerId") String customerId) {
        return fluentAssembler.assemble(customerRepository.get(new CustomerId(customerId))
                .orElseThrow(() -> new NotFoundException(CUSTOMER + customerId + NOT_FOUND)))
                .to(CustomerRepresentation.class);
    }

    /**
     * Method.
     *
     * @param customerRepresentation CustomerRepresentation.
     * @return Response.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCustomer(final CustomerRepresentation customerRepresentation) {
        Customer customer = fluentAssembler.merge(customerRepresentation)
                .into(Customer.class)
                .fromFactory();

        try {
            customerRepository.add(customer);
        } catch (AggregateExistsException e) {
            final int status = 409;
            throw new ClientErrorException(
                    CUSTOMER + customerRepresentation.getId() + " already exists",
                    status
            );
        }

        return Response.created(
                URI.create(this.uriInfo.getRequestUri().toString() + "/" + customerRepresentation.getId()))
                .entity(fluentAssembler.assemble(customer).to(CustomerRepresentation.class))
                .build();
    }

    /**
     * Method.
     *
     * @param customerRepresentation CustomerRepresentation.
     * @param customerId             CustomerID
     * @return CustomerRepresentation.
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{customerId}")
    public CustomerRepresentation updateCustomer(final CustomerRepresentation customerRepresentation,
                                                 final @PathParam("customerId") String customerId) {
        if (!customerId.equals(customerRepresentation.getId())) {
            throw new BadRequestException("Customer identifiers from body and URL don't match");
        }

        Customer customer;
        try {
            customer = fluentAssembler.merge(customerRepresentation).into(Customer.class).fromRepository()
                    .orFail();
        } catch (AggregateNotFoundException e) {

            throw new NotFoundException(CUSTOMER + customerId + NOT_FOUND);
        }
        customerRepository.update(customer);

        return fluentAssembler.assemble(customer).to(CustomerRepresentation.class);
    }

    /**
     * Method.
     *
     * @param customerId String.
     */
    @DELETE
    @Path("/{customerId}")
    public void deleteCustomer(final @PathParam("customerId") String customerId) {
        try {
            customerRepository.remove(new CustomerId(customerId));
        } catch (AggregateNotFoundException e) {
            throw new NotFoundException(CUSTOMER + customerId + NOT_FOUND, e);
        }
    }
}
