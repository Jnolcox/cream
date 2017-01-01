package com.tmm.frm.tagging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tmm.frm.domain.enums.TransactionTag.*;

import com.tmm.frm.domain.BankAccountTransaction;

public class TransactionTaggingEngine {
	
	private final static List<TransactionTaggingRule> PRIMARY_RULES = new ArrayList<TransactionTaggingRule>();
	private final static List<TransactionTaggingRule> SECONDARY_RULES = new ArrayList<TransactionTaggingRule>();
	
	static {
		PRIMARY_RULES.add( new TransactionTaggingRule( MOBILE, Arrays.asList(MOBILE), "(.*tesco.*mobile.*)|(.*mobile.*tesco.*)") );
		PRIMARY_RULES.add( new TransactionTaggingRule( INSURANCE, Arrays.asList(INSURANCE), "(.*tesco.*insurance.*)|(.*insurance.*tesco.*)") );
		PRIMARY_RULES.add( new TransactionTaggingRule( GROCERIES, Arrays.asList(GROCERIES), ".*tesco.*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( CASHWITHDRAWAL, Arrays.asList(CASHWITHDRAWAL), "(.*atm.*)|(.*withdrawal.*)") );
		PRIMARY_RULES.add( new TransactionTaggingRule( LIVINGEXPENSES, Arrays.asList(LIVINGEXPENSES, SOCIAL), ".*coffee.*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( GROCERIES, Arrays.asList(GROCERIES), ".*john lewis.*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( GROCERIES, Arrays.asList(GROCERIES), ".*sainsbury.*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( GROCERIES, Arrays.asList(GROCERIES), ".*waitrose.*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( GROCERIES, Arrays.asList(GROCERIES), ".*spar.*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( GROCERIES, Arrays.asList(GROCERIES, LIVINGEXPENSES), ".*boots.*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( MOBILE, Arrays.asList(MOBILE), ".*vodafone.*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( MOBILE, Arrays.asList(MOBILE), ".*orange.*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( MOBILE, Arrays.asList(MOBILE), ".*o2.*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( LIVINGEXPENSES, Arrays.asList(LIVINGEXPENSES, SOCIAL), ".*cafe.*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( INSURANCE, Arrays.asList(INSURANCE), ".*countrywide.*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( INSURANCE, Arrays.asList(INSURANCE), ".*friends life.*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( LIVINGEXPENSES, Arrays.asList(LIVINGEXPENSES, CONVENIENCE), ".*pret a manger.*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( TRAVEL, Arrays.asList(TRAVEL, LIVINGEXPENSES), "(.*shell.*)|(.* bp .*)") );
		PRIMARY_RULES.add( new TransactionTaggingRule( UTILITIES, Arrays.asList(UTILITIES, GAS, ELECTRICITY), "(.*npower.*)|(.*scottishpower.*)") );
		PRIMARY_RULES.add( new TransactionTaggingRule( MORTGAGE, Arrays.asList(MORTGAGE), ".*mortgage.*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( TRAVEL, Arrays.asList(TRAVEL, LIVINGEXPENSES), "(.*banardos.*)|(.*oxfam.*)") );
		PRIMARY_RULES.add( new TransactionTaggingRule( CLOTHING, Arrays.asList(CLOTHING, LIVINGEXPENSES), "(.*uniqlo.*)|(.*h&m.*)") );
		PRIMARY_RULES.add( new TransactionTaggingRule( UTILITIES, Arrays.asList(UTILITIES, PHONE), ".* bt .*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( SOCIAL, Arrays.asList(SOCIAL, LIVINGEXPENSES), "(.*tortilla.*)|(.*pizza express.*)|(.*dominos.*)|(.*pizza hut.*)|(.*nandos.*)|(.*burger king.*)|(.*shakeshack.*)") );
		PRIMARY_RULES.add( new TransactionTaggingRule( PENSION, Arrays.asList(PENSION), ".*pension.*") );
		
		PRIMARY_RULES.add( new TransactionTaggingRule( FEE, Arrays.asList(FEE), ".* fee .*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( DIRECTDEBIT, Arrays.asList(DIRECTDEBIT), ".*direct debit.*") );
		PRIMARY_RULES.add( new TransactionTaggingRule( STANDINGORDER, Arrays.asList(STANDINGORDER), ".*standing order.*") );
	}
	
	public boolean evaluatePrimaryRules( BankAccountTransaction t ){
		return evaluate( t, PRIMARY_RULES );
	}
	
	public boolean evaluateSecondaryRules( BankAccountTransaction t ){
		return evaluate( t, SECONDARY_RULES );
	}
	
	
	private boolean evaluate( BankAccountTransaction t, List<TransactionTaggingRule> rules ){
		for ( TransactionTaggingRule rule : rules ){
			if ( rule.evaluate( t ) ) return true;
		}
		return false;
	}
}