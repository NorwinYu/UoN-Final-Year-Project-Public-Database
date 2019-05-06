package br.com.uds.udspizzaria.presentation.dto;

import javax.validation.constraints.NotNull;

import br.com.uds.udspizzaria.domain.model.pedido.Pizza;

final public class PedidoDTO {
	@NotNull
	private Pizza pizza;

	public PedidoDTO(Pizza pizza) {
		this.pizza = pizza;
	}
	
	public PedidoDTO(Pizza pizza, String teste) {
		this.pizza = pizza;
	}
	
	public PedidoDTO() {}

	public Pizza getPizza() {
		return this.pizza;
	}
}
