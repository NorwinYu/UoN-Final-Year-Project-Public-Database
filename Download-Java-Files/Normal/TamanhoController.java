package br.com.uds.udspizzaria.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uds.udspizzaria.application.service.TamanhoService;
import br.com.uds.udspizzaria.presentation.dto.TamanhoDTO;

@RestController
@RequestMapping("/tamanhos")
public class TamanhoController {

	@Autowired
	protected TamanhoService tamanhoService;
	
	@GetMapping
	public List<TamanhoDTO> listar() {		
		return this.tamanhoService.listarTodosComIdENome();
	}
}
