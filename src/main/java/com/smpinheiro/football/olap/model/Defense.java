package com.smpinheiro.football.olap.model;

import java.util.Date;

import jakarta.annotation.security.DenyAll;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@DenyAll
@AllArgsConstructor
@NoArgsConstructor
public class Defense {

	private Integer jogadorId;
	
	private Integer golsSofridos;
	
	private Integer jogos;
	
	private Date data;

	
	public Integer getJogadorId() {
		return jogadorId;
	}

	public Integer getGolsSofridos() {
		return golsSofridos;
	}

	public Integer getJogos() {
		return jogos;
	}

	public Date getData() {
		return data;
	}
	
	
}
