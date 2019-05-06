package br.com.uds.udspizzaria.domain.service;

import java.util.List;

import br.com.uds.udspizzaria.domain.model.tamanho.Tamanho;
import br.com.uds.udspizzaria.presentation.dto.TamanhoDTO;

public interface TamanhoServiceInterface extends BaseServiceInterface<Tamanho>{
	public List<TamanhoDTO> listarTodosComIdENome();

}
