package br.com.uds.udspizzaria.domain.service;

import java.util.List;

import br.com.uds.udspizzaria.domain.model.sabor.Sabor;
import br.com.uds.udspizzaria.presentation.dto.SaborDTO;

public interface SaborServiceInterface extends BaseServiceInterface<Sabor> {
	public List<SaborDTO> listarTodosComIdENome();
}
