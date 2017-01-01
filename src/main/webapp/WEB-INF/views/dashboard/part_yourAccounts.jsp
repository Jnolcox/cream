<script type="text/javascript">
	$(document).ready(function() { new accountsList.Views.BankAccounts(); });
</script>

<div id="sidebar-left" style="padding-top: 15px;">
	<div class="nav-collapse sidebar-nav">
		<h3>Your accounts</h3>
			<ul id="your-accounts-list" class="nav nav-tabs nav-stacked main-menu">			
			</ul>
	</div>
</div>



<script type="text/template" id="account-row-template">
	<a id="select-account"  {{# if( active ){ }}class="active"{{# } }}  href="javascript:void(0)">	
		<i class="fa fa-lg fa-fw fa-credit-card"></i> {{ alias }} ({{ bankProvider }})
	</a>
</script>