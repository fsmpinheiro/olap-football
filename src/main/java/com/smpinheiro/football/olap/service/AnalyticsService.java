package com.smpinheiro.football.olap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smpinheiro.football.olap.dto.GoalStatsOutPutDTO;
import com.smpinheiro.football.olap.dto.GoalkeeperStatsOutputDTO;
import com.smpinheiro.football.olap.repository.FootballRepository;

@Service
public class AnalyticsService {

	@Autowired
	private FootballRepository repository;
	
	public List<GoalStatsOutPutDTO> getTopSoccers(){
		return repository.getTopScorers();
	}
	
	public List<GoalkeeperStatsOutputDTO> getTopGoalkeepers(){
		return repository.getTopGoalKeepers();
	}
	
}
