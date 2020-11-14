<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>スケジュール管理システムへようこそ</h2>
        <h3>【スケジュール　一覧】</h3>
        <c:if test="${sessionScope.login_account != null}">
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
                            <td class="schedule_date"><a href="<c:url value='/schedules/daily?schedule_date=${schedule.schedule_date}'/>"><fmt:formatDate value='${schedule.schedule_date}' pattern='yyyy-MM-dd' /></a></td>
                            <td class="schedule_title">${schedule.title}</td>
                            <td class="schedule_action">
                                <c:choose>
                                <c:when test="${schedule.finish_flag == 1}">
                                    (完了済み)<br>
                                    <a href="<c:url value='/schedules/show?id=${schedule.id}'/>">詳細を見る</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/schedules/show?id=${schedule.id}'/>">詳細を見る</a>
                                </c:otherwise>
                            </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <div id="pagination">
            （全 ${schedules_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((schedules_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/schedules/new' />">スケジュールの追加</a></p>

        <h3>${year}年${month}月</h3>
        <table id="calendar">
            <c:forEach var="cal" begin="1" end="${date}" step="1">
                <tr>
                    <td class="calendar"><a href="<c:url value='/schedules/daily?schedule_date=${year}-${month}-${cal}' />">${cal}</a></td>
                </tr>
            </c:forEach>
        </table>

        <h3>チーム一覧</h3>
        <table>
            <tbody>
                <c:forEach var="team" items="${teams}" varStatus="status">
                     <tr class="row${status.count % 2}">
                         <td class="team_name"><c:out value="${team.name}" /></td>
                         <td> <a href="<c:url value='/teams/show?id=${team.id}'/>">詳細を見る</a></td>
                     </tr>
                 </c:forEach>
            </tbody>
        </table>

        <p><a href="<c:url value='/teams/new' />">チームを作成</a></p>

    </c:param>
</c:import>