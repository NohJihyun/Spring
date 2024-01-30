package org.zerock.domain;
/*
 *  ���� ������ �ʿ��ϴٸ� Ŭ������ �����ؼ� �۾��ϴ� ����¡ ó�� ���
 *  ��Ʈ�ѷ� ���� jsp�� �����Ҷ� ��ü�� �����ؼ� Model�� ��� ������ ������ �ܼ������� ������ �ִ�
 */

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	private int startPage;
	private int endPage;
	private boolean prev, next; // ����, ���� �������� Ÿ������
	private int total;
	private Criteria cri;
	
	// �����ڸ� �����ϰ�, ũ���׸��ƿ� ��ü ����Ƽ�� Total�� �Ķ���ͷ� ����
	// Criteria �ȿ���, ���������� �����ͼ���, ���� ��������ȣ(pageNum)�� ������ �ֱ� ������ �̸� �̿��ؼ� �ʿ��� ��� ������ ����Ҽ� �ִ�
	public PageDTO(Criteria cri, int total) {
		
		this.cri = cri;
		this.total = total;
		
		//�������� ����ϴ� ���
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10 ; // ����¡ �� ��ȣ ó�� ���
		this.startPage = this.endPage - 9;  // ����¡ ���� ��ȣ ó�� ���
		
		//�� ��ȣendPage�� �� �������� �����ִ� ������ �� ���� > ��ü ������ �� ��Ż���� ũ�� ����ȣ ó������� �ٽ� ��Ż�� �̿��ؼ� ����Ѵ� 
		//���� ��ü �����ͼ� ��Ż�� �̿��ؼ� realEnd�� ������� �Ǵ����� ��� �Ѵ�.
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));
		// realEnd ��¥ ���������� < ���ص� �� ��ȣ ��� endPage���� �۴ٸ� ����ȣ�� ���� ���� �Ǿ�� �Ѵ�. 
	    if(realEnd < this.endPage) {
	    	this.endPage = realEnd;
	    }
		//prev ����
	    this.prev = this.startPage > 1;
	    this.next = this.endPage < realEnd;
	}
}
