package com.tmm.frm.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmm.frm.domain.enums.BankProviders;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "CRM_BANKACCOUNT")
public class BankAccount extends PersistableObject {

	private static final long serialVersionUID = 3458607287170514439L;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ownerProfile")
	private Profile ownerProfile;
	
	@OneToMany(mappedBy = "transactionGroup", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<BankAccountTransaction> transactions = new HashSet<BankAccountTransaction>();
	
	@OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<BankAccountTransactionGroup> transactionGroups = new HashSet<BankAccountTransactionGroup>();
	
	private Double latestBalance;
	
	public Profile getOwnerProfile() {
		return ownerProfile;
	}

	public void setOwnerProfile(Profile ownerProfile) {
		this.ownerProfile = ownerProfile;
	}

	private String alias;
	
	private BankProviders bankProvider; 

	@JsonProperty
	public BankProviders getBankProvider() {
		return bankProvider;
	}

	public void setBankProvider(BankProviders bankProvider) {
		this.bankProvider = bankProvider;
	}

	@JsonProperty
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Set<BankAccountTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<BankAccountTransaction> transactions) {
		this.transactions = transactions;
	}

	public boolean addTransaction(BankAccountTransaction transaction){
		return getTransactions().add(transaction);
	}
	public boolean removeTransaction(BankAccountTransaction transaction){
		return getTransactions().remove(transaction);
	}
	public boolean addTransactions(Set<BankAccountTransaction> transactions){
		return getTransactions().addAll(transactions);
	}
	public boolean removeTransactions(Set<BankAccountTransaction> transactions){
		return getTransactions().removeAll(transactions);
	}

	public Double getLatestBalance() {
		return latestBalance;
	}

	public void setLatestBalance(Double latestBalance) {
		this.latestBalance = latestBalance;
	}

	public Set<BankAccountTransactionGroup> getTransactionGroups() {
		return transactionGroups;
	}

	public void setTransactionGroups(
			Set<BankAccountTransactionGroup> transactionGroups) {
		this.transactionGroups = transactionGroups;
	}

	public boolean addTransactionGroup( BankAccountTransactionGroup grp ){
		return getTransactionGroups().add(grp);
	}
	public boolean removeTransactionGroup( BankAccountTransactionGroup grp ){
		return getTransactionGroups().remove(grp);
	}
}
