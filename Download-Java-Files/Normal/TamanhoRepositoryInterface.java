package br.com.uds.udspizzaria.infrastructure.persistence.hibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.uds.udspizzaria.domain.model.tamanho.Tamanho;
import br.com.uds.udspizzaria.presentation.dto.TamanhoDTO;

public interface TamanhoRepositoryInterface extends JpaRepository<Tamanho, Long> {
    @Query("select new br.com.uds.udspizzaria.presentation.dto.TamanhoDTO(t.id, t.nome) from Tamanho t ")
	public List<TamanhoDTO> findIdAndNomeByAll();
}
