<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
</head>
<body>
<h2>CommentTest</h2>
comment: <input type="text" name="comment"><br>
<button id="modBtn" type="button">수정완료</button>
<button id="sendBtn" type="button">등록</button>
<div id="commentList"></div>
<div id="replyForm" style="display:none">
    <%--  display를 none으로 하면서 안보이게 설정  --%>
    <%--  답글 버튼을 누르면 해당란에서 보이게 변경  --%>
    <input type="text" name="replyComment">
    <button id="wrtRepBtn" type="button">등록</button>
</div>
<script>
    // CommentController와 비교하면서 보자!

    let bno = 1095;
    let showList = function (bno) {
        $.ajax({
            type: 'GET',       // 요청 메서드
            url: '/ch4/comments?bno=' + bno,  // 요청 URI
            success: function (result) {
                $("#commentList").html(toHTML(result));    // 서버로부터 응답이 도착하면 호출될 함수
            },
            error: function () {
                alert("error")
            } // 에러가 발생했을 때, 호출될 함수
        }); // $.ajax()
    }

    let toHTML = function (comments) {   // 값은 들어오는데 문자열이 아니라 보여주지 못함. 문자열로 변경
        let tmp = "<ul>";

        comments.forEach(function (comment) {
            tmp += '<li data-cno = ' + comment.cno
            tmp += ' data-pcno = ' + comment.pcno
            tmp += ' data-bno = ' + comment.bno + '>'
            if (comment.cno != comment.pcno) {
                tmp += "->";
            }
            tmp += ' commenter = <span class = "commenter">' + comment.commenter + '</span>'
            tmp += ' comment = <span class = "comment">' + comment.comment + '</span>'
            tmp += ' up_date' + comment.up_date
            tmp += '<button class = "modBtn">수정</button>'
            tmp += '<button class = "delBtn">삭제</button>'
            tmp += '<button class = "replyBtn">답글</button>'
            tmp += '</li>'
        });

        return tmp + '</ul>';
    }

    // main함수 느낌
    $(document).ready(function () {
        // 시작하면서 댓글 보여주기
        showList(bno);

        // 답글 누르기
        $("#commentList").on("click", ".replyBtn", function () {
            // 1. replyForm을 옮기고
            $("#replyForm").appendTo($(this).parent());
            // 2. 답글을 입력할 폼을 보여줌
            $("#replyForm").css("display", "block");
        });

        // 답글 추가
        $("#wrtRepBtn").click(function () {
            let comment = $("input[name=replyComment]").val();
            let pcno = $("#replyForm").parent().attr("data-pcno");

            if (comment.trim() == '') {
                alert("댓글을 입력해주세요");
                $("input[name=replyComment]").focus();
                return;
            }
            $.ajax({
                type: 'POST',       // 요청 메서드
                url: '/ch4/comments?bno=' + bno,  // 요청 URI
                headers: {"content-type": "application/json"}, // 요청 헤더
                // 서버로 전송할 데이터. stringify()로 직렬화 필요.
                data: JSON.stringify({pcno: pcno, bno: bno, comment: comment}),
                success: function (result) {
                    alert(result);
                    showList(bno);
                },
                error: function () {
                    alert("error")
                } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()

            // 초기화 작업 필요
            $("#replyForm").css("display", "none"); // 안보이게
            $("input[name=replyComment]").val('');  // 빈상태로
            $("#replyForm").appendTo("body");       // 원래 자리로
        });

        // 수정 누르기
        $("#commentList").on("click", ".modBtn", function () {
            let cno = $(this).parent().attr("data-cno");
            let comment = $("span.comment", $(this).parent()).text();

            // 1. comment의 내용을 input에 뿌려주기
            $("input[name=comment]").val(comment);
            // 2. cno전달하기
            $("#modBtn").attr("data-cno", cno);
        });

        // 수정
        $("#modBtn").click(function () {
            let comment = $("input[name=comment]").val();
            let cno = $(this).attr("data-cno");

            if (comment.trim() == '') {
                alert("댓글을 입력해주세요");
                $("input[name=comment]").focus();
                return;
            }
            $.ajax({
                type: 'PATCH',       // 요청 메서드
                url: '/ch4/comments/' + cno,  // 요청 URI
                headers: {"content-type": "application/json"}, // 요청 헤더
                // 서버로 전송할 데이터. stringify()로 직렬화 필요.
                data: JSON.stringify({cno: cno, comment: comment}),
                success: function (result) {
                    alert(result);
                    showList(bno);
                },
                error: function () {
                    alert("error")
                } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()
        });

        // 삭제
        // $("#delBtn").click(function () {
        // commentList안에 있는 delBtn에 클릭 이벤트를 거는 행위
        $("#commentList").on("click", ".delBtn", function () {
            let cno = $(this).parent().attr("data-cno");
            let bno = $(this).parent().attr("data-bno");
            // alert("delBtn click");
            $.ajax({
                type: 'DELETE',       // 요청 메서드
                url: '/ch4/comments/' + cno + '?bno=' + bno,  // 요청 URI
                success: function (result) {
                    alert(result)
                    showList(bno);
                },
                error: function () {
                    alert("error")
                } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()
        });

        // 등록
        $("#sendBtn").click(function () {
            let comment = $("input[name=comment]").val();

            if (comment.trim() == '') {
                alert("댓글을 입력해주세요");
                $("input[name=comment]").focus();
                return;
            }
            $.ajax({
                type: 'POST',       // 요청 메서드
                url: '/ch4/comments?bno=' + bno,  // 요청 URI
                headers: {"content-type": "application/json"}, // 요청 헤더
                // 서버로 전송할 데이터. stringify()로 직렬화 필요.
                data: JSON.stringify({bno: bno, comment: comment}),
                success: function (result) {
                    alert(result);
                    showList(bno);
                },
                error: function () {
                    alert("error")
                } // 에러가 발생했을 때, 호출될 함수
            }); // $.ajax()
        });
    });
</script>
</body>
</html>