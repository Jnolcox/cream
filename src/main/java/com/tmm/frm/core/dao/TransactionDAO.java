package com.tmm.frm.core.dao;

import java.util.List;

import com.tmm.frm.domain.BankAccountTransaction;

public interface TransactionDAO {

	List<BankAccountTransaction> loadTransactionsForUser(Long id);
}