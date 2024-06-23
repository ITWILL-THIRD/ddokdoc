<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>병원 공지사항</title>
<jsp:include page="../common/navigation.jsp"/>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
	#container { width: 800px; margin: auto; }
	h1, h3, p { text-align: center; }
	
	<%-- 목록 --%>
	table {
		width: 800px;
		border-collapse: collapse;
		margin-left:auto;margin-right:auto;
		margin-top: 23px;
	}
	
	table th, table td {
		position: static;
		text-align: center;
		margin: auto;
		padding: 5px;
		border: 1px #B9B9B9 solid;
		
		border-collapse: collapse;
	}
	
	table th:first-child,
	table td:first-child {
		border-left: 0;
	}

	table th:last-child,
	table td:last-child {
		border-right: 0;
	}

	th { 
		background-color: #E0EAF5;
	}
	
	.center { text-align: center; }
	.border-none, .border-none td { border: none; }
	
	.center {
		margin-bottom: 20px;
	}
	<%-- 제목 링크 --%>
	a {	
		font-weight: bold;
		color: #2C307D;
		text-decoration: none;	
	}
	
	a:hover {
		color:#FFA217;
	}
	
    
        /* 버튼 */
    .btn {
    	border-radius: 5px;
    	background-color: #2C307D;
    	padding: 7px 18px;
    	border: none;
    	cursor: pointer;
    	color: #FFFFFF;
    	text-decoration: none;
    	display: inline-block;
    	border: 1px solid #2C307D;
    	float: right;
    }
    
    .btn:hover {
    	background-color: #FFFFFF;
    	color: #2C307D;
    	border: 1px solid #2C307D;
    }
	

  hr {
  	border: 1px solid #2C307D;
  }
</style>
<script>
function insertNotice(hosIdx){
	//병원(기업)계정만 공지 작성
	//해당 병원 계정 일치 여부 확인 후, 공지 입력
	location.href = "../notice/insertProNotice.do?hosIdx=${hoUser.hosIdx}";
	//location.href = "../notice/insertProNotice.do?hosIdx=" + hosIdx;
}
function updateNotice(hosIdx){
	//병원(기업)계정만 공지 작성
	//해당 병원 계정 일치 여부 확인 후, 공지 입력
	location.href = "../notice/updateProNotice.do?hosIdx=${hoUser.hosIdx}";
	//location.href = "../notice/insertProNotice.do?hosIdx=" + hosIdx;
}
function deleteNotice(frm){
	if (confirm("공지사항을 삭제하시겠습니까?")) {
  	  //휴무 취소 함수 실행시키기
  	  alert("공지사항 삭제");
	  frm.submit();
	} else {
        return;
    }
	//병원(기업)계정만 공지 작성
	//해당 병원 계정 일치 여부 확인 후, 공지 입력
	//location.href = "../notice/insertProNotice.do?hosIdx=" + hosIdx;
}


</script>

</head>
<body>
<h1>공지사항</h1>
<%-- \${hosNoticeList } : ${hosNoticeList } --%>
<%-- \${hoUser } : ${hoUser } --%>
<%-- \${hoUser.hosIdx } : ${hoUser.hosIdx } --%>
	<div id="container">
		<hr>
		<a href="hoMyPage.do">병원 마이페이지 가기</a>
		<div>
			<c:choose>
				<c:when test="${not empty notice}">
					<input class="btn" type="button" value="공지 수정" data-hos-idx="${hosIdx}"
						onclick="updateNotice(this.dataset.hosIdx)">
					<form action="../notice/deleteNotice.do" method="post">
						<input type="hidden" name="hosIdx" value="${hoUser.hosIdx}">
						<input class="btn" type="button" value="공지 삭제" data-hos-idx="${hosIdx}"
							onclick="deleteNotice(this.form)">		
					</form>
				</c:when>
				<c:otherwise>
					<input class="btn" type="button" value="공지 등록" data-hos-idx="${hosIdx}"
						onclick="insertNotice(this.dataset.hosIdx)">
				</c:otherwise>
			</c:choose>
			
			<table border="">
			<c:choose>
				<c:when test="${not empty notice}">
					<tr>
						<th>작성일</th>
						<td>${notice.noticeDate }</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>${notice.noticeContent }</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<p>작성된 공지사항이 없습니다</p>
					</tr>
				</c:otherwise>
			</c:choose>
				
	<%-- 				<a href="../notice/insertProNotice.do?hosIdx=${hoUser.hosIdx}">공지 등록</a> --%>
			</table>
	</div>
	</div>
</body>
</html>