package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

/*�������̽� �߰� sql ó��*/
/*Mapper�������̽��� �ۼ��Ҷ��� ����Ʈ(select), ���(insert) �켱�ؼ� �ۼ�*/
/*�ۼ��� sql�𺧷��ۿ��� ������ �ۼ��� �׽�Ʈ ���� 1.�������� ���� �������� 2commit���� ���� �׽�Ʈ��� ����ġ �Ҽ� �ִ� */
public interface BoardMapper {
	//Select ��ü������
	//@Select("select * from tbl_board where bno > 0")
	public List<BoardVO> getList();
	//insert
	public void insert(BoardVO board);
	
	public void insertSelectKey(BoardVO board);
	//Select �ܰ�
	public BoardVO read(Long bno);
	
	//Delete
	public int delete(Long bno);
	//update
	public int update(BoardVO board);
	//������ó�� Criteria paging ó�� (Ÿ�� ������)
	public List<BoardVO> getListWithPaging(Criteria cri);
	//������ ��ü�����ͼ� ó�� 
	public int getTotalCount(Criteria cri);

}