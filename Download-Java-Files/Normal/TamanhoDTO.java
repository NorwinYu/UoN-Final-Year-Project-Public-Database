package br.com.uds.udspizzaria.presentation.dto;

import java.io.Serializable;

final public class TamanhoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String nome;

	public TamanhoDTO() {}
	
	public TamanhoDTO(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return this.id;
	}

	public String getNome() {
		return this.nome;
	}
}
