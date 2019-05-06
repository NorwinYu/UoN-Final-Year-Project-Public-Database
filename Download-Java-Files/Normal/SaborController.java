package br.com.uds.udspizzaria.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uds.udspizzaria.application.service.SaborService;
import br.com.uds.udspizzaria.presentation.dto.SaborDTO;

@RestController
@RequestMapping("/sabores")
public class SaborController {
	@Autowired
	protected SaborService saborService;
	
	@GetMapping
	public List<SaborDTO> listar() {		
		return this.saborService.listarTodosComIdENome();
	}
}
