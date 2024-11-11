package com.smpinheiro.football.olap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smpinheiro.football.olap.dto.GoalStatsOutPutDTO;
import com.smpinheiro.football.olap.dto.GoalkeeperStatsOutputDTO;
import com.smpinheiro.football.olap.service.AnalyticsService;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

	@Autowired
	private AnalyticsService analyticsService;
	
	@GetMapping("/top/goalkeepers")
	public ResponseEntity<List<GoalkeeperStatsOutputDTO>> getTopGoalKeeeper(){
		return ResponseEntity.ok(analyticsService.getTopGoalkeepers() );
	}
	
	public ResponseEntity<List<GoalStatsOutPutDTO>> getTopScorers(){
		return ResponseEntity.ok(analyticsService.getTopSoccers() );
	}
}
