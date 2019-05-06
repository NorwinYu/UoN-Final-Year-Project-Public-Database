package br.com.uds.udspizzaria.infrastructure.persistence.hibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.uds.udspizzaria.domain.model.sabor.Sabor;
import br.com.uds.udspizzaria.presentation.dto.SaborDTO;

public interface SaborRepositoryInterface extends JpaRepository<Sabor, Long>{
    @Query("select new br.com.uds.udspizzaria.presentation.dto.SaborDTO(s.id, s.nome) from Sabor s ")
	public List<SaborDTO> findIdAndNomeByAll();
}
