package br.com.uds.udspizzaria.application.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.uds.udspizzaria.domain.model.adicional.Adicional;
import br.com.uds.udspizzaria.domain.model.pedido.Pedido;
import br.com.uds.udspizzaria.domain.model.pedido.Pizza;
import br.com.uds.udspizzaria.domain.model.sabor.Sabor;
import br.com.uds.udspizzaria.domain.model.tamanho.Tamanho;
import br.com.uds.udspizzaria.presentation.assembler.PedidoAssembler;
import br.com.uds.udspizzaria.presentation.dto.PedidoDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PedidoServiceTest {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PedidoAssembler assembler;
	
    @Test
    public void montarPedidoComTamanhoPequenoESaborCalabresa() {
    	PedidoDTO pedido = new PedidoDTO(new Pizza(new Tamanho(1L), new Sabor(1L)));
    	
    	Pedido pedidoSalvo = this.pedidoService.salvar(this.assembler.getEntity(pedido));
    	Tamanho tamanho = pedidoSalvo.getPizza().getTamanho();
    	Sabor sabor = pedidoSalvo.getPizza().getSabor();
    	Integer tempoPedidoTotal = sabor.getTempoAdicional() + tamanho.getTempoPreparo();
    	
        assertEquals(new String("Calabresa"), pedidoSalvo.getPizza().getSabor().getNome());
        assertEquals(new String("Pequena"), pedidoSalvo.getPizza().getTamanho().getNome());
        assertEquals(pedidoSalvo.getPizza().getTamanho().getValor(), pedidoSalvo.getValorTotal());
        assertEquals(tempoPedidoTotal, pedidoSalvo.getTempoTotalPreparo());
    }
    
    @Test
    public void personalizarPedidoComAdicionalExtraBacon() {
    	List<Adicional> adicionais = new ArrayList<Adicional>();
    	adicionais.add(new Adicional(1L));
    	
    	PedidoDTO pedido = new PedidoDTO(new Pizza(adicionais));
    	Pedido pedidoSalvo = this.pedidoService.atualizar(1L, this.assembler.getEntity(pedido));

    	Tamanho tamanho = pedidoSalvo.getPizza().getTamanho();
    	Sabor sabor = pedidoSalvo.getPizza().getSabor();
    	Adicional adicional = pedidoSalvo.getPizza().getAdicionais().get(0);
    	
    	Integer tempoAdicional = adicional.getTempoAdicional();
    	Integer tempoPedidoTotal = sabor.getTempoAdicional() + tamanho.getTempoPreparo() + tempoAdicional;
    	BigDecimal valorPedidoTotal = tamanho.getValor().add(adicional.getValorAdicional());
    	
        assertEquals(new String("Calabresa"), pedidoSalvo.getPizza().getSabor().getNome());
        assertEquals(new String("Pequena"), pedidoSalvo.getPizza().getTamanho().getNome());
        assertEquals(new String("Extra Bacon"), adicional.getNome());
        assertEquals(valorPedidoTotal, pedidoSalvo.getValorTotal());
        assertEquals(tempoPedidoTotal, pedidoSalvo.getTempoTotalPreparo());
    }
}
