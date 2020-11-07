<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
       <c:if test="${flush != null}">
           <div id="flush_success">
               <c:out value="${flush}"></c:out>
           </div>
       </c:if>
       <h2>スケジュール 一覧</h2>
       <table id="schdule_list">
           <tbody>
               <tr>
                   <th class=schedule_name>名前</th>
                   <th class=schedule_date>日付</th>
                   <th class=schedule_title>タイトル</th>
                   <th class=schedule_action>操作</th>
               </tr>
               <c:forEach var="schedule" items="${schedules}" varStatus="status">
                   <tr class="row${status.count % 2}">
                       <td class="schedule_name"><c:out value="${schedule.account.name}" /></td>
                       <td class="schedule_date"><fmt:formatDate value="${schedule.schedule_date}" pattern="yyyy-MM-dd"/></td>
                       <td class="schedule_title">${schedule.title}</td>
                       <td class="schedule_action"><a href="<c:url value='/schedules/show?id=${schedule.id}'/>">詳細を見る</a></td>
                   </tr>
               </c:forEach>
           </tbody>
       </table>

       <div id="pagination">
        (全${schedules_count}件) <br />
        <c:forEach var="i" begin="1" end="${((schedules_count) / 15) + 1}" step="1">
            <c:choose>
                <c:when test="${i == page}">
                    <c:out value="${i}" />&nbsp;
                </c:when>
                <c:otherwise>
                <a href="<c:url value='/schedules/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                </c:otherwise>
            </c:choose>
        </c:forEach>
       </div>
       <p><a href="<c:url value='/schedules/new' />">新規タスクを追加</a></p>

    </c:param>
</c:import>