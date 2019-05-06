package com.gcp.registration.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gcp.registration.domain.Address;
import com.gcp.registration.domain.Response;
import com.gcp.registration.domain.User;
import com.gcp.registration.domain.UserDetail;
import com.gcp.registration.exceptions.LogonIdException;
import com.gcp.registration.repository.UserRepository;

/**
 * @author Anuj Kumar
 * 
 *         This class is service layer having implementation of UserService
 *         interface
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	protected UserRepository userRepository;

	/**
	 * This method is having implementation of registerUser method to interact
	 * with DB using user repository
	 * 
	 * @param user
	 * @return User
	 */
	@Override
	public User registerUser(User user) {
		logger.info("Start registerUser method:", UserServiceImpl.class.getName());
		logger.debug("User request: ", user.toString());
		try {
			return userRepository.save(user);
		} catch (Exception ex) {
			logger.error("Exception: ", ex.getCause());
			throw new LogonIdException("User with logonId " + user.getLogonId() + " already exists.");
		}

	}

	/**
	 * This method is find user using logonId and password
	 * 
	 * @param userDetail
	 * @return Response
	 */
	public Response findUserByLogonIdAndPassword(UserDetail userDetail) {
		logger.info("Start findUserByLogonIdAndPassword method:", UserServiceImpl.class.getName());
		logger.debug("Logon details :", userDetail);
		Response response = new Response();
		try {
			User user = userRepository.findUserByLogonIdAndPassword(userDetail.getLogonId(), userDetail.getPassword());
			if (user != null) {
				response.setMessage("User login successful.");
				User userDetails = new User();
				Address address = new Address();
				userDetails.setId(user.getId());
				userDetails.setLogonId(user.getLogonId());
				userDetails.setCreated_At(user.getCreated_At());
				userDetails.setUpdated_At(user.getUpdated_At());
				address.setId(user.getAddress().getId());
				address.setFirstName(user.getAddress().getFirstName());
				address.setLastName(user.getAddress().getLastName());
				address.setAddress1(user.getAddress().getAddress1());
				address.setAddress2(user.getAddress().getAddress2());
				address.setEmail(user.getAddress().getEmail());
				address.setPhoneNumber(user.getAddress().getPhoneNumber());
				address.setDateOfBirth(user.getAddress().getDateOfBirth());
				address.setCity(user.getAddress().getCity());
				address.setState(user.getAddress().getState());
				address.setCountry(user.getAddress().getCountry());
				address.setZipCode(user.getAddress().getZipCode());
				address.setAddressType(user.getAddress().getAddressType());
				address.setStatus(user.getAddress().getStatus());
				userDetails.setAddress(address);
				response.setUserDetails(userDetails);
			} else {
				response.setMessage("Invalid logonId/password.");
				response.setUserDetails(null);
			}
		} catch (Exception ex) {
			logger.error("Exception: ", ex.getCause());
			response.setErrorMessage(ex.getMessage());
			response.setMessage("Failed to fetch user profile details.");
			response.setUserDetails(null);
		}
		return response;
	}
}
