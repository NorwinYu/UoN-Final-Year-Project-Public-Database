package com.qa.apartmentManager.apartmentapi.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.apartmentManager.apartmentapi.constants.Constants;
import com.qa.apartmentManager.apartmentapi.persistence.domain.ApartmentManager;
import com.qa.apartmentManager.apartmentapi.persistence.repository.ApartmentManagerRepository;
import com.qa.apartmentManager.apartmentapi.util.exceptions.ApartmentManagerNotFoundException;
import com.qa.apartmentManager.util.DateCalculator;

@Service
public class ApartmentManagerServiceImpl implements ApartmentManagerService {

	@Autowired
	private ApartmentManagerRepository repo;

	private static Long staticId =1L;
	
	public List<ApartmentManager> getApartmentManager() {
		return repo.findAll();
	}

	public ApartmentManager getApartmentManager(Long id) {
		Optional<ApartmentManager> apartmentManager = repo.findById(id);
		return apartmentManager.orElseThrow(() -> new ApartmentManagerNotFoundException(id.toString()));
	}

	public ApartmentManager addApartmentManager(ApartmentManager apartmentmanager) {
		for (Long i= 1L;i<=repo.findAll().size();i++) {
			if (repo.findById(i) != null) {
				staticId = i+1;
			}
		}
		apartmentmanager.setApartmentId(staticId);
		return repo.save(apartmentmanager);
	}

	public String deleteApartmentManager(Long id) {
		if (apartmentManagerExists(id)) {
			repo.deleteById(id);
			return Constants.SUCCESSMSG;
		}
		return Constants.FAILMSG;
	}

	public String updateApartmentManager(ApartmentManager apartmentManager, Long id) {
		if (apartmentManagerExists(id)) {
			apartmentManager.setApartmentId(id);
			repo.save(apartmentManager);
			return Constants.SUCCESSMSG;
		}
		return Constants.FAILMSG;
	}

	private boolean apartmentManagerExists(Long id) {
		Optional<ApartmentManager> apartmentManagerOptional = repo.findById(id);
		return apartmentManagerOptional.isPresent();
	}

	@Override
	public List<ApartmentManager> getByACleanStatus(String value) {
		return repo.findByCleanStatus(value);
	}

	@Override
	public List<ApartmentManager> getIsOccupied(boolean value) {
		return repo.findIsOccupied(value);
	}

	@Override
	public List<ApartmentManager> getIntake(String value) {
		return repo.findIntake(value);
	}

	@Override
	public List<ApartmentManager> checkDate(String date) {
		List<ApartmentManager> all = repo.findAll();
		String[] dateBreakdown = date.split("-");
		List<ApartmentManager> toFind = DateCalculator.verifyDates(all, dateBreakdown);
		repo.saveAll(toFind);
		return repo.findIsOccupied(false);
	}

	@Override
	public List<ApartmentManager> getCurrentApartmentManager(String intake) {
		List<ApartmentManager> all = repo.findAll();
		List<ApartmentManager> current = DateCalculator.checkForMultipleIntakes(all, all);
		repo.saveAll(current);
		
		return repo.findIsOccupied(Boolean.parseBoolean(intake));
	}

	@Override
	public String upDateH2(List<ApartmentManager> toSave) {
		repo.saveAll(toSave);
		return Constants.H2MESSAGE;
	}

	@Override
	public List<ApartmentManager> getApartmentByDetails(String apartmentBuilding, int apartmentNumber) {
		return repo.findByApartment(apartmentBuilding, apartmentNumber);
	}

}
