<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style type="text/css">
.navbar-default .navbar-nav>.dropdown>.dropdown-menu>.active>a {
  background: #5cb85c !important;
}
</style>

<nav class="navbar navbar-default navbar-static-top">
	<div class="container">
		<div class="navbar-header">
		 	<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
		      <span class="sr-only">Toggle navigation</span>
		      <span class="icon-bar"></span>
		      <span class="icon-bar"></span>
		      <span class="icon-bar"></span>
		    </button>
			<a class="navbar-brand" href="#" style="color:#5cb85c;"><small>Sample</small> Spring Security</a>
		</div>
		
		<sec:authorize access="isAuthenticated()">
		
			<sec:authentication property="principal.class" var="usertype"/>
			<c:choose>
				<c:when test="${fn:contains(usertype, 'waffle.servlet.WindowsPrincipal')}">
					<sec:authentication property="principal.name" var="username"/>
				</c:when>
				<c:otherwise>
					<sec:authentication property="principal.username" var="username"/>
				</c:otherwise>
			</c:choose>
		
			<div id="navbar" class="navbar-collapse collapse">
			    <ul class="nav navbar-nav">
			    	<li class="dropdown">
			    		<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
	             			<span class="glyphicon glyphicon-leaf"></span> Spring <span class="caret"></span>
	             		</a>
	             		<ul class="dropdown-menu">
	             			<li><a href="http://projects.spring.io/spring-framework"><span class="glyphicon glyphicon-grain"></span> Spring MVC</a></li>
	             			<li><a href="http://projects.spring.io/spring-security"><span class="glyphicon glyphicon-lock"></span> Spring Security</a></li>
	             		</ul>
			    	</li>
			    	
					<li><a href="http://getbootstrap.com"><span class="glyphicon glyphicon-btc"></span> Twitter Bootstrap</a></li>
					<li><a href="https://nakupanda.github.io/bootstrap3-dialog"><span class="glyphicon glyphicon-modal-window"></span> Dialog plugin</a></li>
			    </ul>
				<ul class="nav navbar-nav navbar-right">
			    	<li class="dropdown">
			    		<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
	             			<span class="glyphicon glyphicon-user"></span> <c:out value="${username}" /> <span class="caret"></span>
	             		</a>
	             		<ul class="dropdown-menu">
	             			<sec:authorize access="hasRole('ADMIN')">
	             				<li><a href="#"><span class="glyphicon glyphicon-cog"></span> Configuration</a></li>
	             			</sec:authorize>
	             			<li><a href="<c:url value='/logout'/>"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
	             			<li role="separator" class="divider"></li>
                			<li class="dropdown-header">Granted authorities</li>
	             			<sec:authentication property="authorities" var="roles" scope="page" />
				      		<c:forEach var="role" items="${roles}">
						    	<li><a>${role}</a></li>
						    </c:forEach>
	             		</ul>
			    	</li>
				</ul>
	  		</div>
  		
  		</sec:authorize>
  		
	</div>
</nav>