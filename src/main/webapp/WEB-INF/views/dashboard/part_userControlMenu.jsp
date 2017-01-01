<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="sidebar-left" style="padding-top: 5px;">
	<div class="nav-collapse sidebar-nav">
		<h3>Setup</h3>
		<ul class="nav nav-tabs nav-stacked main-menu">			
			<!-- ADD ACCOUNT -->
			<li class="google-connect">
				<c:if test="${personCreated == true}">
					<a data-toggle="modal" data-target=".add-bank-account-modal">
						<i class="fa fa-lg fa-fw fa-credit-card"></i> Add bank account
					</a>
				</c:if>
			</li>
			
			<!-- UPLOAD STATEMENT -->
			<li class="linkedin-connect">
				<c:if test="${accountCreated == true}">
					<a data-toggle="modal" data-target=".upload-statement-modal">
						<i class="fa fa-lg fa-fw fa-cloud-upload"></i> Upload statement
					</a>
				</c:if>
			</li>
		</ul>
	</div>
</div>


<!-- Modal content -->
<div id="create-new-account"  class="modal fade add-bank-account-modal" tabindex="-1" role="dialog" aria-labelledby="Add bank account" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form style="padding:15px;">
				<div class="form-group">
					<label for="new-account-alias">Account alias</label> 
					<input type="text" class="form-control" id="new-account-alias" placeholder="Account alias">
				</div>
				<div class="form-group">
					<label for="new-account-provider">Bank provider</label> 
					<select class="form-control" id="new-account-provider">
						<c:forEach var="prov" items="${providers}">
							<option>${prov}</option>
						</c:forEach>
					</select>
				</div>
				<button id="submit-create-account-form" type="submit" class="btn btn-primary">Add account</button>
			</form>
		</div>
	</div>
</div>
<div id="upload-statement"  class="modal fade upload-statement-modal" tabindex="-1" role="dialog" aria-labelledby="Upload statement" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<form style="padding:15px;" action="upload/statement" method="post" enctype="multipart/form-data" >
				<div class="form-group">
					<label for="new-account-alias">Select file</label>
					<input type="file" class="filestyle" name="statement" data-buttonName="btn-primary"> 
				</div>
				<div class="form-group">
					<label for="bank-account">Account</label> 
					<select class="form-control bank-account-dropdown" name="bankAccount" id="bank-account-dropdown">
					</select>
				</div>
				<button id="submit-upload-statement-form" type="submit" class="btn btn-primary">Upload</button>
			</form>
		</div>
	</div>
</div>

<!-- Templates -->
<script type="text/template" id="account-dropdown-template">{{ alias }}</script>