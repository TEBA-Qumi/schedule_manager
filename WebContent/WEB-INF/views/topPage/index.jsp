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
        <div class="top">
            <h2 class="top">スケジュール管理システムへようこそ</h2>
            <h3 class="top">【スケジュール　一覧】</h3>
        </div>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-6">
                    <c:if test="${sessionScope.login_account != null}">
                        <!-- スケジュールを表示 -->
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
                                                <a href="<c:url value='/schedules/show?id=${schedule.id}'/>">詳細</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="<c:url value='/schedules/show?id=${schedule.id}'/>">詳細</a>
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
                    <div class="add">
                        <p><a href="<c:url value='/schedules/new' />">スケジュールの追加</a></p>
                    </div>
                </div>

                <div class="col-md-5">
                    <!-- 年と月を表示 -->
                    <h3><a href="<c:url value='/?month=${prevMonth}'/>">＜</a>
                        <c:choose>
                            <c:when test="${month % 13 == 0}">${year}年1月</c:when>
                            <c:otherwise>
                                <c:if test="${month != 12}">${year}年${month % 12}月</c:if>
                                <c:if test="${month == 12}">${year}年${month % 13}月</c:if>
                            </c:otherwise>
                        </c:choose>
                    <a href="<c:url value='/?month=${nextMonth}' />">＞</a></h3>

                    <!-- カレンダー -->
                    <table id="calendar">
                         <thead>
                            <tr>
                               <th style="text-align: center; color: red "><b>日</b></th>
                               <th style="text-align: center;"><b>月</b></th>
                               <th style="text-align: center;"><b>火</b></th>
                               <th style="text-align: center;"><b>水</b></th>
                               <th style="text-align: center;"><b>木</b></th>
                               <th style="text-align: center;"><b>金</b></th>
                               <th style="text-align: center; color: blue;"><b>土</b></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <c:forEach var="day" items="${week}" varStatus="status">
                                    <td class="calendar" align="center">
                                        <a href="<c:url value='/schedules/daily?schedule_date=${year}-${month % 12}-${day}' />">${day}</a>
                                    </td>
                                    <c:if test="${(status.count%7)==0}">
                                      </tr>
                                      <tr>
                                    </c:if>
                                </c:forEach>
                            </tr>
                        </tbody>
                    </table>
                </div>
            <!-- class=row -->
            </div>
        <!-- class=container -->
        </div>


        <!-- チームを表示 -->
        <div id = "team">
            <h3>チーム一覧</h3>
            <table>
                <tr>
                    <th class="team_name">チーム名</th>
                    <th class="team_action">操作</th>
                </tr>
                <c:forEach var="team" items="${teams}" varStatus="status">
                    <tr>
                        <td class="team_name"><a href = "<c:url value='/teams/topPage?id=${team.team_Id.id}' />"><c:out value="${team.team_Id.name}" /></a></td>
                        <td class="team_action"><a href = "<c:url value='/teams/edit?id=${team.team_Id.id}' />">チーム情報を編集する</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <div class=create>
            <p><a href="<c:url value='/teams/new' />">チームを作成</a></p>
            <p><a href="<c:url value='/teams/join' />">チームに参加</a></p>
        </div>

    </c:param>
</c:import>