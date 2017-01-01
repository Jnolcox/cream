var transactionsList = transactionsList || { Models: {}, Collections: {}, Views: {}, Helpers: {} };
$(function(){
	/**
	 * List of all transactions for all accounts
	 */
	transactionsList.Models.TransactionModel = Backbone.Model.extend( { });
	var TransactionList = Backbone.Collection.extend({
		model: transactionsList.Models.TransactionModel,
		url: window.core.page.appRoot + 'api/transactions',
		filterTransaction: function( params ){
			return ( _(this.filter(function( transaction ) {
				return ( !params.transactionType || transaction.get("transactionType") == params.transactionType ) 
					&& ( !params.amount || ((params.amountOperator === "GTE" && Math.abs( transaction.get("amount") ) >= params.amount )
						|| (params.amountOperator === "LTE" && Math.abs( transaction.get("amount") ) <= params.amount )
						|| (params.amountOperator === "EQ" &&  Math.abs( transaction.get("amount") ) == params.amount )) )
					&& ( !params.accountName || transaction.get("bankAccount").alias == params.accountName )
					&& ( !params.description || transaction.get("description").toLowerCase().indexOf(params.description.toLowerCase()) != -1 );
			})));
		}
	});
	transactionsList.Collections.Transactions = new TransactionList();
	
	/**
	 * Template for generic row in table of transactions
	 */
	transactionsList.Views.TransactionRowView = Backbone.View.extend({
		tagName:'tr',
		template: _.template( $('#transaction-row-template').html() ),
		initialize: function() { this.listenTo( this.model, 'change', this.render ); },
		render: function() {
			this.$el.html( this.template( this.model.toJSON() ) );
			return this;
		}
	});
	
	/**
	 * Main controlling view that handles all Account related tasks & activity
	 */
	transactionsList.Views.DashboardView = Backbone.View.extend({
		initialize: function () {
			window.transactionsList.Collections.Transactions.on('add', this.addAll, this);
			window.transactionsList.Collections.Transactions.on('reset', this.addAll, this);
			window.transactionsList.Collections.Transactions.fetch({reset: true});
			this.totalsBarchart = new transactionsList.Views.TransactionsTotalsBarChartView();
			this.thirtyDaysChart = new transactionsList.Views.BalanceLastThirtyDaysBarChartView();
		},

		// This is the overall accounts list view&form - rendering will be handled by other views
		renderTransaction: function () {return this;},

		addAll: function( transactions ) {
			this.$('#your-transactions-list').html('');
			this.$('#biggest-transactions-list').html('');
			_(transactions.first(10)).each(this.addOneTransaction, this);
			_(_(transactions.sortBy(function( transaction ){ return transaction.get("amount"); })).first(3)).each(this.addOneTopTransaction, this);
			this.totalsBarchart.render( transactions );
			this.thirtyDaysChart.render( transactions );
		},
		addOneTransaction: function( transaction ) {
			var view = new transactionsList.Views.TransactionRowView({ model: transaction });
			$('#your-transactions-list').append( view.render().el );
		},
		addOneTopTransaction: function( transaction ) {
			var view = new transactionsList.Views.TransactionRowView({ model: transaction });
			$('#biggest-transactions-list').append( view.render().el );
		}
	});
	
	transactionsList.Views.TransactionsPageView = Backbone.View.extend({
		el: $("#transactions-page-body"),
		events: {
			"click #toggle-filter-control": "toggleFilter",
			"change #filter-transactiontype": "applyFilter",
			"change #filter-amountoperator": "applyFilter",
			"keyup #filter-amountvalue": "applyFilter",
			"change #filter-account": "applyFilter",
			"keyup #filter-description": "applyFilter"
		},
		initialize: function () {
			window.transactionsList.Collections.Transactions.on('add', this.addAll, this);
			window.transactionsList.Collections.Transactions.on('reset', this.addAll, this);
			window.transactionsList.Collections.Transactions.fetch({reset: true});
		},
		addAll: function( transactions ) {
			this.$('#your-transactions-list').html('');
			transactions.each(this.addOneTransaction, this);
		},
		addOneTransaction: function( transaction ) {
			var view = new transactionsList.Views.TransactionRowView({ model: transaction });
			$('#your-transactions-list').append( view.render().el );
		},
		toggleFilter: function( e ){
			e.preventDefault();
			$("#filter-control").toggle('show');
		},
		applyFilter: function( e ){
			e.preventDefault();
			var params = {};
			
			//filter by transaction type
			var transactionType = $("#filter-transactiontype").val();
			if (transactionType) params.transactionType = transactionType;
			
			//filter by description
			var description = $("#filter-description").val();
			if(description) params.description = description;
			
			//filter by amount
			var amountOperator = $("#filter-amountoperator").val();
			var amount = $("#filter-amountvalue").val();
			if (amount) {
				params.amount = amount;
				params.amountOperator =  amountOperator;
			}
			
			//filter by account name
			var accountName = $("#filter-account").val();
			if (accountName) params.accountName = accountName;

			var filtered = window.transactionsList.Collections.Transactions.filterTransaction( params );
			this.addAll( filtered );
		}
		
	});
	

	transactionsList.Helpers.TransactionGraphDefault = function( ){
		return { labels: [], datasets: [] };
	};
	transactionsList.Helpers.GreenGraphDefault = function( label ){
		var data = {
			            label: label,
			            fillColor: "rgba(92,184,92,0.5)",
			            strokeColor: "rgba(92,184,92,0.8)",
			            highlightFill: "rgba(92,184,92,0.75)",
			            highlightStroke: "rgba(92,184,92,1)",
			            data: []
			        };
		return data;
	};
	transactionsList.Helpers.OrangeGraphDefault = function( label ){
		var data = {
			            label: label,
			            fillColor: "rgba(240,173,78,0.5)",
			            strokeColor: "rgba(240,173,78,0.8)",
			            highlightFill: "rgba(240,173,78,0.75)",
			            highlightStroke: "rgba(240,173,78,1)",
			            data: []
			        };
		return data;
	};
	
	
	/**
	 *  Barchart showing the last 6 months total outgoings/incomings by month 
	 */
	transactionsList.Views.TransactionsTotalsBarChartView = Backbone.View.extend({
		tagName:'div',
		template: _.template( $('#transaction-row-template').html() ),
		initialize: function() { this.listenTo( window.transactionsList.Collections.Transactions, 'change', this.render ); },
		
		processTransactions: function( transactions ){
			var monthlyMap = {};
			transactions.each(function( transaction ){
				var month = this.parseMonthLabel( transaction.get("dateOfTransaction") );
				if ( month in monthlyMap ){
					if ( transaction.get("transactionType") in monthlyMap[month] ){
						monthlyMap[month][transaction.get("transactionType")] = monthlyMap[month][transaction.get("transactionType")] + Math.abs( transaction.get("amount") ); 
					} else {
						monthlyMap[month][transaction.get("transactionType")] = Math.abs( transaction.get("amount") );
					} 
				} else {
					monthlyMap[month] = {};
					monthlyMap[month][transaction.get("transactionType")] = Math.abs( transaction.get("amount") );
				}	
			}, this);
			
			//sort by date
			var sortedKeys = []
			for ( dateKey in monthlyMap ){
				sortedKeys.push( dateKey )
			}
			//Build graph data
			var labels = [];
			var data = { CREDIT: [], DEBIT:[] };
			sortedKeys.sort();
			_(_(sortedKeys).first(6)).each( function( dateKey ){ 
				labels.push(dateKey);
				data.CREDIT.push( monthlyMap[dateKey].CREDIT ? monthlyMap[dateKey].CREDIT.toFixed(2) : 0.00 );
				data.DEBIT.push( monthlyMap[dateKey].DEBIT ? monthlyMap[dateKey].DEBIT.toFixed(2) : 0.00 );
				
			}, this);
			//Build graph data
			var graph = transactionsList.Helpers.TransactionGraphDefault();
			graph.labels = labels;
			var creditGraph = transactionsList.Helpers.GreenGraphDefault("CREDIT");
			var debitGraph = transactionsList.Helpers.OrangeGraphDefault("DEBIT");
			creditGraph.data = data.CREDIT;
			debitGraph.data = data.DEBIT;
			graph.datasets.push( creditGraph );
			graph.datasets.push( debitGraph );
			return graph;
		},

		parseMonthLabel: function(input) {
			var parts = input.split('-');
			return parts[0] + "-" + parts[1];
		},
		
		render: function( transactions ) {
			var data = this.processTransactions( transactions );
			var canvas = document.getElementById( "monthly-totals-chart" );
			var ctx = canvas.getContext("2d");
			new Chart( ctx ).Bar( data, { scaleShowGridLines:true, responsive:true } );
			return this;
		}
	});
	
	
	/**
	 * Barchart showing last 30 days incoming/outgoing
	 */
	transactionsList.Views.BalanceLastThirtyDaysBarChartView = Backbone.View.extend({
		tagName:'div',
		template: _.template( $('#transaction-row-template').html() ),
		initialize: function() { this.listenTo( window.transactionsList.Collections.Transactions, 'change', this.render ); },
		
		processTransactions: function( transactions ){
			var accountMap = {};
			var latestDate;
			transactions.each(function( transaction ){
				if ( !latestDate || latestDate<transaction.get("dateOfTransaction")) latestDate = transaction.get("dateOfTransaction");   
				var date = transaction.get("dateOfTransaction");
				if (!accountMap[transaction.get("bankAccount").alias]) accountMap[transaction.get("bankAccount").alias] = {};
				accountMap[transaction.get("bankAccount").alias][date] = transaction.get("balance");
			}, this);
			
			//sort by date
			var sortedKeys = []

			var dateparts = latestDate.split("-");
			
			var today = new Date(dateparts[0], dateparts[1]- 1, dateparts[2]);
			var year = today.getFullYear();
			var month = today.getMonth();
			var date = today.getDate();

			console.log(today);
			var labels = [];
			for(var i=30; i>0; i--){
				var iDate = new Date( year, month-1, (date - i));
				var day = iDate.getFullYear() + "-" + (iDate.getMonth()<10? "0"+iDate.getMonth() : iDate.getMonth()) + "-" + iDate.getDate(); 
				for ( account in accountMap ){
					if ( !accountMap[account][day] ) accountMap[account][day] = 0.00;
				}
				console.log(day);
				labels.push(day);
			}
			
			var graph = transactionsList.Helpers.TransactionGraphDefault();
			for ( account in accountMap ){
				var accountBalanceGraph = transactionsList.Helpers.GreenGraphDefault(account);
				var data = { BALANCE: []};
				var previosBalance;
				_(labels).each( function( dateKey ){
					if (accountMap[account][dateKey]) previousBalance = accountMap[account][dateKey];
					if (!accountMap[account][dateKey] && previousBalance) accountMap[account][dateKey] = previousBalance; 
					data.BALANCE.push( accountMap[account][dateKey]? accountMap[account][dateKey].toFixed(2) : 0.00 );
				}, this);
				//Build graph data
				var trimmedLabels = [];
				_(labels).each(function(label){
					trimmedLabels.push(label.substring(8,10));
				}); 
				accountBalanceGraph.data = data.BALANCE;
				graph.datasets.push( accountBalanceGraph );
			}
			//Build graph data
			graph.labels = trimmedLabels;
			return graph;
		},
		
		render: function( transactions ) {
			var data = this.processTransactions( transactions );
			var canvas = document.getElementById( "last-thirty-totals-chart" );
			var ctx = canvas.getContext("2d");
			new Chart( ctx ).Line( data, { scaleShowGridLines:true, responsive:true, pointDot: false } );
			return this;
		}
	});
	
}());