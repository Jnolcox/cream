package com.tmm.frm.service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.frm.domain.BankAccountTransaction;
import com.tmm.frm.domain.enums.TransactionTag;
import com.tmm.frm.tagging.TransactionTaggingEngine;


@Service
public class TaggingService {
	
	private final int SEARCH_THRESHOLD = 3;
	
	@Autowired ProfileService profileService;
	@Autowired TransactionTaggingEngine transactionTaggingEngine;
	
	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Transactional public void autoTag(List<BankAccountTransaction> transactions) {
		for (BankAccountTransaction t : transactions){
			if ( t.getTransactionGroup().getCategory() == null ){
				transactionTaggingEngine.evaluatePrimaryRules( t );
				evaluateSimilarTransactions( t );
				transactionTaggingEngine.evaluateSecondaryRules( t );
			}
		}
	}
	

	/**
	 * Method performs a full-text-search for other Transactions with similar descriptions, if there are 
	 * three other results found, and they have the same category/tags, then this is applied to the transaction passed in.
	 * 
	 *  Category only set if not already set.
	 *  Tags will be added to the list.
	 * 
	 * @param t - BankAccountTransaction that is being updated 
	 */
	private void evaluateSimilarTransactions(BankAccountTransaction t) {
		List<BankAccountTransaction> matches = searchTransactionDescriptions( t.getDescription() );
		if ( matches.size() == SEARCH_THRESHOLD ){
			TransactionTag catA = matches.get(0).getTransactionGroup().getCategory();
			TransactionTag catB = matches.get(1).getTransactionGroup().getCategory();
			TransactionTag catC = matches.get(2).getTransactionGroup().getCategory();
			if (catA!=null && catA==catB && catA==catC){
				t.getTransactionGroup().setCategory( catA );
			}
			
			Set<TransactionTag> tagsA = matches.get(0).getTransactionGroup().getTags();
			Set<TransactionTag> tagsB = matches.get(1).getTransactionGroup().getTags();
			Set<TransactionTag> tagsC = matches.get(2).getTransactionGroup().getTags();
			Set<TransactionTag> intersection = new HashSet<TransactionTag>( tagsA );
			intersection.retainAll( tagsB );
			intersection.retainAll( tagsC );
			if (intersection.size() > 0 ){
				t.getTransactionGroup().getTags().addAll( intersection );
			}			
		}
	}

	
	/**
	 * 
	 * @param searchTerm
	 * @return
	 */
	@SuppressWarnings("unchecked") private List<BankAccountTransaction> searchTransactionDescriptions(String searchTerm) {
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(getEntityManager());
		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(BankAccountTransaction.class).get();
		org.apache.lucene.search.Query query = qb.keyword().onFields("description").matching(searchTerm).createQuery();
		javax.persistence.Query persistenceQuery = fullTextEntityManager.createFullTextQuery(query, BankAccountTransaction.class);
		persistenceQuery.setMaxResults(SEARCH_THRESHOLD);
		return (List<BankAccountTransaction>) persistenceQuery.getResultList();
	}

}