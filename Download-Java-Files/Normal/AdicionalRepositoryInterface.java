package br.com.uds.udspizzaria.infrastructure.persistence.hibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.uds.udspizzaria.domain.model.adicional.Adicional;
import br.com.uds.udspizzaria.presentation.dto.AdicionalDTO;

public interface AdicionalRepositoryInterface extends JpaRepository<Adicional, Long> {
    @Query("select new br.com.uds.udspizzaria.presentation.dto.AdicionalDTO(a.id, a.nome) from Adicional a ")
	public List<AdicionalDTO> findIdAndNomeByAll();
}
