/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 * <p>
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of
 * the MPL was not distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.samples.store.interfaces.rest.product;

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
import org.seedstack.samples.store.domain.model.product.Product;
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
import java.net.URISyntaxException;

/**
 * Class.
 */
@Path("/products")
@Transactional
@JpaUnit("store")
public class ProductsResource {
    @Inject
    private Repository<Product, Long> productRepository;
    @Inject
    private FluentAssembler fluentAssembler;
    @Inject
    private SpecificationBuilder specificationBuilder;
    @Inject
    private Paginator paginator;
    @Context
    private UriInfo uriInfo;

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
    public Page<ProductRepresentation> listProducts(final @QueryParam("searchString") String searchString,
                                                    final @BeanParam Paging paging) {
        return fluentAssembler.assemble(paginator.paginate(productRepository)
                .byPage(paging.getPageIndex())
                .ofSize(paging.getPageSize())
                .matching(buildFilteringSpecification(searchString)))
                .toPageOf(ProductRepresentation.class);
    }

    /**
     * Method.
     *
     * @param searchString String.
     * @return Spec.
     */
    private Specification<Product> buildFilteringSpecification(final String searchString) {
        if (!Strings.isNullOrEmpty(searchString)) {
            return specificationBuilder.of(Product.class)
                    .property("designation").matching("*" + searchString + "*")
                    .or()
                    .property("summary").matching("*" + searchString + "*")
                    .build();
        } else {
            return specificationBuilder.of(Product.class)
                    .all()
                    .build();
        }
    }

    /**
     * Method.
     *
     * @param productId Long.
     * @return ProductRepresentation.
     */
    @GET
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductRepresentation getProduct(final @PathParam("productId") long productId) {
        return fluentAssembler.assemble(productRepository.get(productId)
                .orElseThrow(() -> new NotFoundException("Customer " + productId + NOT_FOUND)))
                .to(ProductRepresentation.class);
    }

    /**
     * Method.
     *
     * @param productRepresentation ProductRepresentation.
     * @return Response.
     * @throws URISyntaxException Exception.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProduct(final ProductRepresentation productRepresentation)
            throws URISyntaxException {
        Product product = fluentAssembler.merge(productRepresentation)
                .into(Product.class)
                .fromFactory();

        try {
            productRepository.add(product);
        } catch (AggregateExistsException e) {
            final int status = 409;
            throw new ClientErrorException("Customer " + productRepresentation.getId() + " already exists", status);
        }

        return Response
                .created(URI.create(uriInfo.getRequestUri().toString() + "/" + product.getId()))
                .entity(fluentAssembler.assemble(product).to(ProductRepresentation.class))
                .build();
    }

    /**
     * Method.
     *
     * @param productRepresentation ProductRepresentation.
     * @param productId             Long.
     * @return ProductRepresentation.
     */
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{productId}")
    public ProductRepresentation updateProduct(final ProductRepresentation productRepresentation,
                                               final @PathParam("productId") long productId) {
        if (productRepresentation.getId() != productId) {
            throw new BadRequestException("Product identifiers from body and URL don't match");
        }

        Product product;
        try {
            product = fluentAssembler.merge(productRepresentation)
                    .into(Product.class)
                    .fromRepository()
                    .orFail();
        } catch (AggregateNotFoundException e) {
            throw new NotFoundException("Product " + productId + NOT_FOUND);
        }
        productRepository.update(product);

        return fluentAssembler.assemble(product).to(ProductRepresentation.class);
    }

    /**
     * Method.
     *
     * @param productId Long.
     */
    @DELETE
    @Path("/{productId}")
    public void deleteProduct(final @PathParam("productId") long productId) {
        try {
            productRepository.remove(productId);
        } catch (AggregateNotFoundException e) {
            throw new NotFoundException("Product " + productId + NOT_FOUND, e);
        }
    }
}
