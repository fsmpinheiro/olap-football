package com.smpinheiro.football.olap.response;

import java.util.List;

import lombok.Data;

@Data
public class PlayerApiRoot {
	
	private List<ResponseDataPlayer> response;
}
