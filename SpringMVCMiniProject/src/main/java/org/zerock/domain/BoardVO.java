package org.zerock.domain;

import java.util.Date;

import lombok.Data;
/*���̺� ���踦 �������� VO�� ����� �ȴ� VO:Read Only �����͸� �ҷ��ͼ� �ѷ��ִ� ����*/
/*lombok�� �̿��ؼ� getter/setter ���� toString()���� ������ ��Ļ��*/
/*��ü*/
@Data
public class BoardVO {

	private Long bno;
	
	private String title;
	
	private String content;
	
	private String writer;
	
	private Date regdate;
	
	private Date updateDate;
}