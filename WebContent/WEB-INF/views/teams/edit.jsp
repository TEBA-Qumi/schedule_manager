<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${team != null}">
                <h2>「${team.name}」 のチーム情報　編集ページ</h2>
                <p>（パスワードは変更する場合のみ入力してください）</p>
                <form method="POST" action="<c:url value='/teams/update' />">
                    <c:import url="_form.jsp" />
                </form>

            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>


        <p><a href="<c:url value='/' />">一覧に戻る</a></p>
        <p><a href="#" onclick="confirmDestroy();">このチームから退出する</a></p>
        <form method="POST" action="${pageContext.request.contextPath}/teams/destroy">
            <input type="hidden" name="_token" value="${_token}" />
        </form>
        <script>
        function confirmDestroy() {
            if(confirm("本当に退出してよろしいですか？")) {
                document.forms[1].submit();
            }
        }
        </script>
    </c:param>
</c:import>