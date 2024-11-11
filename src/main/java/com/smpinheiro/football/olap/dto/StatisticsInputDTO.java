package com.smpinheiro.football.olap.dto;

import lombok.Data;

@Data
public class StatisticsInputDTO {

	private TeamInputDTO team;
	private GamesInputDTO games;
	private GoalsInputDTO goals;
	private FoulsInputDTO fouls;
	private PenaltyInputDTO penalty;
	
}
