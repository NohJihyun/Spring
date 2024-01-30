package org.zerock.domain;

import java.util.Date;

import lombok.Data;
/*테이블 설계를 기준으로 VO를 만들면 된다 VO:Read Only 데이터를 불러와서 뿌려주는 역할*/
/*lombok을 이용해서 getter/setter 셋팅 toString()등을 만들어내는 방식사용*/
/*객체*/
@Data
public class BoardVO {

	private Long bno;
	
	private String title;
	
	private String content;
	
	private String writer;
	
	private Date regdate;
	
	private Date updateDate;
}