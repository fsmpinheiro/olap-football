package com.smpinheiro.football.olap.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.smpinheiro.football.olap.dto.FoulsInputDTO;
import com.smpinheiro.football.olap.dto.GamesInputDTO;
import com.smpinheiro.football.olap.dto.GoalsInputDTO;
import com.smpinheiro.football.olap.dto.PlayerInputDTO;
import com.smpinheiro.football.olap.dto.StatisticsInputDTO;
import com.smpinheiro.football.olap.dto.TeamInputDTO;
import com.smpinheiro.football.olap.model.Jogador;
import com.smpinheiro.football.olap.model.Team;
import com.smpinheiro.football.olap.repository.FootballRepository;
import com.smpinheiro.football.olap.response.PlayerApiRoot;
import com.smpinheiro.football.olap.response.ResponseDataPlayer;
import com.smpinheiro.football.olap.response.ResponseDataTeam;
import com.smpinheiro.football.olap.response.TeamApiRoot;

@Service
public class FootballDataService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private FootballRepository footballRepository;
	
	@Value("${api.football.url}")
	private String apiURL;
	
	@Value("${api.football.key}")
	private String apiKey;
	
	private static final String API_PLAYER_STATS = "/players?season=2022&league=71";

	private static final String API_TEAM_STATS = "/teams?season=2022&league=71";
  
	
	// Public Methods
	
	public void fetchAndStoreData() throws JsonProcessingException{
		HttpEntity<String> entity = setHeaders();
		ResponseEntity<TeamApiRoot> responseTeam = getResponseApi(entity);
		ResponseEntity<PlayerApiRoot> responsePlayer = responsePlayer(entity);
		
		PlayerApiRoot apiResponsePlayer = responsePlayer.getBody();
		mountDataAndSavePlayer(apiResponsePlayer);
		
		TeamApiRoot apiResponseTeam = responseTeam.getBody();
		mountDataAndSaveTeam(apiResponseTeam);
	}
	
	
	// Private only Methods
	private void mountDataAndSaveTeam(TeamApiRoot apiResponseTeam){
		for (ResponseDataTeam teamItem : apiResponseTeam.getResponse() ) {
			Team team = new Team();
			team.setId( teamItem.getTeam().getId() );
			team.setName(teamItem.getTeam().getName() );
			footballRepository.saveTeam( team );
		}
	}
	
	private void mountDataAndSavePlayer(PlayerApiRoot apiResponsePlayer){
		for(ResponseDataPlayer responseData : apiResponsePlayer.getResponse() ) {
			PlayerInputDTO playerDTO = responseData.getPlayer();
			Jogador player = new Jogador();
			player.setId(playerDTO.getId() );
			player.setName(playerDTO.getName() );
			//player.setPosition(playerDTO.getPosition() );
			player.setNationality(playerDTO.getNationality() );
			savePlayerStats(responseData, player);
		}
	}
	
	private void savePlayerStats(ResponseDataPlayer responseData, Jogador jogador){
		for( StatisticsInputDTO statisticsDTO: responseData.getStatistics() ) {
			TeamInputDTO teamDTO = statisticsDTO.getTeam();
			GamesInputDTO gamesDTO = statisticsDTO.getGames();
			
			jogador.setTeam(teamDTO.getName() );
			jogador.setPosition(gamesDTO.getPosition() );
			footballRepository.savePlayerData(jogador);
			
			Integer goals = Optional.ofNullable(statisticsDTO.getGoals()).map(GoalsInputDTO::getTotal).orElse(0);
			
			LocalDate today = LocalDate.now();
			Date date = Date.from(today.atStartOfDay(ZoneId.systemDefault() ).toInstant() );
			footballRepository.saveGoalData(jogador.getId(), goals, date);
			
			Integer concededGoal = Optional.ofNullable(statisticsDTO.getGoals() ).map(GoalsInputDTO::getConceded).orElse(0);
			Integer games = Optional.ofNullable(gamesDTO.getNumber() ).orElse(0);
			footballRepository.saveDefensesData(jogador.getId(), concededGoal, games, date);
			
			Integer fouls = Optional.ofNullable(statisticsDTO.getFouls() ).map(FoulsInputDTO::getCommitted).orElse(0);
			footballRepository.saveFoulsData(jogador.getId(), fouls, date);
		}
	}
	
	private ResponseEntity<PlayerApiRoot> responsePlayer(HttpEntity<String> entity){
		ResponseEntity<PlayerApiRoot> responsePlayer = restTemplate.exchange(apiURL + API_PLAYER_STATS, HttpMethod.GET, entity, PlayerApiRoot.class);
		
		return responsePlayer;
	}
	
	private ResponseEntity<TeamApiRoot> getResponseApi(HttpEntity<String> entity){
		ResponseEntity<TeamApiRoot> responseTeam = restTemplate.exchange( apiURL + API_TEAM_STATS, HttpMethod.GET, entity, TeamApiRoot.class );
		
		return responseTeam;
	}
	
	private HttpEntity<String> setHeaders(){
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-rapidapi-hos", "v3.football.api-sports.io");
		headers.set("x-rapidapi-key", apiKey);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return entity;
	}
}
