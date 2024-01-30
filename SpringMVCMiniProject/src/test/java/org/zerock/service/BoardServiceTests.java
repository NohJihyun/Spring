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
		assertNotNull(service); //junit Nullüũ �ϴ� �޼���
	}
	@Test //insert ��� �׽�Ʈ
	public void testRegister() {
		BoardVO board = new BoardVO();
		board.setTitle("���� �ۼ��ϴ� ���Դϴ�");
		board.setContent("���� �ۼ��ϴ� �����Դϴ�");
		board.setWriter("newbie �Դϴ�");
		
		service.register(board);   // ���� test ���� -> �������� -> ����.java-> ����.xml ������ ���� 
		
		log.info("������ �Խù��� ��ȣ :" + board.getBno());
	}
	@Test //select ��ü LiST
	public void testGetList() {
	//service.getList().forEach(board -> log.info(board));
	// Criteria paging ó���� ��� �Ҽ� �ּ�
		service.getList(new Criteria(2,10)).forEach(board -> log.info(board));	
	}
	@Test //select �ܰ�
	public void testGet() {
		log.info(service.get(1L));
	}
	@Test//DELETE 
	public void testDelete() {
		//�Խù� ��ȣ�� ���� ���θ� Ȯ���ϰ� �׽�Ʈ�Ұ�
		log.info("REMOVE RESULT:" + service.remove(2L));
	}
	@Test//UPDATE
	public void testUpdate() {
		BoardVO board = service.get(1L);
		
		if(board == null) {
			return;
		}
		board.setTitle("������ �����մϴ�.");
		log.info("UPDATE RESULT:" + service.modify(board));
	}
	
}
