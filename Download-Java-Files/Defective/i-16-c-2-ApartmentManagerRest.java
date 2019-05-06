package com.qa.apartmentManager.apartmentapi.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.qa.apartmentManager.apartmentapi.constants.Constants;
import com.qa.apartmentManager.apartmentapi.persistence.domain.ApartmentManager;
import com.qa.apartmentManager.apartmentapi.persistence.domain.SentApartmentManager;
import com.qa.apartmentManager.apartmentapi.service.ApartmentManagerService;
import com.qa.apartmentManager.util.DBTranslator;

@CrossOrigin
@RequestMapping("${path.base}")
@RestController
public class ApartmentManagerRest {

	@Autowired
	private ApartmentManagerService service;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Value("${url.mongoUpdate}")
	private String mongoUpdate;
	
	@Value("${url.mongoDelete}")
	private String mongoDelete;
	
	@Value("${url.mongoUrl}")
	private String mongoUrl;

	@Value("${url.mongoService}")
	private String mongoService;

	@Value("${url.verifyAccountService}")
	private String verifyAccountService;

	@Value("${url.verifyUrl}")
	private String verifyUrl;
	
	@Value("${url.colourPickerService}")
	private String colourPickerService;
	
	@Value("${url.colourUrl}")
	private String colourUrl;

	@GetMapping("${path.getColour}")
	public String getColour() {
		return pickColour();
	}
	
	@GetMapping("${path.checkPassword}")
	public String checkPassword(@PathVariable String password) {
		return verifyPassword(password);
	}

	@GetMapping("${path.getAllFromMongo}")
	public String getAllFromMongo() {
		return getMongoData();
	}

	@GetMapping("${path.getCurrentApartmentManager}")
	public List<ApartmentManager> getCurrentApartmentManager(@PathVariable String intake) {
		return service.getCurrentApartmentManager(intake);
	}

	@GetMapping("${path.getApartmentManager}")
	public List<ApartmentManager> getApartmentManager() {
		return service.getApartmentManager();
	}

	@GetMapping("${path.getApartmentManagerById}")
	public ApartmentManager getApartmentManager(@PathVariable Long id) {
		return service.getApartmentManager(id);
	}

	@DeleteMapping("${path.deleteApartmentManager}")
	public String deleteApartmentManager(@PathVariable Long id) {
		 if (mongoDelete(id)) {
		return service.deleteApartmentManager(id);
		 }
		 else return Constants.FAILMSG;
	}

	@PutMapping("${path.updateApartmentManager}")
	public String updateApartmentManager(@RequestBody ApartmentManager apartmentmanager,
			@PathVariable Long id) {
		if (mongoUpdate(id, apartmentmanager)) {
			return service.updateApartmentManager(apartmentmanager, id);
		}
		else return Constants.FAILMSG;
	}

	@PostMapping("${path.createApartmentManager}")
	public ApartmentManager createApartmentManager(@RequestBody ApartmentManager apartmentManager) {
		sendToQueueForCreate(apartmentManager);
		return service.addApartmentManager(apartmentManager);
	}

	private void sendToQueueForCreate(ApartmentManager apartmentManager) {
		 SentApartmentManager apartmentManagerToStore = new
		 SentApartmentManager(apartmentManager);
		 jmsTemplate.convertAndSend("ApartmentManagerQueue", apartmentManagerToStore);
	}
	
	private boolean mongoDelete(Long id) {
		try {
			restTemplate.delete(mongoService+ mongoDelete + id);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	private boolean mongoUpdate(Long id, ApartmentManager apartmentManager) {
		try {
			restTemplate.put(mongoService + mongoUpdate + id, apartmentManager);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	private String getMongoData() {
		List<SentApartmentManager> mongo = restTemplate.getForObject(mongoService + mongoUrl, List.class);
		Object[] SAMArray = mongo.toArray();
		return service.upDateH2(DBTranslator.getForMongo(SAMArray));
	}

	private String verifyPassword(String password) {
		return restTemplate.getForObject(verifyAccountService + verifyUrl + password, String.class);
	}
	
	private String pickColour() {
		return restTemplate.getForObject(colourPickerService + colourUrl, String.class);
	}
}
