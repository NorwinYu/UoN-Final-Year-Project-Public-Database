package br.com.uds.udspizzaria.presentation.assembler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.uds.udspizzaria.application.service.SaborService;
import br.com.uds.udspizzaria.application.service.TamanhoService;
import br.com.uds.udspizzaria.domain.model.adicional.Adicional;
import br.com.uds.udspizzaria.domain.model.pedido.Pedido;
import br.com.uds.udspizzaria.domain.model.pedido.Pizza;
import br.com.uds.udspizzaria.domain.model.sabor.Sabor;
import br.com.uds.udspizzaria.domain.model.tamanho.Tamanho;
import br.com.uds.udspizzaria.domain.service.AdicionalServiceInterface;
import br.com.uds.udspizzaria.presentation.dto.DetalhePedidoDTO;
import br.com.uds.udspizzaria.presentation.dto.PedidoDTO;

@Service
public class PedidoAssembler {
	@Autowired
	private TamanhoService tamanhoService;
	
	@Autowired
	private SaborService saborService;
	
	@Autowired
	private AdicionalServiceInterface adicionalService;
	
	/**
	 * Metodo responsávvel por retornar a entidade devidamente preenchida
	 * através do DTO
	 * 
	 * @param PedidoDTO pedido
	 * @return Pedido
	 */
	public Pedido getEntity(PedidoDTO pedido) {
		Tamanho tamanho = new Tamanho();
		Sabor sabor = new Sabor();

		if (pedido.getPizza().getTamanho() != null) {
			tamanho = this.tamanhoService.buscar(pedido.getPizza().getTamanho().getId());
		}
		
		if (pedido.getPizza().getTamanho() != null) {
			sabor = this.saborService.buscar(pedido.getPizza().getSabor().getId());
		}

		List<Adicional> adicionais = pedido.getPizza().getAdicionais()
		.stream()
		.filter(listElement -> listElement != null)
		.map(adicional -> adicional = this.adicionalService.buscar(adicional.getId()))
		.collect(ArrayList<Adicional>::new, ArrayList::add, ArrayList::addAll);

		return new Pedido(new Pizza(tamanho, sabor, adicionais));
	}
	
	/**
	 * Metodo responsável por retornar o DTO devidamente preenchido
	 * através da entidade
	 * 
	 * @param pedido
	 * @return
	 */
	public DetalhePedidoDTO getDTO(Pedido pedido) {
		Pizza pizza = pedido.getPizza();

		return new DetalhePedidoDTO(
				pizza.getTamanho(),
				pizza.getSabor(),
				pizza.getAdicionais(),
				pedido.getTempoTotalPreparo(),
				pedido.getValorTotal()
		);
	}
}
