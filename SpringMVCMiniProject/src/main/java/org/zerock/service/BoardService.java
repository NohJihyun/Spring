package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService {
	//INSERT
	public void register(BoardVO board);
	//SELECT
	public BoardVO get(Long bno);
	//UPDATE
	public boolean modify(BoardVO board);
	//DELETE
	public boolean remove(Long bno);
	//LIST SELECT
	//public List<BoardVO> getList();
	//criteria paging LIST ����
	public List<BoardVO> getList(Criteria cri);
	//criteria paging ��ü�����ͼ� ó�� count 
	public int getTotal(Criteria cri);
}
