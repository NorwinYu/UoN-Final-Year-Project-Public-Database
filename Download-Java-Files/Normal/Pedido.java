package br.com.uds.udspizzaria.domain.model.pedido;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Embedded
	private Pizza pizza;
	
	@Column(name = "valor_total")
	private BigDecimal valorTotal;
	
	@Column(name = "tempo_total_preparo")
	private Integer tempoTotalPreparo;
	
	public Pedido(Pizza pizza) {
		this.pizza = pizza;
	}
	
	public Pedido() {}

	public Pizza getPizza() {
		return pizza;
	}

	public void setPizza(Pizza pizza) {
		this.pizza = pizza;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Integer getTempoTotalPreparo() {
		return tempoTotalPreparo;
	}

	public void setTempoTotalPreparo(Integer tempoTotalPreparo) {
		this.tempoTotalPreparo = tempoTotalPreparo;
	}

	public Long getId() {
		return id;
	}
	
	/**
	 * Metodo responsável por fechar o pedido.
	 */
	public void fechar() {
		this.calcularTempoTotal();
		this.calcularValorTotal();
	}
	
	/**
	 * Metodo responsável por calcular o tempo total através:
	 * Tempo de preparo do tamanho da pizza.
	 * Tempo adicional do sabor da pizza
	 * Tempo adicionais da personalização (Adicionais)
	 */
	private void calcularTempoTotal() {
		this.tempoTotalPreparo = this.pizza.getTamanho().getTempoPreparo() + this.pizza.getSabor().getTempoAdicional();
		this.pizza.getAdicionais()
			.stream()
			.filter(adicional -> adicional.getValorAdicional() != null)
			.forEach(adicional -> {
				this.tempoTotalPreparo += adicional.getTempoAdicional();
			});
	}

	/**
	 * Metodo responsável por realizar o calculo do valor total do pedido considerando:
	 * O tamanho e os adicionais 
	 */
	private void calcularValorTotal() {
		this.valorTotal = this.pizza.getTamanho().getValor();
		this.pizza.getAdicionais()
			.stream()
			.filter(adicional -> adicional.getValorAdicional() != null)
			.forEach(adicional -> {
				this.valorTotal = this.valorTotal.add(adicional.getValorAdicional());
			});
	}
}
