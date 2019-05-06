package br.com.uds.udspizzaria.domain.service;

import java.util.List;

import br.com.uds.udspizzaria.domain.model.adicional.Adicional;
import br.com.uds.udspizzaria.presentation.dto.AdicionalDTO;

public interface AdicionalServiceInterface extends BaseServiceInterface<Adicional> {
	public List<AdicionalDTO> listarTodosComIdENome();
}
