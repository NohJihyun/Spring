package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

//����ü
//�������̵�
/*@Service ������̼�: ����Ͻ����� ����ϴ� ��ü*/
@Log
@Service
@AllArgsConstructor //��� �Ķ���͸� �̿��ϴ� �����ڸ� ����� ������ ���� �ڵ�� �Ʒ��� ���� ���Թ޴� �����ڰ� ��������� �ȴ�
public class BoardServiceImpl implements BoardService {
	
	// Spring 4.3 �̻󿡼� �ڵ�ó��
	// ��ü�� @Stter(OnMethod_= @Autowired) �����ؾ��� ���� ������ ����� �۵��ϴ°��� ������ 4.3�� �����Ѵ� 
	private BoardMapper mapper; // ���������� ����� �۵��ϱ� ���ؼ� ��ü�� �ʿ��ϴ�

	@Override //insert
	public void register(BoardVO board) {
		// TODO Auto-generated method stub
		log.info("register....." + board);
		mapper.insertSelectKey(board);
	}

	@Override //select �ܰ�
	public BoardVO get(Long bno) {
		
		log.info("get................." + bno);
		
		return mapper.read(bno);
	}
    //SLECT ��ü LIST ����¡ ó���� ���ؼ� �ּ�ó��
	//@Override 
	/*
	 * public List<BoardVO> getList() {
	 * log.info("getList............................");
	 * 
	 * return mapper.getList(); }
	 */
	@Override //Criteria paging ó��
	public List<BoardVO> getList(Criteria cri) {
		log.info("get List with criteria:" + cri);
	
		return mapper.getListWithPaging(cri);	
	}
	@Override //update ���� | ��ȯŸ�� boolean true /false
	//���� ������ == 1 ���� �������� ����°��� ó���ϴ°��� ture/false�� ó���Ѵ�
	public boolean modify(BoardVO board) {
		// TODO Auto-generated method stub
		log.info("modify:......" + board);
	
		return mapper.update(board) == 1;
	}

	@Override //delete ���� | ��ȯŸ�� boolean true /false
	//���� ������ == 1 ���� �������� ����°��� ó���ϴ°��� ture/false�� ó���Ѵ�
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

