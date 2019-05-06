package br.com.uds.udspizzaria.domain.model.sabor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sabor")
public class Sabor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "tempo_adicional")
	private Integer tempoAdicional;
	
	public Sabor(Long id) {
		this.id = id;
	}
	
	public Sabor() {}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
