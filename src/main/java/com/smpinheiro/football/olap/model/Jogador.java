package com.smpinheiro.football.olap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jogador {

	private Integer id;
	
	private String  name;
	
	private String  position;
	
	private String  team;
	
	private String  nationality;
}
