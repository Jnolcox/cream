package com.tmm.frm.tagging;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tmm.frm.domain.BankAccountTransaction;
import com.tmm.frm.domain.enums.TransactionTag;

public class TransactionTaggingRule {
	
	public TransactionTaggingRule( TransactionTag c, List<TransactionTag> t, String p ){
		category = c;
		tags = t;
		pattern = Pattern.compile( p, Pattern.CASE_INSENSITIVE );
	}
	
	private TransactionTag category;
	private List<TransactionTag> tags;
	private Pattern pattern;
	
	
	public boolean evaluate( BankAccountTransaction t ){
		Matcher m = pattern.matcher( t.getDescription() );
		if ( m.matches() ) {
			if ( t.getTransactionGroup().getCategory() == null ) t.getTransactionGroup().setCategory( category );
			t.getTransactionGroup().getTags().addAll(tags);
			return true;
		}
		return false;
	}
}