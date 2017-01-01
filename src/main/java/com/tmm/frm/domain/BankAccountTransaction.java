package com.tmm.frm.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tmm.frm.domain.enums.TransactionType;
import com.tmm.frm.helper.JacksonDateSerializer;

@Indexed
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "CRM_TRANSACTION")
public class BankAccountTransaction extends PersistableObject {

	private static final long serialVersionUID = 3458607287170514439L;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bankAccount", nullable=false)
	private BankAccount bankAccount;
	private Date dateOfTransaction;
	private Double amount;
	private Double balance;
	private TransactionType transactionType;
	@Field private String description;
	
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "transactionGroup", nullable=false)
	private BankAccountTransactionGroup transactionGroup;
	
	@JsonProperty
	public BankAccount getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}
	@JsonSerialize(using=JacksonDateSerializer.class)
	@JsonProperty
	public Date getDateOfTransaction() {
		return dateOfTransaction;
	}
	public void setDateOfTransaction(Date dateOfTransaction) {
		this.dateOfTransaction = dateOfTransaction;
	}
	@JsonProperty
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
		setTransactionType(amount<0 ? TransactionType.DEBIT : TransactionType.CREDIT);
	}
	@JsonProperty
	public Double getBalance() { 
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	@JsonProperty
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@JsonProperty
	public TransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	
	@JsonProperty
	public BankAccountTransactionGroup getTransactionGroup() {
		return transactionGroup;
	}
	public void setTransactionGroup(BankAccountTransactionGroup transactionGroup) {
		this.transactionGroup = transactionGroup;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result
				+ ((bankAccount == null) ? 0 : bankAccount.hashCode());
		result = prime
				* result
				+ ((dateOfTransaction == null) ? 0 : dateOfTransaction
						.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((transactionType == null) ? 0 : transactionType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		BankAccountTransaction other = (BankAccountTransaction) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (bankAccount == null) {
			if (other.bankAccount != null)
				return false;
		} else if (!bankAccount.equals(other.bankAccount))
			return false;
		if (dateOfTransaction == null) {
			if (other.dateOfTransaction != null)
				return false;
		} else if (!dateOfTransaction.equals(other.dateOfTransaction))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (transactionType != other.transactionType)
			return false;
		return true;
	}
	
	
}
