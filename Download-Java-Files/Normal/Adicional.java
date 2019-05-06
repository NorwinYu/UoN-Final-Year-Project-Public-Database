package br.com.uds.udspizzaria.domain.model.adicional;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "adicional")
public class Adicional implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "valor_adicional")
	private BigDecimal valorAdicional;
	
	@Column(name = "tempo_adicional")
	private Integer tempoAdicional;
	
	public Adicional(Long id) {
		this.id = id;
	}
	
	public Adicional() {}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValorAdicional() {
		return this.valorAdicional == null ? BigDecimal.ZERO : this.valorAdicional;
	}

	public void setValorAdicional(BigDecimal valorAdicional) {
		this.valorAdicional = valorAdicional;
	}

	public Integer getTempoAdicional() {
		return this.tempoAdicional == null ? Integer.valueOf(0) : this.tempoAdicional;
	}

	public void setTempoAdicional(Integer tempoAdicional) {
		this.tempoAdicional = tempoAdicional;
	}

	public Long getId() {
		return id;
	}
}
