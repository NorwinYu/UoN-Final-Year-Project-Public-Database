/**
 * 
 */
package com.htc.par.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.htc.par.entity.Skill;
import com.htc.par.exceptions.ResourceDuplicateException;
import com.htc.par.exceptions.ResourceNotCreatedException;
import com.htc.par.exceptions.ResourceNotDeletedException;
import com.htc.par.exceptions.ResourceNotFoundException;
import com.htc.par.exceptions.ResourceNotUpdatedException;
import com.htc.par.repository.SkillRepository;
import com.htc.par.service.SkillService;
import com.htc.par.to.SkillTO;
import com.htc.par.utilities.NullAwareBeanUtil;

/**
 * Skill Service Implementation
 *
 */

@Service
public class SkillServiceImpl implements SkillService {

	@Autowired
	private SkillRepository skillRepository;

	@Override
	public SkillTO getSkillTO(Skill skill) {
		SkillTO skillTO = new SkillTO(skill.getSkillId(), skill.getSkillName(), skill.getSkillActive());
		return skillTO;
	}

	@Override
	public Skill getSkill(SkillTO skillTO) {
		Skill skill = new Skill(skillTO.getSkillId(), skillTO.getSkillName(), skillTO.getSkillActive());
		return skill;
	}

	/**
	 * Get active Skill by Id
	 * 
	 * @param skillId
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public SkillTO getSkillById(int skillId) throws ResourceNotFoundException {
		Optional<Skill> skillOptional = skillRepository.findBySkillIdAndSkillActive(skillId, true);
		SkillTO skillTO = null;
		if (!skillOptional.isPresent())
			throw new ResourceNotFoundException(String.format("Skill Id: %s not found.", skillId));
		skillTO = getSkillTO(skillOptional.get());
		return skillTO;
	}

	/**
	 * Get active Skills
	 * 
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@Override
	public List<SkillTO> getAllSkills() throws ResourceNotFoundException {
		List<Skill> skillEntities = skillRepository.findAllBySkillActive(true);
		if (CollectionUtils.isEmpty(skillEntities))
			throw new ResourceNotFoundException("No Skills Found.");
		List<SkillTO> skillList = skillEntities.stream().map(skill -> {
			return getSkillTO(skill);
		}).collect(Collectors.toList());
		return skillList;
	}

	/**
	 * Update active skill
	 * 
	 * @return
	 * @throws ResourceNotFoundException, ResourceNotUpdatedException
	 */
	@Override
	public SkillTO updateSkill(SkillTO skillTO) throws ResourceNotFoundException, ResourceNotUpdatedException {
		SkillTO updatedSkillTO = null;
		Skill skill = null;
		try {
			Optional<Skill> skillOptional = skillRepository.findBySkillIdAndSkillActive(skillTO.getSkillId(), true);
			if (!skillOptional.isPresent())
				throw new ResourceNotFoundException(String.format("Skill: %s Not Found.", skillTO.getSkillName()));
			skill = skillOptional.get();
			NullAwareBeanUtil.copyProperties(skillTO, skill);
			updatedSkillTO = getSkillTO(skillRepository.save(skill));
		} catch (DataIntegrityViolationException datintexcp) {
			Optional<Skill> existingSkillOptional = skillRepository.findBySkillName(skillTO.getSkillName());
			if (existingSkillOptional.isPresent()) {
				skill = existingSkillOptional.get();
				if(skill.getSkillActive()) {
					throw new ResourceDuplicateException(String.format("Skill: %s Already Exist.", skillTO.getSkillName()));				
				}else {
					throw new ResourceDuplicateException(String.format("Skill: %s has already been in-activated. Please add it as new skill.", skillTO.getSkillName()));
				}
			} else {
				throw new ResourceDuplicateException(String.format("Skill: %s Already Exist.", skillTO.getSkillName()));
			}
		} catch (DataAccessException dae) {
			throw new ResourceNotUpdatedException(String.format("Skill: %s Not Found.", skillTO.getSkillName()));
		}
		return updatedSkillTO;
	}

	/**
	 * create new skill
	 * 
	 * @return
	 * @throws ResourceDuplicateException, ResourceNotCreatedException
	 */
	@Override
	public SkillTO createSkill(SkillTO skillTO) throws ResourceDuplicateException, ResourceNotCreatedException {
		try {
			Optional<Skill> skillOptional = skillRepository.findBySkillName(skillTO.getSkillName());
			Skill skill = null;
			if (!skillOptional.isPresent()) {
				skillTO.setSkillActive(true);
				skill = getSkill(skillTO);
			} else {
				skill = skillOptional.get();
				if (skill.getSkillActive())
					throw new ResourceDuplicateException(
							String.format("Skill: %s Already Exist.", skillTO.getSkillName()));
				skill.setSkillActive(true);
				skillTO.setSkillActive(true);
			}
			skill = skillRepository.save(skill);
			skillTO = getSkillTO(skill);
		} catch (DataAccessException dae) {
			throw new ResourceNotCreatedException(String.format("Skill: %s not created.", skillTO.getSkillName()));
		}
		return skillTO;
	}

	/**
	 * De-activate skill
	 * 
	 * @return
	 * @throws ResourceNotFoundException, ResourceNotDeletedException
	 */
	@Override
	public boolean deleteSkill(int skillId) throws ResourceNotFoundException, ResourceNotDeletedException {
		try {
			Optional<Skill> skillOptional = skillRepository.findBySkillIdAndSkillActive(skillId, true);
			if (!skillOptional.isPresent())
				throw new ResourceNotFoundException("Skill not found.");
			Skill skill = skillOptional.get();
			skill.setSkillActive(false);
			skillRepository.save(skill);
		} catch (DataAccessException dae) {
			throw new ResourceNotDeletedException("Skill not deleted.");
		}
		return true;
	}
}
