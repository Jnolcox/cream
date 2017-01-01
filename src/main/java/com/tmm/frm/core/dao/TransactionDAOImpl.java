package com.tmm.frm.core.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tmm.frm.domain.BankAccountTransaction;


@Repository
public class TransactionDAOImpl extends GenericHibernateDAO<BankAccountTransaction, Long> implements TransactionDAO {


	@SuppressWarnings("unchecked")
	@Override public List<BankAccountTransaction> loadTransactionsForUser(Long id) {
		Query query = getEntityManager().createQuery( "select bat from BankAccountTransaction bat where bat.bankAccount.ownerProfile.id = ?1 order by bat.dateOfTransaction desc");
		query.setParameter(1, id);
		return query.getResultList();
	}
}
