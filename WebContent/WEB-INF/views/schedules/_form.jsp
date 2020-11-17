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
<label for="schedule_date">日付</label><br />
<input type="date" name="schedule_date" value="<fmt:formatDate value='${schedule.schedule_date}' pattern='yyyy-MM-dd' />" />
<br /><br />

<label for="name">名前</label><br />
<c:out value="${sessionScope.login_account.name}" />
<br /><br />

<label for="title">タイトル</label><br />
<input type="text" name="title" value="${schedule.title}" />
<br /><br />

<label for="content">内容</label><br />
<textarea name="content" rows="10" cols="50">${schedule.content}</textarea>
<br /><br />

<label for="share_flag">公開範囲</label><br />
<select name="share_flag">
    <option value="0"<c:if test="${schedule.share_flag == 0}"> selected</c:if>>private</option>
    <c:forEach var="team" items="${teams}" varStatus="status">
        <option value="${team.team_Id.id}"<c:if test="${schedule.share_flag == team.team_Id.id}"> selected</c:if>>${team.team_Id.name}</option>
    </c:forEach>
</select>
<br /><br />

<select name="finish_flag">
    <option value="0"<c:if test="${schedule.finish_flag == 0}"> selected</c:if>>未完了</option>
    <option value="1"<c:if test="${schedule.finish_flag == 1}"> selected</c:if>>完了済</option>
</select>
<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">投稿</button>