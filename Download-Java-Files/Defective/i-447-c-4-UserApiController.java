package edu.itba.paw.jimi.webapp.api;

import edu.itba.paw.jimi.interfaces.services.UserService;
import edu.itba.paw.jimi.models.User;
import edu.itba.paw.jimi.webapp.dto.UserDTO;
import edu.itba.paw.jimi.webapp.dto.UserListDTO;
import edu.itba.paw.jimi.webapp.dto.form.user.UserForm;
import edu.itba.paw.jimi.webapp.utils.PaginationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@Path("users")
@Controller
@Produces(value = {MediaType.APPLICATION_JSON})
public class UserApiController extends BaseApiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserApiController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PaginationHelper paginationHelper;

	@Context
	private UriInfo uriInfo;

	private static final int DEFAULT_PAGE_SIZE = 10;
	private static final int MAX_PAGE_SIZE = 30;

	@GET
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response listUsers(@QueryParam("page") @DefaultValue("1") Integer page,
	                          @QueryParam("pageSize") @DefaultValue("" + DEFAULT_PAGE_SIZE) Integer pageSize) {
		page = paginationHelper.getPageAsOneIfZeroOrLess(page);
		pageSize = paginationHelper.getPageSizeAsDefaultSizeIfOutOfRange(pageSize, DEFAULT_PAGE_SIZE, MAX_PAGE_SIZE);
		final Collection<User> allUsers = userService.findAll(pageSize, (page - 1) * pageSize);
		return Response.ok(new UserListDTO(new LinkedList<>(allUsers), buildBaseURI(uriInfo)))
				.links(paginationHelper.getPaginationLinks(uriInfo, page, userService.getTotalUsers()))
				.build();
	}

	@POST
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response createUser(@Valid final UserForm userForm) {
		if (userForm == null)
			return Response.status(Response.Status.BAD_REQUEST).build();

		if (!userForm.getPassword().equals(userForm.getRepeatPassword()))
			return Response
					.status(Response.Status.BAD_REQUEST)
					.entity(errorMessageToJSON(messageSource.getMessage("non_matching_passwords", null, LocaleContextHolder.getLocale())))
					.build();

		if (userService.findByUsername(userForm.getUsername()) != null) {
			LOGGER.warn("Cannot create user: existing username {} found", userForm.getUsername());
			return Response
					.status(Response.Status.CONFLICT)
					.entity(errorMessageToJSON(messageSource.getMessage("user.error.repeated.body", null, LocaleContextHolder.getLocale())))
					.build();
		}

		final User user = userService.create(userForm.getUsername(), passwordEncoder.encode(userForm.getPassword()));
		final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getId())).build();
		return Response.created(location).entity(new UserDTO(user, buildBaseURI(uriInfo))).build();
	}

	@GET
	@Path("/{id}")
	public Response getUserById(@PathParam("id") final long id) {
		final User user = userService.findById(id);

		if (user == null) {
			LOGGER.warn("User with id {} not found", id);
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		return Response.ok(new UserDTO(user, buildBaseURI(uriInfo))).build();
	}

}
