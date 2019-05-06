package edu.itba.paw.jimi.webapp.api;

import edu.itba.paw.jimi.interfaces.services.DishService;
import edu.itba.paw.jimi.models.Dish;
import edu.itba.paw.jimi.webapp.dto.DishDTO;
import edu.itba.paw.jimi.webapp.dto.DishListDTO;
import edu.itba.paw.jimi.webapp.dto.form.dish.DishForm;
import edu.itba.paw.jimi.webapp.dto.form.dish.SetDishStockForm;
import edu.itba.paw.jimi.webapp.utils.PaginationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;

@Path("dishes")
@Controller
public class DishApiController extends BaseApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DishApiController.class);

	@Autowired
	private DishService dishService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PaginationHelper paginationHelper;

	@Context
	private UriInfo uriInfo;

	private static final int DEFAULT_PAGE_SIZE = 5;
	private static final int MAX_PAGE_SIZE = 20;

	@GET
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response listDishes(@QueryParam("page") @DefaultValue("1") Integer page,
	                           @QueryParam("pageSize") @DefaultValue("" + DEFAULT_PAGE_SIZE) Integer pageSize) {
		page = paginationHelper.getPageAsOneIfZeroOrLess(page);
		pageSize = paginationHelper.getPageSizeAsDefaultSizeIfOutOfRange(pageSize, DEFAULT_PAGE_SIZE, MAX_PAGE_SIZE);
		final Collection<Dish> allDishes = dishService.findAll(pageSize, (page - 1) * pageSize);
		return Response.ok(new DishListDTO(new LinkedList<>(allDishes), buildBaseURI(uriInfo)))
				.links(paginationHelper.getPaginationLinks(uriInfo, page, dishService.getTotalDishes()))
				.build();
	}

	@POST
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response createDish(@Valid final DishForm dishForm) {
		if (dishForm == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		final Dish dish = dishService.create(dishForm.getName(), dishForm.getPrice());
		dishService.setStock(dish, dishForm.getStock());
		dishService.setMinStock(dish, dishForm.getMinStock());

		final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(dish.getId())).build();
		return Response.created(location).entity(new DishDTO(dish, buildBaseURI(uriInfo))).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateDish(@PathParam("id") final int id,
	                           @Valid final DishForm dishForm) {
		if (dishForm == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		final Dish dish = dishService.findById(id);
		if (dish == null) {
			LOGGER.warn("Dish with id {} not found", id);
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		dishService.setName(dish, dishForm.getName());
		dishService.setStock(dish, dishForm.getStock());
		dishService.setPrice(dish, dishForm.getPrice());
		dishService.setMinStock(dish, dishForm.getMinStock());

		return Response.ok(new DishDTO(dish, buildBaseURI(uriInfo))).build();
	}

	@POST
	@Path("/{id}/stock")
	public Response setDishStock(@PathParam("id") final int id,
	                             @Valid final SetDishStockForm setDishStockForm) {
		if (setDishStockForm == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		final Dish dish = dishService.findById(id);
		if (dish == null) {
			LOGGER.warn("Dish with id {} not found", id);
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		dishService.setStock(dish, setDishStockForm.getNewStock());
		return Response.ok(new DishDTO(dish, buildBaseURI(uriInfo))).build();
	}

	@POST
	@Path("/downloadCSV")
	@Produces({"text/csv"})
	public Response downloadDishesMissingStockCSV(@Context HttpServletResponse response) throws IOException {
		CsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

		String[] headerT = {
				messageSource.getMessage("csv.name", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("csv.price", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("csv.stock", null, LocaleContextHolder.getLocale()),
				messageSource.getMessage("csv.minStock", null, LocaleContextHolder.getLocale()),
		};
		csvWriter.writeHeader(headerT);

		String[] header = {"name", "price", "stock", "minStock"};
		for (Dish dish : dishService.findDishesMissingStock(
				dishService.getTotalDishes(),
				0
		))
			csvWriter.write(dish, header);

		csvWriter.flush();
		csvWriter.close();

		return Response
				.ok()
				.header("Content-Disposition", String.format("attachment; filename=\"%s\"", messageSource.getMessage("csv.file", null, LocaleContextHolder.getLocale())))
				.header("Content-Type", "application/vnd.ms-excel")
				.build();
	}
}
