<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<div class="container-fluid" style="padding: 15px;">

	<div class="col-md-2" style="padding-left: 0px;">
		<tiles:insertAttribute name="use-control-menu" />
		<tiles:insertAttribute name="your-accounts" />
	</div>
	<div class="col-md-10" >
		<tiles:insertAttribute name="page-body" />
	</div>
</div>