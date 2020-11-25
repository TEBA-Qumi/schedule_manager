<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>スケジュール管理アプリ</title>
        <link rel="stylesheet" href="<c:url value='/CSS/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/CSS/style.css' />">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <div id="header_menu">
                    <h1><a href="<c:url value='/' />">スケジュール管理システム</a></h1>&nbsp;&nbsp;&nbsp;
                </div>
                <c:if test="${sessionScope.login_account != null}">
                    <div id="account_name">
                        <c:out value="${sessionScope.login_account.name}" />&nbsp;さん&nbsp;&nbsp;&nbsp;
                        <a href="<c:url value='/accounts/show?id=${login_account.id}' />">アカウント詳細</a>&nbsp;&nbsp;&nbsp;
                        <a href="<c:url value='/logout' />">ログアウト</a>
                    </div>
                </c:if>
            </div>
            <div id="content">
                ${param.content}
            </div>
            <div id="footer">
                by Takumi Sato.
            </div>
        </div>
    </body>
</html>