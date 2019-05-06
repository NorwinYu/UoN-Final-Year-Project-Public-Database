package br.com.uds.udspizzaria.application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.uds.udspizzaria.domain.model.adicional.Adicional;
import br.com.uds.udspizzaria.domain.service.AdicionalServiceInterface;
import br.com.uds.udspizzaria.infrastructure.persistence.hibernate.repository.AdicionalRepositoryInterface;
import br.com.uds.udspizzaria.presentation.dto.AdicionalDTO;

@Service
public class AdicionalService extends BaseService<Adicional> implements AdicionalServiceInterface {
	@Autowired
	private AdicionalRepositoryInterface adicionalRepository;

	@Override
	protected JpaRepository<Adicional, Long> getRepository() {
		return this.adicionalRepository;
	}

	@Override
	public List<AdicionalDTO> listarTodosComIdENome() {
		return this.adicionalRepository.findIdAndNomeByAll();
	}
}
