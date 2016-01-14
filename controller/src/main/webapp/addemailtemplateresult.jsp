<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="BankBundle" />

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="addemailtemplateresultpagetitle" /></title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="resources/css/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="resources/css/bootstrap-theme.min.css">
    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Latest compiled JavaScript -->
    <script src="resources/js/bootstrap.min.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<!-- Modal -->
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><fmt:message key="addmodalheader" /></h4>
            </div>
            <div class="modal-body">
                <p><b><fmt:message key="emailtemplatesuccess" /></b></p>
            </div>
            <div class="modal-footer">
                <button id="okButton" type="button" class="btn btn-success" data-dismiss="modal">OK</button>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">
    document.getElementById("okButton").onclick = function () {
        location.href = "/viewemailtemplates";
    };
</script>

<script type="text/javascript">
    $(window).load(function(){
        $('#myModal').modal({backdrop: "static"});
    });
</script>
</body>
</html>