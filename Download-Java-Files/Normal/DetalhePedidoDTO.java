package br.com.uds.udspizzaria.presentation.dto;

import java.math.BigDecimal;
import java.util.List;

import br.com.uds.udspizzaria.domain.model.adicional.Adicional;
import br.com.uds.udspizzaria.domain.model.sabor.Sabor;
import br.com.uds.udspizzaria.domain.model.tamanho.Tamanho;

public class DetalhePedidoDTO {
	private Tamanho tamanho;
	
	private Sabor sabor;
	
	private List<Adicional> adicionais;
	
	private Integer tempoTotal;
	
	private BigDecimal valorTotal;
	
	public DetalhePedidoDTO(Tamanho tamanho, Sabor sabor, List<Adicional> adicionais, Integer tempoTotal,
			BigDecimal valorTotal) {
		this.tamanho = tamanho;
		this.sabor = sabor;
		this.adicionais = adicionais;
		this.tempoTotal = tempoTotal;
		this.valorTotal = valorTotal;
	}

	public Tamanho getTamanho() {
		return tamanho;
	}

	public Sabor getSabor() {
		return sabor;
	}

	public List<Adicional> getAdicionais() {
		return adicionais;
	}

	public Integer getTempoTotal() {
		return tempoTotal;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}
}
