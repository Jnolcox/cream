package com.tmm.frm.controller.api;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmm.frm.domain.BankAccount;
import com.tmm.frm.domain.Profile;
import com.tmm.frm.service.BankAccountService;
import com.tmm.frm.service.ProfileService;

/**
 * Controller class that handles all requests for the site home page - it
 * determines whether or not the user is logged in and either displays the site
 * welcome/login page or directs the user to their user profile page
 * 
 * @author robert.hinds
 * 
 */
@Controller
@RequestMapping("/api/accounts")
public class BankAccountApiController {
	
	static class AccountObject{
		private String alias;
		private String bankProvider;
		private Boolean active;
		public String getAlias() { return alias; }
		public void setAlias(String alias) { this.alias = alias; }
		public Boolean isActive() { return active; }
		public void setActive(Boolean active) { this.active = active; }
		public String getBankProvider() { return bankProvider; }
		public void setBankProvider(String bankProvider) { this.bankProvider = bankProvider; }
	}
	
	@Autowired ProfileService profileService;
	@Autowired BankAccountService bankAccountService;

	@RequestMapping(value="", method = RequestMethod.GET)
	@ResponseBody
	public Set<BankAccount> list(HttpServletRequest request) throws Exception {
		Profile p = profileService.getLoggedInProfile();
		return p.getBankAccounts();
	}
	
	@RequestMapping(value="", method = RequestMethod.POST)
	@ResponseBody
	public BankAccount create(@RequestBody AccountObject acc) throws Exception {
		return bankAccountService.createAccount(acc.getAlias(), acc.getBankProvider());
	}
}
