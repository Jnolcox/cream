/**
 * 
 */
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

import com.tmm.frm.security.Account;

/**
 * Class responsible for persisting user profile details
 * 
 * @author robert.hinds
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "CRM_USERPROFILE")
public class Profile extends PersistableObject {

	private static final long serialVersionUID = 3458607287170514439L;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account")
	private Account linkedAccount;

	@OneToMany(mappedBy = "ownerProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<BankAccount> bankAccounts = new HashSet<BankAccount>();

	public Set<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(Set<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}
	
	public BankAccount getBankAccountByAlias(String alias) {
		for (BankAccount acc : getBankAccounts()){
			if (acc.getAlias()!=null && acc.getAlias().equals(alias)){
				return acc;
			}
		}
		return null;
	}

	public Account getLinkedAccount() {
		return linkedAccount;
	}

	public void setLinkedAccount(Account linkedAccount) {
		this.linkedAccount = linkedAccount;
	}

	public void addBankAccounts(BankAccount bankAccount) {
		this.bankAccounts.add(bankAccount);
	}

	public void removeBankAccounts(BankAccount bankAccount) {
		this.bankAccounts.remove(bankAccount);
	}
	
}
