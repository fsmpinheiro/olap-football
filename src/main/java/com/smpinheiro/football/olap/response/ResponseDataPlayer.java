package com.smpinheiro.football.olap.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.smpinheiro.football.olap.dto.PlayerInputDTO;
import com.smpinheiro.football.olap.dto.StatisticsInputDTO;

import lombok.Data;

@Data
public class ResponseDataPlayer {

	private PlayerInputDTO player;
	
	@JsonProperty("statistics")
	private List<StatisticsInputDTO> statistics;
	
}
