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

/* 1.URL 분석된 내용들을 반영하는 메서드를 설계한다
 * 2.@Countroller 어노테이션을 추가해서 스프링의 빈으로 인식할수 있게 
 * 3.@RequestMapping URL 매핑
 * 4.해당 컨트롤러가 속한 패키지를 SERVLET-CONTEXT.XML에 설정한다
 * 5.SERVLET-CONTEXT.XML에서 사용자의 요청을 처리하기위해서 컨트롤러를 찾는다 
 * */
@Controller
@Log
@RequestMapping("/board/*")
@AllArgsConstructor /* 1.@AllArgsConstructor 생성자 주입 BoardService */
public class BoardController {
	//BoardService 타입의 객체와 같이 연동해야 하므로 서비스 객체주입을 받는다.
	/* 2. @AllArgsConstructor 이방법이 아닐시 onMethode_ = {@Autowired} 객체주입한다*/
	private BoardService service;
	
	/*/list 요청이 들어오면 model객체에 리스트를 담아서 뷰로 전달한다*/
	//@GetMapping("/list")
	//public void list(Model model) {
	//	log.info("list");
		//모델객체사용해서 뷰로 리턴해준다 
	//	model.addAttribute("list", service.getList());
	//}
	/*
	 * 
	 *  Criteria List Mapping에 적용
	 *  PageDTO 객체에서 페이지 계산 처리를 한것을 컨트롤러에서 Model에 담아서 화면에 전달한다.
	 *  페이지 계산처리는 된상태이고, 전체 데이터 수처리는 하지 않은 상태라 하단에 123으로 데이터 지정함 임의로
	 *  p.324 Criteria Count 처리 검색및 리스트에 페이징 처리시 pageNum, count 같이 움직인다고 보면 좋다
	 */
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		log.info("list:"+cri);
		//모델객체사용해서 뷰로 리턴해준다 
		model.addAttribute("list", service.getList(cri));
		//pageMaker라는 이름으로 PageDTO 클래스에서 객체를 만들어서 모델에 담아서 뷰로 전달한다
		//pageDTO를 구성하기 위해서는 전체 데이터 수가 필요한데, 아직 그처리가 이루어 진상태가 아니라 123를 지정
		//model.addAttribute("pageMaker", new PageDTO(cri,123));
		
		int total = service.getTotal(cri);
		log.info("total: " + total);
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}
/*
 * INSERT 등록처리 POST방식으로 전달받는다 [파라미터전송 POST:몸체(본문안) 데이터 전송]
 * 1.String을 리턴타입으로 지정한다 
 * 2.RedirectAttributes를 파라미터로 지정한다
 * 1,2번을 한 이유는: 등록작업이 끝난 후 다시 목록 화면으로 이동하기 위함이다.
 * 추가적으로 새롭게 등록된 게시물의 번호를 같이 전달하기 위해서 RedirectAttributes 사용한다.
 */
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
	
		log.info("register:" + board);
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno()); /* RedirectAttributes rttr 파라미터사용 add 추가게시물 등록시 bno 추가 */
		return "redirect:/board/list";
	}
/*
 * SELECT 조회처리 GET방식으로 처리한다.[파라미터전송 GET:헤더(URL) 데이터 전송]
 * @GetMapping을 이용한처리
 * VO객체에 있는 bno 값을 좀더 명시적으로 처리하기 때문에 @RequestParam 지정
 * @RequestParam : Http 요청 파라미터 값 편리하게 사용적용 :  request.getParameter 동일하다 생각하면됨
 * 파라미터 이름과 변수 이름을 기준으로 동작시키기 때문에 생략해도 무방
 * 화면 쪽으로 해당 번호의 게시물을 전달해야 하므로 Model을 파라미터로 지정한다.
 */
	
/* @GetMapping @PostMapping 등에는 URL을 배열로 처리할 수 있으므로 아래와 같이 하나의 메서드로 여러 URL을 처리할수 있다*/
/* 브라우저 요청파라미터 URL 수정처리를 /board/modify?bno=30과 같은 방식으로 처리하고 있다.*/
/* @ModelAttribute는 자동으로 Model에 데이터를 지정한 이름으로 담아준다*/
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		
		log.info("/get or modify");
		
		model.addAttribute("board", service.get(bno));
	}
/*
 *  UPDATE 수정처리 변경된 내용을 수집하고, BoardVO 파라미터로 처리후 BoardService를 호출한다.
 *  GET 방식으로 접근하지만, 실제 작업은 POST 방식.
 *  p320.paging Criteria 추가된 형태, RedirectAttribute URL 뒤에 클릭한 페이지 이동 pageNum / amount 값 가지고 이동
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
 * DELETE 삭제 POST 방식
 * 삭제 후 페이지의 이동이 필요하므로 RedirectAttributes
 * redirect를 이용해서 삭제 처리 후에 다시 목록페이지로 이동한다.
 * p320.paging Criteria 추가된 형태, RedirectAttribute URL 뒤에 클릭한 페이지 이동 pageNum / amount 값 가지고 이동
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
	/* 게시물의 등록작업은 POST, 화면에서 입력을 받아야 하므로 GET방식으로 입력 페이지를 볼수 있도록 처리*/
	/* register 메서드는 입력 페이지를 보여주는 역할만 하기때문에 별도에 처리가 없다.*/
	@GetMapping("/register")
	public void register() {
		
	}
	
}
