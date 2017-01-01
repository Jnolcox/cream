<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div class="container-fluid" style="padding: 15px;">

	<div class="col-md-2" style="padding-left: 0px;">
		<tiles:insertAttribute name="use-control-menu" />
		<tiles:insertAttribute name="your-accounts" />
	</div>
	<div class="col-md-8" >
		<tiles:insertAttribute name="dashboard-body" />
	</div>
	<div class="col-md-2" >
		<tiles:insertAttribute name="account-alerts" />
	</div>
</div>