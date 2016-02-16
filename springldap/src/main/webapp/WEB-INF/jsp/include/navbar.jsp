<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
			<a class="navbar-brand" href="#" style="color:#5cb85c;"><small>Sample</small> Spring LDAP</a>
		</div>
			
		<div id="navbar" class="navbar-collapse collapse">
		    <ul class="nav navbar-nav">
		    	<li class="dropdown">
		    		<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
             			<span class="glyphicon glyphicon-leaf"></span> Spring <span class="caret"></span>
             		</a>
             		<ul class="dropdown-menu">
             			<li><a href="http://projects.spring.io/spring-framework"><span class="glyphicon glyphicon-grain"></span> Spring MVC</a></li>
             			<li><a href="http://projects.spring.io/spring-ldap"><span class="glyphicon glyphicon-tent"></span> Spring LDAP</a></li>
             		</ul>
		    	</li>
		    	
				<li><a href="http://getbootstrap.com"><span class="glyphicon glyphicon-btc"></span> Twitter Bootstrap</a></li>
				<li><a href="https://nakupanda.github.io/bootstrap3-dialog"><span class="glyphicon glyphicon-modal-window"></span> Dialog plugin</a></li>
		      	<li><a href="https://www.datatables.net/"><span class="glyphicon glyphicon-list-alt"></span> jQuery DataTable</a></li>
		    </ul>
			<ul class="nav navbar-nav navbar-right">
		    	<li class="dropdown">
		    		<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
             			<span class="glyphicon glyphicon-globe"></span> <s:message code="lang"/> <span class="caret"></span>
             		</a>
             		<ul class="dropdown-menu">
             			
             			<c:set var="cookielang" value="${empty param.lang ? cookie.lang.value : param.lang}"/>
             			<c:set var="lang" value="${empty cookielang ? 'fr' : cookielang}" />
             			
             			<li class="${lang == 'en' ? 'active' : ''}"><a href="?lang=en"><span class="glyphicon glyphicon-thumbs-up"></span> <s:message code="lang.en"/></a></li>
             			<li class="${lang == 'fr' ? 'active' : ''}"><a href="?lang=fr"><span class="glyphicon glyphicon-thumbs-down"></span> <s:message code="lang.fr"/></a></li>
             		</ul>
		    	</li>
			</ul>
  		</div>
	</div>
</nav>