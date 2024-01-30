package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

/*인터페이스 추가 sql 처리*/
/*Mapper인터페이스를 작성할때는 리스트(select), 등록(insert) 우선해서 작성*/
/*작성후 sql디벨로퍼에서 쿼리문 작성후 테스트 진행 1.문제없이 실행 가능한지 2commit으로 인한 테스트결과 불일치 할수 있다 */
public interface BoardMapper {
	//Select 전체데이터
	//@Select("select * from tbl_board where bno > 0")
	public List<BoardVO> getList();
	//insert
	public void insert(BoardVO board);
	
	public void insertSelectKey(BoardVO board);
	//Select 단건
	public BoardVO read(Long bno);
	
	//Delete
	public int delete(Long bno);
	//update
	public int update(BoardVO board);
	//페이지처리 Criteria paging 처리 (타입 변수명)
	public List<BoardVO> getListWithPaging(Criteria cri);
	//페이지 전체데이터수 처리 
	public int getTotalCount(Criteria cri);

}