package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

//구현체
//오버라이드
/*@Service 어노테이션: 비즈니스영역 담당하는 객체*/
@Log
@Service
@AllArgsConstructor //모든 파라미터를 이용하는 생성자를 만들기 때문에 실제 코드는 아래와 같이 주입받는 생성자가 만들어지게 된다
public class BoardServiceImpl implements BoardService {
	
	// Spring 4.3 이상에서 자동처리
	// 객체를 @Stter(OnMethod_= @Autowired) 주입해야지 서비스 임플이 제대로 작동하는것을 스프링 4.3이 지원한다 
	private BoardMapper mapper; // 서비스임플이 제대로 작동하기 위해서 객체가 필요하다

	@Override //insert
	public void register(BoardVO board) {
		// TODO Auto-generated method stub
		log.info("register....." + board);
		mapper.insertSelectKey(board);
	}

	@Override //select 단건
	public BoardVO get(Long bno) {
		
		log.info("get................." + bno);
		
		return mapper.read(bno);
	}
    //SLECT 전체 LIST 페이징 처리로 인해서 주석처리
	//@Override 
	/*
	 * public List<BoardVO> getList() {
	 * log.info("getList............................");
	 * 
	 * return mapper.getList(); }
	 */
	@Override //Criteria paging 처리
	public List<BoardVO> getList(Criteria cri) {
		log.info("get List with criteria:" + cri);
	
		return mapper.getListWithPaging(cri);	
	}
	@Override //update 수정 | 반환타입 boolean true /false
	//수정 삭제시 == 1 값이 없어지고 생기는것을 처리하는것을 ture/false로 처리한다
	public boolean modify(BoardVO board) {
		// TODO Auto-generated method stub
		log.info("modify:......" + board);
	
		return mapper.update(board) == 1;
	}

	@Override //delete 제거 | 반환타입 boolean true /false
	//수정 삭제시 == 1 값이 없어지고 생기는것을 처리하는것을 ture/false로 처리한다
	public boolean remove(Long bno) {
		// TODO Auto-generated method stub
		log.info("remove:......" + bno);
		return mapper.delete(bno) == 1;
	}
	@Override //Criteria Paging count
	public int getTotal(Criteria cri) {
		// TODO Auto-generated method stub
		log.info("get total count");
		return mapper.getTotalCount(cri);
	}
	
 }

