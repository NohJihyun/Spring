package org.zerock.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	/* pageNum과 amount 값을 같이 전달하는 용도 생성자를 통해 기본값 1페이지 ~10개로 지정해서 처리한다 */
	private int pageNum;  //페이지 번호
	private int amount;   //데이터 양 
	/* 검색조건 확장으로 변수 지정 2.게터/세터 셋팅 3. 검색조건 각 글자 배열로 한번에 처리*/
	private String type; // 검색조건 type 확장
	private String keyword; // 검색조건 keyword 확장
	
	public Criteria() {
		this(1,10);
	}
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	//검색조건 각 글자 배열로 처리 String [] 타입의 메서드
	public String [] getTypeArr() {
		return type == null ? new String[] {} : type.split("");
	}
	/*p345 검색조건 처리 --> 페이지이동, 기능작동시 파라미터처리를 --> URL의 형태로 만들어주는 기능 추가*/
	/*p349 파라미터를 손쉽게 추가한 형태로 Criteria가 생성된다고 가정*/
	public String getListLink() {
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
			
			.queryParam("pageNum", this.pageNum) // P349 queryParam() 메서드를 이용해서 필요한 파라미터들을 손쉽게 추가할수 있다
			.queryParam("amount", this.amount)
			.queryParam("type", this.type)
			.queryParam("keyword", this.keyword);
			
		return builder.toUriString();
	}
}
