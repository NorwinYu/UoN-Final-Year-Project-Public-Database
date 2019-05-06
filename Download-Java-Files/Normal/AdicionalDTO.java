package br.com.uds.udspizzaria.presentation.dto;

final public class AdicionalDTO {
	private Long id;
	
	private String nome;

	public AdicionalDTO(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
}
