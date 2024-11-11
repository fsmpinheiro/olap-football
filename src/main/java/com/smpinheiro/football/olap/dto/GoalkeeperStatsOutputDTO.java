package com.smpinheiro.football.olap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalkeeperStatsOutputDTO {

	private Integer id;
	private Integer concedGoals;
	private Integer gameTotal;
	
}
