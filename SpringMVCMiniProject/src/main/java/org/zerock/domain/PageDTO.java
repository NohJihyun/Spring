package org.zerock.domain;
/*
 *  여러 정보가 필요하다면 클래스를 구성해서 작업하는 페이징 처리 방식
 *  컨트롤러 역할 jsp에 전달할때 객체를 생성해서 Model에 담아 보내는 과정이 단순해지는 장점도 있다
 */

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	private int startPage;
	private int endPage;
	private boolean prev, next; // 이전, 다음 논리형으로 타입지정
	private int total;
	private Criteria cri;
	
	// 생성자를 정의하고, 크리테리아와 전체 데이티수 Total를 파라미터로 지정
	// Criteria 안에는, 페이지에서 데이터수와, 현재 페이지번호(pageNum)를 가지고 있기 때문에 이를 이용해서 필요한 모든 내용을 계산할수 있다
	public PageDTO(Criteria cri, int total) {
		
		this.cri = cri;
		this.total = total;
		
		//페이지를 계산하는 방법
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10 ; // 페이징 끝 번호 처리 방식
		this.startPage = this.endPage - 9;  // 페이징 시작 번호 처리 방식
		
		//끝 번호endPage와 한 페이지당 보여주는 데이터 수 곱이 > 전체 데이터 수 토탈보다 크면 끝번호 처리방식은 다시 토탈을 이용해서 계산한다 
		//먼저 전체 데이터수 토탈을 이용해서 realEnd가 몇번까지 되는지를 계산 한다.
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));
		// realEnd 진짜 끝페이지가 < 구해둔 끝 번호 방식 endPage보다 작다면 끝번호는 작은 값이 되어야 한다. 
	    if(realEnd < this.endPage) {
	    	this.endPage = realEnd;
	    }
		//prev 이전
	    this.prev = this.startPage > 1;
	    this.next = this.endPage < realEnd;
	}
}
