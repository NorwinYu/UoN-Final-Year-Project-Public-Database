package br.com.uds.udspizzaria.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uds.udspizzaria.application.service.AdicionalService;
import br.com.uds.udspizzaria.presentation.dto.AdicionalDTO;

@RestController
@RequestMapping("/adicionais")
public class AdicionalController {
	@Autowired
	protected AdicionalService adicionalService;
	
	@GetMapping
	public List<AdicionalDTO> listar() {		
		return this.adicionalService.listarTodosComIdENome();
	}
}
