var accountsList = accountsList || { Models: {}, Collections: {}, Views: {} };
$(function(){
	accountsList.Models.AccountModel = Backbone.Model.extend( {
		defaults: function() {
			return {
				active: false
			};
		}
	});
	var AccountList = Backbone.Collection.extend({
		model: accountsList.Models.AccountModel,
		url: window.core.page.appRoot + 'api/accounts'
	});
	accountsList.Collections.Accounts = new AccountList();
	
	/**
	 * List of all accounts associated with user
	 */
	accountsList.Views.AccountRowView = Backbone.View.extend({
		tagName:'li',
		className:'google-connect',
		template: _.template( $('#account-row-template').html() ),
		events: { "click #select-account" : "selectRow" },
		initialize: function() {
			this.listenTo( this.model, 'change', this.render );
		},
		render: function() {
			this.$el.html( this.template( this.model.toJSON() ) );
			return this;
		},
		selectRow: function( e ) {
			if ( !this.model.get('active') ){
				this.model.set( {active: true});
			}else{
				this.model.set( {active: false});
			}
		}
	});
	
	/**
	 * View to drive accounts dropdowns
	 */
	accountsList.Views.AccountDropdownView = Backbone.View.extend({
		tagName:'option',
		template: _.template( $('#account-dropdown-template').html() ),
		initialize: function() {
			this.listenTo( this.model, 'change', this.render );
		},
		render: function() {
			this.$el.html( this.template( this.model.toJSON() ) );
			return this;
		}
	});
	
	
	
	/**
	 * Main controlling view that handles all Account related tasks & activity
	 */
	accountsList.Views.BankAccounts = Backbone.View.extend({
		el: $('#create-new-account'),
		events: { 'click #submit-create-account-form' : 'submitAdd' },

		initialize: function () {
			this.alias = this.$( '#new-account-alias' );
			this.bankProvider = this.$( '#new-account-provider' );

			//setup listeners for accounts list
			window.accountsList.Collections.Accounts.on('add', this.addOne, this);
			window.accountsList.Collections.Accounts.on('reset', this.addAll, this);
			window.accountsList.Collections.Accounts.on('add', this.render, this);

			//initial load of list of accounts on first page load
			window.accountsList.Collections.Accounts.fetch({reset: true});
		},

		// This is the overall accounts list view&form - rendering will be handled by other views
		render: function () {return this;},

		//smash the current list of accounts and reload from the loaded collection
		addAll: function() {
			this.$('#your-accounts-list').html('');
			accountsList.Collections.Accounts.each(this.addOne, this);
		},
		addOne: function( account ) {
			var view = new accountsList.Views.AccountRowView({ model: account });
			$('#your-accounts-list').append( view.render().el );
			var dropdown = new accountsList.Views.AccountDropdownView({ model: account });
			$('.bank-account-dropdown').append( dropdown.render().el );
		},
		
	    submitAdd: function(e) {
	        e.preventDefault();
	        accountsList.Collections.Accounts.create( { alias:this.alias.val(), bankProvider:this.bankProvider.val() } );
			this.alias.val('');
			this.bankProvider.val('');
	        $('#create-new-account').modal('hide');
	        return false;
	    }
	});
	
}());