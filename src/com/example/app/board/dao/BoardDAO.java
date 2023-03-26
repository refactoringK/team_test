package com.example.app.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.example.app.board.dto.BoardDTO;
import com.example.app.board.vo.BoardVO;
import com.mybatis.config.MyBatisConfig;

public class BoardDAO {
	public SqlSession sqlSession;
	
	public BoardDAO() {
		sqlSession = MyBatisConfig.getSqlSessionFactory().openSession(true);
	}
	
	public List<BoardVO> selectAll(Map<String, Integer> pageMap) {
		return sqlSession.selectList("board.selectAll", pageMap);
	}
	
	public int getTotal() {
		return sqlSession.selectOne("board.getTotal");
	}
	
	public void insert(BoardDTO boardDTO) {
		sqlSession.insert("board.insert", boardDTO);
	}
	
	public int getSequence() {
		return sqlSession.selectOne("board.getSequence");
	}
	
	public BoardVO select(int boardNumber) {
		return sqlSession.selectOne("board.select", boardNumber);
	}
	
	public void delete(int boardNumber) {
		sqlSession.delete("board.delete", boardNumber);
	}
	
	public void update(BoardDTO boardDTO) {
		sqlSession.update("board.update", boardDTO);
	}
	
	public void updateReadCount(int boardNumber) {
		sqlSession.update("board.updateReadCount", boardNumber);
	}
}











