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
        </div>
        <div class="top">
            <h3>【スケジュール　一覧】</h3>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-md-6">
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
                                                <td class="schedule_date">
                                                    <a href="
                                                        <c:url value='/schedules/daily?schedule_date=${schedule.schedule_date}'>
                                                            <c:param name='id' value='${schedule.share_flag}' />
                                                        </c:url>
                                                    "><fmt:formatDate value='${schedule.schedule_date}' pattern='yyyy-MM-dd' /></a>
                                                </td>
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
                    <p><a href="<c:url value='/schedules/new' />">スケジュールの追加</a></p>
                </div>

                <!--  カレンダー -->
                <div class="col-md-5">
                    <h3>
                    <!-- 先月 -->
                    <a href="
                        <c:url value='/teams/topPage?month=${prevMonth}'>
                            <c:param name='id' value='${id}'/>
                        </c:url>
                    ">＜</a>
                    <!-- 今月 -->
                    <c:choose>
                            <c:when test="${month % 13 == 0}">${year}年1月</c:when>
                            <c:otherwise>
                                <c:if test="${month != 12}">${year}年${month % 12}月</c:if>
                                <c:if test="${month == 12}">${year}年${month % 13}月</c:if>
                            </c:otherwise>
                    </c:choose>
                    <!-- 来月 -->
                    <a href="
                        <c:url value='/teams/topPage?month=${nextMonth}' >
                            <c:param name='id' value='${id}' />
                        </c:url>
                    ">＞</a></h3>

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
                                    <forEach var="schedule" items="${schedules}" varStatus="status">
                                        <td class="calendar" align="center">
                                        <a href="
                                           <c:url value='/schedules/daily?schedule_date=${year}-${month % 12}-${day}' >
                                               <c:param name='id' value='${id}'/>
                                           </c:url>
                                       ">${day}</a>
                                        </td>
                                        <c:if test="${(status.count%7)==0}">
                                          </tr>
                                          <tr>
                                        </c:if>
                                    </forEacH>
                                </c:forEach>
                            </tr>
                        </tbody>
                    </table>
                </div>
            <!-- class=row -->
            </div>
        <!-- class=container -->
        </div>

        <div id="topPage">
            <p><a href="<c:url value='/' />">個人ページに戻る</a></p>
        </div>

    </c:param>
</c:import>