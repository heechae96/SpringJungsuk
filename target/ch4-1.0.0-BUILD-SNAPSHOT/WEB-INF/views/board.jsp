<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>fastcampus</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <%-- jquery를 사용하기 위해 --%>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">fastcampus</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/board/list'/>">Board</a></li>
        <li><a href="<c:url value='/login/login'/>">login</a></li>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
        <li><a href=""><i class="fas fa-search small"></i></a></li>
    </ul>
</div>
<div style="text-align:center">
    <h2>게시물 읽기</h2>
    <form action="" id="form">
        <input type="text" name="bno" value="${boardDto.bno}" readonly="readonly">
        <input type="text" name="title" value="${boardDto.title}" readonly="readonly">
        <textarea name="content" id="" cols="30" rows="10" readonly="readonly">${boardDto.content}</textarea>
        <button type="button" id="writeBtn" class="btn">등록</button>
        <button type="button" id="modifyBtn" class="btn">수정</button>
        <button type="button" id="removeBtn" class="btn">삭제</button>
        <button type="button" id="listBtn" class="btn">목록</button>
    </form>
</div>
<script>
    $(document).ready(function(){   // main함수로 생각!
        $('#listBtn').on("click", function (){
            // 버튼이 적용되었는지 이와 같은 방법으로 확인하는게 중요!
            // alert("listBtn click")
            // Get방식
           location.href = "<c:url value='/board/list'/>?page=${page}&pageSize=${pageSize}";
        });

        $('#removeBtn').on("click", function (){
            // alert("removeBtn click")
            // Post방식
            if(!confirm("정말로 삭제하시겠습니까?")){  // 확인 누르면 빠져나가면서 아래 문장들이 실행, 취소 누르면 종료
                return;
            }
            let form = $('#form');
            form.attr("action", "<c:url value='/board/remove'/>?page=${page}&pageSize=${pageSize}");
            form.attr("method", "post");
            form.submit();
        });
    });
</script>
</body>
</html>