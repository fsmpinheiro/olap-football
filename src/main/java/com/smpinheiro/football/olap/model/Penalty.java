package com.smpinheiro.football.olap.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Penalty {

	private Integer jogadorId;
	
	private Integer faltas;
	
	private Date data;
}
