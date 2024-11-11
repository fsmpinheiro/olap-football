package com.smpinheiro.football.olap.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.smpinheiro.football.olap.dto.GoalStatsOutPutDTO;
import com.smpinheiro.football.olap.dto.GoalkeeperStatsOutputDTO;
import com.smpinheiro.football.olap.model.Jogador;
import com.smpinheiro.football.olap.model.Team;

@Repository
public class FootballRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void savePlayerData(Jogador jogador){
		String sql = "INSERT INTO jogador (id, nome, posicao, time) VALUES (?,?,?,?)";
		jdbcTemplate.update(sql, jogador.getId(), jogador.getName(), jogador.getPosition(), jogador.getTeam() );
	}
	
	public void saveGoalData(Integer jogadorId, Integer goals, Date data){
		String sql = "INSERT INTO goals (jogador_id, goals, data) VALUES (?,?,?)";
		jdbcTemplate.update(sql, jogadorId, goals, data);
	}
	
	public void saveDefensesData(Integer jogadorId, Integer golsSofridos, Integer jogos, Date data){
		String sql = "INSERT INTO defense (joagador_id, gols_sofridos, jogos, data) VALUES (?,?,?,?)";
		jdbcTemplate.update(sql, jogadorId, golsSofridos, jogos, data);
	}
	
	public void saveFoulsData(Integer jogadorId, Integer faltas, Date data) {
		String sql = "INSERT INTO fouls (jogador_id, faltas, data) VALUES (?,?,?)";
		jdbcTemplate.update(sql, jogadorId, faltas, data);
	}
	
	public void saveTeam(Team time) {
		String sql = "INSERT INTO team (id, nome) VALUES (?,?)";
		jdbcTemplate.update(sql, time.getId(), time.getName());
	}
	
	
	//Listando jogadores com maior número de gols
	public List<GoalStatsOutPutDTO> getTopScorers(){
		String sql = "SELECT jogador_id, SUM(goals) as total_goals FROM goals GROUP BY jogador_id ORDER BY total_goals DESC LIMIT 10";
		
		return jdbcTemplate.query(sql,
				(rs, rowNum) -> new GoalStatsOutPutDTO(rs.getInt("jogador_id"), rs.getInt("total_goals") ) );
	}
	
	//Listando goleiros com melhor desempenho
	public List<GoalkeeperStatsOutputDTO> getTopGoalKeepers(){
		String sql = "SELECT jogador_id, SUM(gols_sofridos) as total_gols_sofridos, SUM(jogos) as total_jogos FROM defense GROUP BY jogador_id ORDER BY total_gols_sofridos ASC LIMIT 10";
		
		return jdbcTemplate.query(sql, 
				(rs, rowNum) -> new GoalkeeperStatsOutputDTO(rs.getInt("jogador_id"),rs.getInt("total_gols_sofridos"), rs.getInt("total_jogos") ) );
	}
	
}
