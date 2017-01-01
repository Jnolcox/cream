<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">$(document).ready(function() { new transactionsList.Views.TransactionsPageView(); });</script>

<div id="transactions-page-body" style="padding-top: 5px;">
	<div class="nav-collapse sidebar-nav">
		<h3>Latest transactions</h3>
		<a href="#" id="toggle-filter-control">filter transactions</a>
		<div id="filter-control" style="display: none; margin-bottom:5px;" class="whitecard">
			<tiles:insertTemplate template="/WEB-INF/views/common-loggedin/part_transactionsFilterControl.jsp" />
		</div>
		<div class="whitecard">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Type</th>
						<th>Description</th>
						<th>Amount</th>
						<th>Date</th>
						<th>Account</th>
					</tr>
				</thead>
				<tbody id="your-transactions-list">
				</tbody>
			</table>
		</div>
	</div>
</div>


<script type="text/template" id="transaction-row-template">
	<td>
		<span class="label label-{{# if (transactionType=='CREDIT' ){ }}success{{# }else{ }}warning{{# } }}">{{ transactionType }}</span>
	</td>
	<td>
		{{ description }} <br/>
		<small>
			<em>Category:</em> 
				{{# if ( transactionGroup.category ){ }}
					<span class="label label-primary">
						{{ transactionGroup.category }} 
						<i class="fa fa-fw fa-pencil"></i>
					</span>
				{{# }else{ }} 
					<select id="tag-dropdown">
						<option></option>
						<c:forEach var="tag" items="${tags}">
							<option>${tag}</option>
						</c:forEach>
					</select>
				{{# } }}

			<em>Tags:</em> 
				{{# _.each(transactionGroup.tags, function( tag ) {  }} 
					<span class="label label-info">{{ tag }} <i class="fa fa-fw fa-times"></i></span> 
				{{#  });  }}
				<span class="label label-info">add <i class="fa fa-fw fa-plus"></i></span>
		</small>
	</td>
	<td>£{{ Math.abs( amount ) }}</td>
	<td>{{ dateOfTransaction }}</td>
	<td>{{ bankAccount.alias }}</td>
</script>