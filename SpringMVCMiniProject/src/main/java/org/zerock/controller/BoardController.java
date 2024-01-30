package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
//import org.zerock.domain.Criteria;
//import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
/*
 *  
 */

/* 1.URL �м��� ������� �ݿ��ϴ� �޼��带 �����Ѵ�
 * 2.@Countroller ������̼��� �߰��ؼ� �������� ������ �ν��Ҽ� �ְ� 
 * 3.@RequestMapping URL ����
 * 4.�ش� ��Ʈ�ѷ��� ���� ��Ű���� SERVLET-CONTEXT.XML�� �����Ѵ�
 * 5.SERVLET-CONTEXT.XML���� ������� ��û�� ó���ϱ����ؼ� ��Ʈ�ѷ��� ã�´� 
 * */
@Controller
@Log
@RequestMapping("/board/*")
@AllArgsConstructor /* 1.@AllArgsConstructor ������ ���� BoardService */
public class BoardController {
	//BoardService Ÿ���� ��ü�� ���� �����ؾ� �ϹǷ� ���� ��ü������ �޴´�.
	/* 2. @AllArgsConstructor �̹���� �ƴҽ� onMethode_ = {@Autowired} ��ü�����Ѵ�*/
	private BoardService service;
	
	/*/list ��û�� ������ model��ü�� ����Ʈ�� ��Ƽ� ��� �����Ѵ�*/
	//@GetMapping("/list")
	//public void list(Model model) {
	//	log.info("list");
		//�𵨰�ü����ؼ� ��� �������ش� 
	//	model.addAttribute("list", service.getList());
	//}
	/*
	 * 
	 *  Criteria List Mapping�� ����
	 *  PageDTO ��ü���� ������ ��� ó���� �Ѱ��� ��Ʈ�ѷ����� Model�� ��Ƽ� ȭ�鿡 �����Ѵ�.
	 *  ������ ���ó���� �Ȼ����̰�, ��ü ������ ��ó���� ���� ���� ���¶� �ϴܿ� 123���� ������ ������ ���Ƿ�
	 *  p.324 Criteria Count ó�� �˻��� ����Ʈ�� ����¡ ó���� pageNum, count ���� �����δٰ� ���� ����
	 */
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		log.info("list:"+cri);
		//�𵨰�ü����ؼ� ��� �������ش� 
		model.addAttribute("list", service.getList(cri));
		//pageMaker��� �̸����� PageDTO Ŭ�������� ��ü�� ���� �𵨿� ��Ƽ� ��� �����Ѵ�
		//pageDTO�� �����ϱ� ���ؼ��� ��ü ������ ���� �ʿ��ѵ�, ���� ��ó���� �̷�� �����°� �ƴ϶� 123�� ����
		//model.addAttribute("pageMaker", new PageDTO(cri,123));
		
		int total = service.getTotal(cri);
		log.info("total: " + total);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}
/*
 * INSERT ���ó�� POST������� ���޹޴´� [�Ķ�������� POST:��ü(������) ������ ����]
 * 1.String�� ����Ÿ������ �����Ѵ� 
 * 2.RedirectAttributes�� �Ķ���ͷ� �����Ѵ�
 * 1,2���� �� ������: ����۾��� ���� �� �ٽ� ��� ȭ������ �̵��ϱ� �����̴�.
 * �߰������� ���Ӱ� ��ϵ� �Խù��� ��ȣ�� ���� �����ϱ� ���ؼ� RedirectAttributes ����Ѵ�.
 */
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
	
		log.info("register:" + board);
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno()); /* RedirectAttributes rttr �Ķ���ͻ�� add �߰��Խù� ��Ͻ� bno �߰� */
		return "redirect:/board/list";
	}
/*
 * SELECT ��ȸó�� GET������� ó���Ѵ�.[�Ķ�������� GET:���(URL) ������ ����]
 * @GetMapping�� �̿���ó��
 * VO��ü�� �ִ� bno ���� ���� ��������� ó���ϱ� ������ @RequestParam ����
 * @RequestParam : Http ��û �Ķ���� �� ���ϰ� ������� :  request.getParameter �����ϴ� �����ϸ��
 * �Ķ���� �̸��� ���� �̸��� �������� ���۽�Ű�� ������ �����ص� ����
 * ȭ�� ������ �ش� ��ȣ�� �Խù��� �����ؾ� �ϹǷ� Model�� �Ķ���ͷ� �����Ѵ�.
 */
	
/* @GetMapping @PostMapping ��� URL�� �迭�� ó���� �� �����Ƿ� �Ʒ��� ���� �ϳ��� �޼���� ���� URL�� ó���Ҽ� �ִ�*/
/* ������ ��û�Ķ���� URL ����ó���� /board/modify?bno=30�� ���� ������� ó���ϰ� �ִ�.*/
/* @ModelAttribute�� �ڵ����� Model�� �����͸� ������ �̸����� ����ش�*/
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		
		log.info("/get or modify");
		
		model.addAttribute("board", service.get(bno));
	}
/*
 *  UPDATE ����ó�� ����� ������ �����ϰ�, BoardVO �Ķ���ͷ� ó���� BoardService�� ȣ���Ѵ�.
 *  GET ������� ����������, ���� �۾��� POST ���.
 *  p320.paging Criteria �߰��� ����, RedirectAttribute URL �ڿ� Ŭ���� ������ �̵� pageNum / amount �� ������ �̵�
 */
	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("modify : " + board);
		
		if(service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/board/list" + cri.getListLink();
	}
/*
 * DELETE ���� POST ���
 * ���� �� �������� �̵��� �ʿ��ϹǷ� RedirectAttributes
 * redirect�� �̿��ؼ� ���� ó�� �Ŀ� �ٽ� ����������� �̵��Ѵ�.
 * p320.paging Criteria �߰��� ����, RedirectAttribute URL �ڿ� Ŭ���� ������ �̵� pageNum / amount �� ������ �̵�
 */	
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		
		log.info("remove......." + bno);
		
		if(service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/board/list" + cri.getListLink();
	}
	/* �Խù��� ����۾��� POST, ȭ�鿡�� �Է��� �޾ƾ� �ϹǷ� GET������� �Է� �������� ���� �ֵ��� ó��*/
	/* register �޼���� �Է� �������� �����ִ� ���Ҹ� �ϱ⶧���� ������ ó���� ����.*/
	@GetMapping("/register")
	public void register() {
		
	}
	
}
