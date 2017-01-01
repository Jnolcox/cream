<form class="form-horizontal" role="form">
	<div class="form-group">
		<label for="filter-transactiontype" class="col-sm-2 control-label">Transaction type</label>
		<div class="col-sm-10">
			<select id="filter-transactiontype"  class="form-control" >
				<option></option>
				<option value="CREDIT">Credit</option>
				<option value="DEBIT">Debit</option>
			</select>
		</div>
	</div>
	<div class="form-group">
		<label for="filter-description" class="col-sm-2 control-label">Description</label>
		<div class="col-sm-10">
			<input id="filter-description" class="form-control" type="text" placeholder="Description">
		</div>
	</div>
	<div class="form-group">
		<label for="filter-amountoperator" class="col-sm-2 control-label">Amount</label>
		<div class="col-sm-2">
			<select id="filter-amountoperator"  class="form-control" >
				<option value="GTE">Greater than</option>
				<option value="LTE">Less than</option>
				<option value="EQ">Equals</option>
			</select>
		</div>
		<div class="col-sm-8">
			<div class="input-group">
				<div class="input-group-addon">£</div>
				<input id="filter-amountvalue" class="form-control" type="text" placeholder="Amount">
			</div>
		</div>
	</div>
	<div class="form-group">
		<label for="filter-account" class="col-sm-2 control-label">Account</label>
		<div class="col-sm-10">
			<select id="filter-account"  class="bank-account-dropdown form-control" >
				<option></option>
			</select>
		</div>
	</div>
</form>