<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- jstl 라이브러리 추가  -->    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!-- header include -->
<%@ include file="../includes/header.jsp" %>
<!-- 모달창 피드백 처리를 jquery 사용함   -->
<script type="text/javascript">
	$(document).ready(function(){
		
		var result = '<c:out value="${result}"/>';   
						
							 //컨트롤러에서 bno 등록번호를 key = result 에 담아서 활용중				
		checkModal(result);  //컨트롤러에서 bno로 새게시글이 작성되는경우 result 키로 넘기고 있다 RedirectAttribute로 게시물의 번호가 전송되므로 이를 이용해서 모달창의 내용을 수정한다.
							 //checkModal(): 파라미터에 따라서 모달창을 보여주거나 내용을 수정한 뒤 보이도록 작성한다.
		
		//페이지 뒤로가기 window history 객체 사용 					 
		//window history 객체는 스택구조로 동작하기 때문에, 모달창 띄울 필요없다고 표시해 두는 방식을 이용해서 페이지 이동처리를 한다
		//팝업없이 등록페이지에서 -> 팝업생략 -> 리스트페이지 -> 조회페이지 -> 뒤로가기 클릭시 팝업후 -> 목록페이지 경로를
		//등록페이지 --> 리스트페이지 --> 조회페이지 --> 뒤로가기 클릭시 --> 리스트페이지 --> 등록페이지가 팝업없이 출력이 된다
		history.replaceState({}, null, null);

		function checkModal(result){
			if(result === '' || history.state){
				return;
			}

			if(parseInt(result) > 0){
				$('.modal-body').html("게시글 " + parseInt(result) + "번이 등록되었습니다."); //modal-body로 html class 속성으로 연결되서 모달내용바꿈
			}
			$('#myModal').modal("show"); //모달창 호출하기
		}
		$("#regBtn").on("click", function(){   // 등록페이지 이동
			self.location = "/board/register"; // 게시물 등록버튼 클릭시 페이지 이동 게시물 등록페이지로 div 영역의 button id로 bind(연결함) 
		});
		//페이지네이션 버튼클릭 url 이동처리
		var actionForm = $("#actionForm");
		
		$('.paginate_button a').on('click', function(e){  //폼태그를 추가해서 url의 이동 처리하도록 변경함
			e.preventDefault(); //<a> 태그 직접처리 페이지 이동없도록 처리
			console.log('click');

			actionForm.find("input[name='pageNum']").val($(this).attr("href"));
			actionForm.submit();
		});
		//class 값을 폼에 줘서 이벤트처리를 연결해서 처리하였다 
		//게시물의 제목을 클릭하면 폼태그에 추가로 bno 값을 전송하기 위해서 인풋태그를 만들어주고 폼태그의 action은 board/get으로 변경함
		//처리가 정상적으로 되었다면 pageNum과 amount 파라미터가 추가로 전달되는것을 볼수 있다 
		$(".move").on("click", function(e){
			e.preventDefault();

			actionForm.append("<input type='hidden' name='bno' value='"+$(this).attr("href")+"'>");
			actionForm.attr("action", "/board/get");
			actionForm.submit();
		})
		//검색 처리후 페이지 1페이지 이동 및 화면에 검색조건과 키워드 보이게 하는처리 
		//검색 버튼 클릭 <form>태그의 전송은 막고, 페이지 번호는 1이 되도록 처리
		// searchForm
		var searchForm = $('#searchForm');
		$('#searchForm button').on('click', function(e){
			if(!searchForm.find('option:selected').val()){
				alert("검색종류를 선택하세요.");
				return false;
			}

			if(!searchForm.find("input[name='keyword']").val()){
				alert("키워드를 입력하세요");
				return false;
			}

			searchForm.find("input[name='pageNum']").val("1");
			e.preventDefault();
			searchForm.submit();
		})
		
		
	});
