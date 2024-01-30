package org.zerock.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
/* 
 * 
 * 테스트 코드는 다르게 진행되는데,URL테스트는 서버를 실행해야지 가능하므로,
 * 서버를 실행하지 않고 테스트를 진행할수 있는데 추가적인 코드가 필요로 하다
 * 
 * */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration /*ServletContext를 이용해서 스프링에서는 WebApplicationContext라는 존재를 이용한다.*/
@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j

public class BoardControllerTests {
	
	@Setter(onMethod_ = {@Autowired})
	private WebApplicationContext ctx;
	private MockMvc mockMvc;
	/* 1. @Before 적용된 setUP() import 할때 junit을 라이브러리? 이용해서 해야 한다*/
	/* 2. @Before 어노테이션에서 적용된 메서드는 모든 테스트 전에 매번 실행되는 메서드가 된다.*/
	/* 3. mockMvc는 말그대로 가짜MVC라고 생각하면 된다 
	 *	  가짜로 URL과 파라미터 등을 브라우저에서 사용하는 것처럼 만들어서 컨트롤러를 실행해 볼수 있다  
	 * 4. testList() MockMvcRequestBuilders 라는 존재를 이용해서 GET 방식의 호출을 해서 getList()에서 반환된 결과를 model에 담아 데이터를 확인
	 * 
	 * */
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}	
	@Test
	public void testList() throws Exception {
		log.info(
			mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
			.andReturn()
			.getModelAndView()
			.getModelMap());
	}
	@Test
	public void testRegister() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
			.param("title", "테스트 새글 제목")
			.param("content", "테스트 새글 내용")
			.param("writer", "user00")
		).andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);		
	}
	@Test
	public void testGet() throws Exception {
		log.info(mockMvc.perform(MockMvcRequestBuilders
			.get("/board/get")
			.param("bno", "2")) /*bno 파라미터를 지정해서 추가시킨후 실행한다.*/
			.andReturn()
			.getModelAndView()
			.getModelMap());
	}	
	@Test
	public void testRemove() throws Exception {
		
		// 삭제전 데이터베이스에 게시물 번호를 확인할 것
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
			.param("bno", "15")
			).andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
	}
	@Test
	public void testListPaging() throws Exception {
		log.info(
			mockMvc.perform(MockMvcRequestBuilders.get("/board/list")
			.param("pageNum", "2")
			.param("amount", "50"))
			.andReturn()
			.getModelAndView()
			.getModelMap());
	}
}
