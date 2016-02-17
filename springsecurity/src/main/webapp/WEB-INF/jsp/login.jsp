<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="include/head.jsp" %>
		
		<style type="text/css">
			.form-signin {
				max-width: 400px;
				padding: 15px;
				margin: 0 auto;
			}
			.fieldError{
				font-size: 10px;
				color: red;
			}
		</style>
	</head>
	<body>
	
		<%@ include file="include/navbar.jsp" %>
	
		<div class="container">

			<form name="login" method="POST" action="<c:url value='/login' />" class="form-signin">
		        <h2 class="form-signin-heading">Authentication...</h2>
		        
		        <hr />
		        
		        <c:if test="${not empty error}">
					<div class="alert alert-danger"><span class="glyphicon glyphicon-remove-sign"></span> ${error}</div>
				</c:if>
				 <c:if test="${not empty message}">
					<div class="alert alert-success"><span class="glyphicon glyphicon-ok-sign"></span> ${message}</div>
				</c:if>
				
				<div style="margin-bottom: 25px" class="input-group">
		         	<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
		        	<input type="text" id="username" name="username" class="form-control" placeholder="Username"/>                                        
		   		</div>
		   		
		        <div style="margin-bottom: 25px" class="input-group">
		      		<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
		     		<input type="password" id="password" name="password" class="form-control" placeholder="Password" required="required" />
		    	</div>
		
		        <button class="btn btn-lg btn-success btn-block" type="submit"><span class="glyphicon glyphicon-log-in"></span> Login</button>
			</form>
		</div>		

	</body>
</html>