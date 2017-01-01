package com.tmm.frm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tmm.frm.core.exception.CustomException;
import com.tmm.frm.domain.BankAccount;
import com.tmm.frm.domain.BankAccountTransaction;
import com.tmm.frm.domain.BankAccountTransactionGroup;
import com.tmm.frm.domain.Profile;
import com.tmm.frm.domain.enums.BankProviders;
import com.tmm.frm.statement.StatementFileProcessor;
import com.tmm.frm.statement.StatementFileProcessorFactory;

@Service
public class BankAccountService {
	
	@Autowired ProfileService profileService;
	@Autowired TaggingService taggingService;
	
	@Transactional public BankAccount createAccount( String alias, String provider){
		Profile p = profileService.getLoggedInProfile();
		BankAccount account = new BankAccount();
		account.setAlias(alias);
		account.setBankProvider(BankProviders.valueOf(provider));
		account.setOwnerProfile(p);
		p.addBankAccounts(account);
		return account;
	}

	
	/**
	 * Import & processes a file upload containing transaction details
	 * 
	 * @param statement
	 * @param accountAlias
	 * @throws CustomException
	 */
	@Transactional public void saveTransactions(MultipartFile statement, String accountAlias) throws CustomException {
		Profile p = profileService.getLoggedInProfile();
		BankAccount acc = p.getBankAccountByAlias(accountAlias);
		
		// Process file import
		StatementFileProcessor processor = StatementFileProcessorFactory.createStatementFileProcessot(acc.getBankProvider());
		Set<BankAccountTransaction> transactions = processor.processFile(statement);
		List<BankAccountTransaction> newTransactions = new ArrayList<BankAccountTransaction>();
		for (BankAccountTransaction t : transactions ){ 
			t.setBankAccount(acc);
			if ( acc.addTransaction(t) ) {
				newTransactions.add(t);
			}
		}
		
		// create transaction groups
		for (BankAccountTransaction newT : newTransactions ){
			if ( newT.getTransactionGroup() == null ){
				for (BankAccountTransaction t : acc.getTransactions() ){
					if ( newT.getDescription().toLowerCase().equals(t.getDescription().toLowerCase()) && !newT.equals(t) &&  t.getTransactionGroup() != null ){
						newT.setTransactionGroup(t.getTransactionGroup());
						t.getTransactionGroup().addTransaction(newT);
					}
				}
				
				//no existing group found so create now
				if ( newT.getTransactionGroup() == null ) {
					BankAccountTransactionGroup grp = new BankAccountTransactionGroup();
					newT.setTransactionGroup(grp);
					grp.addTransaction(newT);
					grp.setBankAccount(acc);
				}
			}
		}
		
		// automatically tag new transactions
		taggingService.autoTag( newTransactions );
		
		// Update latest account balance
		Date latestDate=null;
		for (BankAccountTransaction t : acc.getTransactions() ){
			if ( latestDate==null || t.getDateOfTransaction().after(latestDate) ){
				acc.setLatestBalance(t.getBalance());
				latestDate = t.getDateOfTransaction();
			}
		}
	}
}