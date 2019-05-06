package my.example.rest_logic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.example.rest_logic.dao.ProfileDao;
import my.example.rest_logic.entity.Profile;
import my.example.rest_logic.errors.ProfileNotFoundException;

@Service // mark the implementation of business logic
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ProfileDao profileDao;

	@Override
	public List<Profile> getAllProfiles() {
		// TODO add @Transactional once DB connection established
		return profileDao.getAllProfiles();
	}

	@Override
	public Profile getProfileById(int id) {
		// TODO add @Transactional once DB connection established
		return profileDao.getProfileById(id);
	}

	@Override
	public Profile getProfileByUserName(String userName) {
		// TODO add @Transactional once DB connection established
		return profileDao.getProfileByUserName(userName);
	}

	@Override
	public Profile saveProfile(Profile profile) {
		profile.setId(0);
		return profileDao.saveProfile(profile);
	}

	@Override
	public Profile updateProfile(Profile profile) {
		Profile temp = profileDao.updateProfile(profile);
		
		if(temp == null){
			//TODO throw custom exception about update error
			//temp = new Profile(0, "UPDATE ERROR", "Error updating profile " + profile);
			throw new ProfileNotFoundException("No profile found to update");
		}
		
		return temp;
	}

	@Override
	public Profile deleteProfile(int profileId) {
		Profile temp = profileDao.deleteProfile(profileId);
		if (temp == null) {
			// TODO throw custom exception about update error
			// temp = new Profile(0, "DELETE ERROR", "Error deleting profile at
			// " + profileId);
			throw new ProfileNotFoundException("No profile found to delete");
		}
		return temp;
	}

}
