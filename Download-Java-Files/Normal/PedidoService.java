package br.com.uds.udspizzaria.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.uds.udspizzaria.application.service.exception.InformacaoNaoEncontradaException;
import br.com.uds.udspizzaria.application.service.exception.PedidoNaoEncontradoException;
import br.com.uds.udspizzaria.domain.model.pedido.Pedido;
import br.com.uds.udspizzaria.domain.service.PedidoServiceInterface;
import br.com.uds.udspizzaria.infrastructure.persistence.hibernate.repository.PedidoRepositoryInterface;
import br.com.uds.udspizzaria.presentation.assembler.PedidoAssembler;
import br.com.uds.udspizzaria.presentation.dto.DetalhePedidoDTO;

@Service
public class PedidoService extends BaseService<Pedido> implements PedidoServiceInterface {

	@Autowired
	private PedidoRepositoryInterface pedidoRepository;
	
	@Autowired
	private PedidoAssembler assembler;
	
	@Override
	protected JpaRepository<Pedido, Long> getRepository() {
		return this.pedidoRepository;
	}

	@Override
	public DetalhePedidoDTO detalhar(Long id) {
		try {
			Pedido pedido = this.buscar(id);

			return assembler.getDTO(pedido);
		} catch (InformacaoNaoEncontradaException e) {
			throw new PedidoNaoEncontradoException();
		}
	}
	
	@Override
	public Pedido atualizar(Long id, Pedido pedidoDTO) {
		Pedido pedido = this.buscar(id);
		pedido.getPizza().setAdicionais(pedidoDTO.getPizza().getAdicionais());
		pedido.fechar();

		return super.salvar(pedido);
	}
	
	@Override
	public Pedido salvar(Pedido pedido) {
		pedido.fechar();
		
		return super.salvar(pedido);
	}
}
