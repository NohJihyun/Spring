package org.zerock.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {

	@Setter(onMethod_ = {@Autowired})
	private BoardService service;
	
	@Test
	public void testExist() {
		
		log.info(service);
		assertNotNull(service); //junit Null체크 하는 메서드
	}
	@Test //insert 등록 테스트
	public void testRegister() {
		BoardVO board = new BoardVO();
		board.setTitle("새로 작성하는 글입니다");
		board.setContent("새로 작성하는 내용입니다");
		board.setWriter("newbie 입니다");
		
		service.register(board);   // 서비스 test 시작 -> 서비스임플 -> 매퍼.java-> 매퍼.xml 쿼리문 실행 
		
		log.info("생성된 게시물의 번호 :" + board.getBno());
	}
	@Test //select 전체 LiST
	public void testGetList() {
	//service.getList().forEach(board -> log.info(board));
	// Criteria paging 처리로 상단 소수 주석
		service.getList(new Criteria(2,10)).forEach(board -> log.info(board));	
	}
	@Test //select 단건
	public void testGet() {
		log.info(service.get(1L));
	}
	@Test//DELETE 
	public void testDelete() {
		//게시물 번호의 존재 여부를 확인하고 테스트할것
		log.info("REMOVE RESULT:" + service.remove(2L));
	}
	@Test//UPDATE
	public void testUpdate() {
		BoardVO board = service.get(1L);
		
		if(board == null) {
			return;
		}
		board.setTitle("제목을 수정합니다.");
		log.info("UPDATE RESULT:" + service.modify(board));
	}
	
}
