package com.qa.apartmentManager.apartmentapi.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.qa.apartmentManager.apartmentapi.constants.Constants;
import com.qa.apartmentManager.apartmentapi.persistence.domain.ApartmentManager;


@Repository
public interface ApartmentManagerRepository extends JpaRepository<ApartmentManager, Long> {

	@Query (Constants.CLEANSTATUSQUERY)
	public List<ApartmentManager> findByCleanStatus(@Param ("value") String value);
	
	@Query (Constants.ISOCCUPIEDQUERY)
	public List<ApartmentManager> findIsOccupied(@Param ("value") boolean value);
	
	@Query (Constants.FINDINTAKEQUERY)
	public List<ApartmentManager> findIntake(@Param ("value") String value);
	
	@Query (Constants.FINDAPARTMENTQUERY)
	public List<ApartmentManager> findByApartment(@Param ("apartmentBuilding") String apartmentBuilding, @Param ("apartmentNumber") int apartmentNumber);
}