</script>
	<div class="row">
	    <div class="col-lg-12">
	        <h1 class="page-header">게시판</h1>
	    </div>
	    <!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
	<div class="row">
	    <div class="col-lg-12">
	        <div class="panel panel-default">
	            <!-- 게시물 등록을 클릭시 등록페이지로 이동 -->
	            <div class="panel-heading">
	                Board List Page
	                <button id="regBtn" type="button" class="btn btn-xs pull-right">게시물 등록</button>
	            </div>
	            
	            <!-- /.panel-heading -->
	            <!-- 리스트 목록 화면출력하는곳 -->
	            <div class="panel-body">
	                <table width="100%" class="table table-striped table-bordered table-hover">
	                    <thead>
	                        <tr>
	                            <th>#번호</th>
	                            <th>제목</th>
	                            <th>작성자</th>
	                            <th>작성일</th>
	                            <th>수정일</th>
	                        </tr>
	                    </thead>
	                    
	                    <c:forEach items="${list}" var="board">
	                    
		                    <tr>
		                    	<td><c:out value="${board.bno}"/></td>
		                    	<td><a class="move" href='<c:out value="${board.bno}"/>'><c:out value="${board.title}"/></a></td>
		                    	<td><c:out value="${board.writer}"/></td>
		                    	<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate}"/></td>
		                    	<td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updateDate }"/></td>
		                    </tr>
	                    </c:forEach>
	                </table>
	               
	                <!--검색조건처리-->
	                <!--작은 따옴표, 큰따옴표 속성값에 스타일은 자유-->
	                <div class='row'>
	                 <div class="col-lg-12">
	                	<form id='searchForm' action="/board/list" method="get">
	                	<select name='type'>
	                				<option value='' <c:out value="${pageMaker.cri.type == null ? 'selected' : ''}"/>>--</option>
	                				<option value='T' <c:out value="${pageMaker.cri.type eq 'T' ? 'selected' : ''}"/>>제목</option>
	                				<option value='C' <c:out value="${pageMaker.cri.type eq 'C' ? 'selected' : ''}"/>>내용</option>
	                				<option value='W' <c:out value="${pageMaker.cri.type eq 'W' ? 'selected' : ''}"/>>작성자</option>
	                				<option value='TC' <c:out value="${pageMaker.cri.type eq 'TC' ? 'selected' : ''}"/>>제목 or 내용</option>
	                				<option value='TW' <c:out value="${pageMaker.cri.type eq 'TW' ? 'selected' : ''}"/>>제목 or 작성자</option>
	                				<option value='TWC' <c:out value="${pageMaker.cri.type eq 'TWC' ? 'selected' : ''}"/>>제목 or 내용 or 작성자</option>
	                			</select>
	                			<input type='text' name='keyword' value='<c:out value="${pageMaker.cri.keyword}"/>'/>
	                			<input type='hidden' name='pageNum' value='<c:out value="${pageMaker.cri.pageNum}"/>'/>
	                			<input type='hidden' name='amount' value='<c:out value="${pageMaker.cri.amount}"/>'/>
						<button class='btn btn-default'>검색</button>                	
	                	</form>
	                 </div>
	                </div>
	               
	                <!-- /.table-responsive -->
	                <!-- 패이징 처리 prev, next 처리 / <a>태그 직접처리 방식이 아닌 폼태그 활용-->
	                <!-- 페이지 번호 클릭시 이동할때도 검색 조건과 키워드는 같이 전달되어야 한다  -->
	                <div class="pull-right">
	                	<ul class="pagination">
	                		<c:if test="${pageMaker.prev}">
	                			<li class="paginate_button previous">
	                				<a href="${pageMaker.startPage -1}">이전</a>
	                			</li>
	                		</c:if>
	                		
	                		<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage }">
	                				<li class="paginate_button ${pageMaker.cri.pageNum == num ? "active": ""}">
	                				<a href="${num }">${num }</a>
	                				</li>
	                		</c:forEach>
	                		
	                		<c:if test="${pageMaker.next}">
	                			<li class="paginate_button next">
	                				<a href="${pageMaker.endPage +1 }">다음</a>
	                			</li>
	                		</c:if>
	                	</ul>
	                	   	<form id="actionForm" action="/board/list" method="get">
	                		<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum }">
	                		<input type="hidden" name="amount" value="${pageMaker.cri.amount }">
	                		<input type="hidden" name="type" value='<c:out value="${pageMaker.cri.type }"/>'>
	                		<input type="hidden" name="keyword" value='<c:out value="${pageMaker.cri.keyword }"/>'>
	                		</form>
	                </div>
	                <!-- end Pagination -->
	                <!-- 모달창으로 피드백 처리하기 -->
	                <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	                	<div class="modal-dialog">
	                		<div class="modal-content">
	                			<div class="modal-header">
	                				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                				<h4 class="modal-title" id="myModalLabel">Modal title</h4>
	                			</div>
	                			<div class="modal-body">처리가 완료되었습니다.</div>
	                			<div class="modal-footer">
	                				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	                				<button type="button" class="btn btn-primary">Save changes</button>
	                			</div>
	                		</div>
	                	</div>
	                
	                </div>
	            </div>
	            <!-- /.panel-body -->
	        </div>
	        <!-- /.panel -->
	    </div>
	    <!-- /.col-lg-12 -->
	</div>
            
            
<%@ include file="../includes/footer.jsp" %>