package br.com.uds.udspizzaria.application.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uds.udspizzaria.domain.model.pedido.Pedido;
import br.com.uds.udspizzaria.domain.service.PedidoServiceInterface;
import br.com.uds.udspizzaria.presentation.assembler.PedidoAssembler;
import br.com.uds.udspizzaria.presentation.dto.DetalhePedidoDTO;
import br.com.uds.udspizzaria.presentation.dto.PedidoDTO;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	@Autowired
	private PedidoAssembler assembler;

	@Autowired
	private PedidoServiceInterface pedidoService;

	@PostMapping
	public ResponseEntity<Pedido> salvar(@RequestBody @Valid PedidoDTO pedido) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.pedidoService.salvar(assembler.getEntity(pedido)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pedido> atualizar(@PathVariable Long id, @Valid @RequestBody PedidoDTO pedido) {
		return ResponseEntity.ok(this.pedidoService.atualizar(id, assembler.getEntity(pedido)));
	}
	
	@GetMapping("/{id}")
	public DetalhePedidoDTO buscar(@PathVariable Long id) {
		return this.pedidoService.detalhar(id);
	}
}
