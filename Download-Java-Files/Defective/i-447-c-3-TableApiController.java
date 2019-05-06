package edu.itba.paw.jimi.webapp.api;

import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.interfaces.services.OrderService;
import edu.itba.paw.jimi.interfaces.services.TableService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.models.Order;
import edu.itba.paw.jimi.models.Table;
import edu.itba.paw.jimi.models.TableStatus;
import edu.itba.paw.jimi.webapp.dto.OrderDTO;
import edu.itba.paw.jimi.webapp.dto.TableDTO;
import edu.itba.paw.jimi.webapp.dto.TableListDTO;
import edu.itba.paw.jimi.webapp.dto.form.table.*;
import edu.itba.paw.jimi.webapp.utils.PaginationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;

@Path("tables")
@Controller
public class TableApiController extends BaseApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TableApiController.class);

	@Autowired
	private TableService tableService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private DishService dishService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PaginationHelper paginationHelper;

	@Context
	private UriInfo uriInfo;

	private static final int DEFAULT_PAGE_SIZE = 10;
	private static final int MAX_PAGE_SIZE = 20;

	@GET
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response listTables(@QueryParam("page") @DefaultValue("1") Integer page,
	                           @QueryParam("pageSize") @DefaultValue("" + DEFAULT_PAGE_SIZE) Integer pageSize) {
		page = paginationHelper.getPageAsOneIfZeroOrLess(page);
		pageSize = paginationHelper.getPageSizeAsDefaultSizeIfOutOfRange(pageSize, DEFAULT_PAGE_SIZE, MAX_PAGE_SIZE);
		final Collection<Table> allTables = tableService.findAll(pageSize, (page - 1) * pageSize);
		return Response.ok(new TableListDTO(new LinkedList<>(allTables), buildBaseURI(uriInfo)))
				.build();
	}

	@POST
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response createTable(@Valid final TableForm tableForm) {
		if (tableForm == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		if (tableService.tableNameExists(tableForm.getName())) {
			LOGGER.warn("Cannot create table: existing name {} found", tableForm.getName());
			return Response
					.status(Response.Status.CONFLICT)
					.entity(errorMessageToJSON(messageSource.getMessage("table.error.existing.name.body", null, LocaleContextHolder.getLocale())))
					.build();
		}

		final Table table = tableService.create(tableForm.getName());
		final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(table.getId())).build();
		return Response.created(location).entity(new TableDTO(table, buildBaseURI(uriInfo))).build();
	}

	@GET
	@Path("/{id}")
	public Response getTableById(@PathParam("id") final long id) {
		final Table table = tableService.findById(id);

		if (table == null) {
			LOGGER.warn("Table with id {} not found", id);
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.ok(new TableDTO(table, buildBaseURI(uriInfo))).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteTable(@PathParam("id") final long id) {
		final Table table = tableService.findById(id);

		if (table == null) {
			LOGGER.warn("Table with id {} not found", id);
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		if (table.getStatus() != TableStatus.FREE) {
			Response
					.status(Response.Status.CONFLICT)
					.entity(errorMessageToJSON(messageSource.getMessage("table.error.not.free.body", null, LocaleContextHolder.getLocale())))
					.build();
		}

		tableService.delete(id);
		LOGGER.info("Table with id {} deleted", id);
		return Response.noContent().build();
	}

	@POST
	@Path("/{id}/name")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setTableName(@PathParam("id") final long id,
	                             @Valid final TableForm tableForm) {
		if (tableForm == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		final Table table = tableService.findById(id);
		if (table == null) {
			LOGGER.warn("Table with id {} not found", id);
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		if (tableService.tableNameExists(tableForm.getName())) {
			LOGGER.warn("Cannot rename table: existing name {} found", tableForm.getName());
			return Response
					.status(Response.Status.CONFLICT)
					.entity(errorMessageToJSON(messageSource.getMessage("table.error.existing.name.body", null, LocaleContextHolder.getLocale())))
					.build();
		}

		tableService.setName(table, tableForm.getName());
		return Response.ok(new TableDTO(table, buildBaseURI(uriInfo))).build();
	}

	@POST
	@Path("/{id}/status")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setTableStatus(@PathParam("id") final long id,
	                               @Valid final TableStatusForm tableStatusForm) {
		if (tableStatusForm == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		final Table table = tableService.findById(id);
		if (table == null) {
			LOGGER.warn("Table with id {} not found", id);
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		tableService.changeStatus(table, tableStatusForm.getStatus());
		return Response.ok(new TableDTO(table, buildBaseURI(uriInfo))).build();
	}

	@POST
	@Path("/{id}/diners")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setTableDiners(@PathParam("id") final long id,
	                               @Valid final TableDinersForm tableDinersForm) {
		if (tableDinersForm == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		final Table table = tableService.findById(id);
		if (table == null) {
			LOGGER.warn("Table with id {} not found", id);
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		orderService.setDiners(table.getOrder(), tableDinersForm.getDiners());
		return Response.ok(new TableDTO(table, buildBaseURI(uriInfo))).build();
	}

	@POST
	@Path("/{id}/dishes")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addDish(@PathParam("id") final long id,
	                        @Valid final TableAddDishForm tableAddDishForm) {
		if (tableAddDishForm == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		final Table table = tableService.findById(id);
		if (table == null) {
			LOGGER.warn("Table with id {} not found", id);
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity(errorMessageToJSON(messageSource.getMessage("table.error.not.found.body", null, LocaleContextHolder.getLocale())))
					.build();
		}

		final Dish dish = dishService.findById(tableAddDishForm.getDishId());
		if (dish == null) {
			LOGGER.warn("Dish with id {} not found", tableAddDishForm.getDishId());
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity(errorMessageToJSON(messageSource.getMessage("dish.error.404.body", null, LocaleContextHolder.getLocale())))
					.build();
		}

		if (orderService.containsDish(table.getOrder(), dish.getId())) {
			LOGGER.warn("From table with id {}, dish id {} already exists", id, dish.getId());
			return Response
					.status(Response.Status.CONFLICT)
					.entity(errorMessageToJSON(messageSource.getMessage("table.error.dish.exists.body", null, LocaleContextHolder.getLocale())))
					.build();
		}

		final Order order = table.getOrder();
		orderService.addDishes(order, dish, tableAddDishForm.getAmount());
		return Response.ok(new TableDTO(table, buildBaseURI(uriInfo))).build();
	}

	@POST
	@Path("/{id}/dishes/{dishId}/amount")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response setDishAmount(@PathParam("id") final long id,
	                              @PathParam("dishId") final int dishId,
	                              @Valid final TableDishAmountForm tableDishAmountForm) {
		if (tableDishAmountForm == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		final Table table = tableService.findById(id);
		if (table == null) {
			LOGGER.warn("Table with id {} not found", id);
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity(errorMessageToJSON(messageSource.getMessage("table.error.not.found.body", null, LocaleContextHolder.getLocale())))
					.build();
		}

		if (!orderService.containsDish(table.getOrder(), dishId)) {
			LOGGER.warn("From table with id {}, dish id {} not found", id, dishId);
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity(errorMessageToJSON(messageSource.getMessage("table.error.dish.not.found.body", null, LocaleContextHolder.getLocale())))
					.build();
		}

		final Order order = table.getOrder();
		final Dish currentDish = orderService.getDishById(order, dishId);
		final int newAmount = tableDishAmountForm.getAmount();
		orderService.setNewUndoneDishAmount(order, currentDish, newAmount);
		return Response.ok(new TableDTO(table, buildBaseURI(uriInfo))).build();
	}

	@DELETE
	@Path("/{id}/dishes/{dishId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response removeDish(@PathParam("id") final long id,
	                           @PathParam("dishId") final int dishId) {
		final Table table = tableService.findById(id);
		if (table == null) {
			LOGGER.warn("Table with id {} not found", id);
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity(errorMessageToJSON(messageSource.getMessage("table.error.not.found.body", null, LocaleContextHolder.getLocale())))
					.build();
		}

		if (!orderService.containsDish(table.getOrder(), dishId)) {
			LOGGER.warn("From table with id {}, dish id {} not found", id, dishId);
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity(errorMessageToJSON(messageSource.getMessage("table.error.dish.not.found.body", null, LocaleContextHolder.getLocale())))
					.build();
		}

		final Order order = table.getOrder();
		orderService.removeAllUndoneDish(order, orderService.getDishById(order, dishId));
		return Response.noContent().build();
	}

	@GET
	@Path("/{id}/order")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getCheckoutBill(@PathParam("id") final long id) {
		final Table table = tableService.findById(id);
		if (table == null) {
			LOGGER.warn("Table with id {} not found", id);
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok(new OrderDTO(table.getOrder(), buildBaseURI(uriInfo))).build();
	}
}
