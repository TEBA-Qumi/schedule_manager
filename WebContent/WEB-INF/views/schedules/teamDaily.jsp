<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h3>スケジュール一覧</h3><br>
        <table id="schedule_list">
                <tbody>
                    <tr>
                        <th class="schedule_name">名前</th>
                        <th class="schedule_date">日付</th>
                        <th class="schedule_title">タイトル</th>
                        <th class="schedule_action">操作</th>
                    </tr>
                    <c:forEach var="schedule" items="${schedules}" varStatus="status">
                        <tr class="row${status.count % 2}">
                            <td class="schedule_name"><c:out value="${schedule.account.name}" /></td>
                            <td class="schedule_date"><fmt:formatDate value='${schedule.schedule_date}' pattern='yyyy-MM-dd' /></td>
                            <td class="schedule_title">${schedule.title}</td>
                            <td class="schedule_action"><a href="<c:url value='/schedules/show?id=${schedule.id}' />">詳細を見る</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <p><a href="<c:url value='/schedules/new' />">スケジュールの追加</a></p>

        <p><a href="<c:url value="/" />">一覧に戻る</a></p>
    </c:param>
</c:import>