package com.tmm.frm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.frm.core.dao.TransactionDAO;
import com.tmm.frm.domain.BankAccountTransaction;
import com.tmm.frm.domain.Profile;

@Service
public class TransactionService {
	
	@Autowired ProfileService profileService;
	@Autowired TransactionDAO transactionDAO;
	
	@Transactional public List<BankAccountTransaction> getTransactionsForLoggedInUser( ){
		Profile p = profileService.getLoggedInProfile();
		return transactionDAO.loadTransactionsForUser(p.getId());
	}

}