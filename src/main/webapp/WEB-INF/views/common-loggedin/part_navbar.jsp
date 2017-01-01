<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>

<!-- Fixed navbar -->
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid" style="padding-right: 15px;">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="<c:url value='/' />">CREAM</a>
		</div>
		
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li <c:if test="${currentpage == 'dashboard'}">class="selected"</c:if>><a href="<c:url value='/' />">Dashboard</a></li>
				<li class="<c:if test="${currentpage == 'transactions'}">selected </c:if>dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">Transactions <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="<c:url value='/transactions' />">All</a></li>
						<li><a href="<c:url value='/transactions/graphs' />">Graphs</a></li>
					</ul>
				</li>
				<li>
					<a href="#" onclick="document.getElementById('logoutform').submit();" >Log out</a>
					<form id="logoutform" action="<c:url value='/logout' />" method="POST"><input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/></form>
				</li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
</div>