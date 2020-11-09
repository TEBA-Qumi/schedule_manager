<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label for="code">アカウント番号</label><br />
<input type="text" name="code" value="${account.code}" />
<br /><br />

<label for="name">名前</label><br />
<input type="text" name="name" value="${account.name}" />
<br /><br />

<label for="password">パスワード</label><br />
<input type="password" name="password" />
<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">登録</button>