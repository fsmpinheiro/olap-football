package com.smpinheiro.football.olap.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goals {
	
	private Integer jogadorId;
	
	private Integer goals;
	
	private Date data;
}
