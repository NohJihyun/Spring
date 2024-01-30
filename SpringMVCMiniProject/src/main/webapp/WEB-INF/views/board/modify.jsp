<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="../includes/header.jsp" %>

<script type="text/javascript">
$(document).ready(function(){
	
	var formObj = $("form");

	$('button').on("click", function(e){
		e.preventDefault();   // 기본제어 

		var operation = $(this).data("oper"); //data-oprer 속성으로 원하는 기능 동작시키기

		console.log(operation);

		if(operation === 'remove'){
			formObj.attr("action", "/board/remove");
		} else if (operation === 'list'){
			// move to list
			formObj.attr("action", "/board/list").attr("method", "get");
			//폼태그에서 필요한 부분 clone 복사 보관
			//p347 다시 리스트로 이동하는 경우에는 필요한 파라미터만 전송하기 위해서 폼태그에 모든 내용을 empty() 지우고, 다시 추가하는 방식을 사용했으므로
			//p347 keyword와 type 역시 추가시킴
			var pageNumTag = $("input[name='pageNum']").clone();
			var amountTag = $("input[name='amount']").clone();
			var keywordTag = $("input[name='keyword']").clone();
			var typeTag = $("input[name='type']").clone();

			formObj.empty();  // list로 돌아가는 버튼을 눌렀을때는 아무런 파라미터가 없기때문에 폼태그에서 모든 내용을 삭제한다음에 submit() 진행한다.
			//필요한 태그들만 추가해서 /board/list를 호출하는 형태를 이용한다.
			//p347 위에서 폼에 내용을 지우고 다시 폼에 담에서 클릭시 다시 추가 
			formObj.append(pageNumTag);
			formObj.append(amountTag);
			formObj.append(keywordTag);
			formObj.append(typeTag);
		}
		formObj.submit();  // 서브밋으로 전송
	});	
});
</script>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">수정/삭제 페이지</h1>
	</div>
</div>

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Board Read Page</div>
			
			<div class="panel-body">
			
				<form role="form" action="/board/modify" method="post">
				
					<input type="hidden" name="pageNum" value='<c:out value="${cri.pageNum }"/>'>
					<input type="hidden" name="amount" value='<c:out value="${cri.amount }"/>'>
					<input type="hidden" name="type" value='<c:out value="${cri.type }"/>'>
					<input type="hidden" name="keyword" value='<c:out value="${cri.keyword }"/>'>
					
					<div class="form-group">
						<label>Bno</label> <input class="form-control" name='bno' value='<c:out value="${board.bno}"/>' readonly="readonly">
					</div>
					
					<div class="form-group">
						<label>제목수정</label> <input class="form-control" name='title' value='<c:out value="${board.title}"/>'>
					</div>
					
					<div class="form-group">
						<label>내용수정</label> <textarea class="form-control" rows="3" name='content'><c:out value="${board.content}"/></textarea>
					</div>
					
					<div class="form-group">
						<label>Writer</label> <input class="form-control" name='writer' value='<c:out value="${board.writer}"/>' readonly="readonly">
					</div>
					
					<div class="form-group">
						<label>RegDate</label> <input class="form-control" name='regDate' value='<fmt:formatDate pattern = "yyyy/MM/dd" value = "${board.regdate}" />' readonly="readonly">
					</div>
					
					<div class="form-group">
						<label>Update Date</label> <input class="form-control" name='updatedate' value='<fmt:formatDate pattern = "yyyy/MM/dd" value = "${board.updateDate}" />' readonly="readonly">
					</div>
				
					<button type="submit" data-oper='modify' class="btn btn-default">수정</button>
					<button type="submit" data-oper='remove' class="btn btn-default">삭제</button>
					<button type="submit" data-oper='list' class="btn btn-default">리스트</button>
				</form>
				
			</div>
			
		</div>
		
	</div>
</div>
<%@ include file="../includes/footer.jsp" %>