package org.zerock.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	/* pageNum�� amount ���� ���� �����ϴ� �뵵 �����ڸ� ���� �⺻�� 1������ ~10���� �����ؼ� ó���Ѵ� */
	private int pageNum;  //������ ��ȣ
	private int amount;   //������ �� 
	/* �˻����� Ȯ������ ���� ���� 2.����/���� ���� 3. �˻����� �� ���� �迭�� �ѹ��� ó��*/
	private String type; // �˻����� type Ȯ��
	private String keyword; // �˻����� keyword Ȯ��
	
	public Criteria() {
		this(1,10);
	}
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	//�˻����� �� ���� �迭�� ó�� String [] Ÿ���� �޼���
	public String [] getTypeArr() {
		return type == null ? new String[] {} : type.split("");
	}
	/*p345 �˻����� ó�� --> �������̵�, ����۵��� �Ķ����ó���� --> URL�� ���·� ������ִ� ��� �߰�*/
	/*p349 �Ķ���͸� �ս��� �߰��� ���·� Criteria�� �����ȴٰ� ����*/
	public String getListLink() {
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
			
			.queryParam("pageNum", this.pageNum) // P349 queryParam() �޼��带 �̿��ؼ� �ʿ��� �Ķ���͵��� �ս��� �߰��Ҽ� �ִ�
			.queryParam("amount", this.amount)
			.queryParam("type", this.type)
			.queryParam("keyword", this.keyword);
			
		return builder.toUriString();
	}
}
