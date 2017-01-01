<script type="text/javascript">
	$(document).ready(function() { 
		new transactionsList.Views.DashboardView();
	});
</script>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-6" >
			<div style="padding-top: 5px;">
				<div class="nav-collapse sidebar-nav">
					<h3>Monthly totals</h3>
					<div class="whitecard">
						<canvas id="monthly-totals-chart" style="padding-right: 15px;"></canvas>
					</div>
				</div>
			</div>
		</div>
		
		<div class="col-md-6" >
			<div style="padding-top: 5px;">
				<div class="nav-collapse sidebar-nav">
					<h3>Last 30 days</h3>
					<div class="whitecard">
						<canvas id="last-thirty-totals-chart" style="padding-right: 15px;"></canvas>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<div style="padding-top: 5px;">
	<div class="nav-collapse sidebar-nav">
		<h3>Top 3 outgoing transactions</h3>
		<div class="whitecard">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Description</th>
						<th>Amount</th>
						<th>Date</th>
						<th>Account</th>
					</tr>
				</thead>
				<tbody id="biggest-transactions-list">
				</tbody>
			</table>
		</div>
	</div>
</div>

<div style="padding-top: 5px;">
	<div class="nav-collapse sidebar-nav">
		<h3>Latest transactions</h3>
		<div class="whitecard">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Description</th>
						<th>Amount</th>
						<th>Date</th>
						<th>Account</th>
					</tr>
				</thead>
				<tbody id="your-transactions-list">
				</tbody>
			</table>
			<a href="transactions"  class="btn btn-primary">View all transactions</a>
		</div>
	</div>
</div>


<script type="text/template" id="transaction-row-template">
	<td>
		<span class="label label-{{# if (transactionType=='CREDIT' ){ }}success{{# }else{ }}warning{{# } }}">{{ transactionType }}</span>
		{{ description }}
	</td>
	<td>£{{ Math.abs( amount ) }}</td>
	<td>{{ dateOfTransaction }}</td>
	<td>{{ bankAccount.alias }}</td>
</script>