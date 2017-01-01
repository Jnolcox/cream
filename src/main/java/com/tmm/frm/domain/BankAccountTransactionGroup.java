package com.tmm.frm.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmm.frm.domain.enums.TransactionTag;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "CRM_TRANSACTIONGROUP")
public class BankAccountTransactionGroup extends PersistableObject {

	private static final long serialVersionUID = 3458607287170514439L;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bankAccount", nullable=false)
	private BankAccount bankAccount;
	
	@OneToMany(mappedBy = "transactionGroup", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	private Set<BankAccountTransaction> transactions = new HashSet<BankAccountTransaction>();
	
	private TransactionTag category;
	
	@Column(nullable = true, insertable = true, updatable = true)
	@ElementCollection
	@Enumerated(EnumType.STRING)
	private Set<TransactionTag> tags = new HashSet<TransactionTag>();
	
	
	
	public BankAccount getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	@JsonProperty
	public TransactionTag getCategory() {
		return category;
	}
	public void setCategory(TransactionTag category) {
		this.category = category;
	}	
	@JsonProperty
	public Set<TransactionTag> getTags() {
		return tags;
	}
	public void setTags(Set<TransactionTag> tags) {
		this.tags = tags;
	}
	public boolean addTag(TransactionTag t ){
		return this.tags.add(t);
	}
	public boolean removeTag(TransactionTag t ){
		return this.tags.remove(t);
	}
	public Set<BankAccountTransaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(Set<BankAccountTransaction> transactions) {
		this.transactions = transactions;
	}
	public boolean addTransaction( BankAccountTransaction t ){
		return getTransactions().add(t);
	}
	public boolean removeTransaction( BankAccountTransaction t ){
		return getTransactions().remove(t);
	}
}
