package com.tmm.frm.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmm.frm.domain.BankAccountTransaction;
import com.tmm.frm.service.TransactionService;

/**
 * Controller class that handles all requests for the site home page - it
 * determines whether or not the user is logged in and either displays the site
 * welcome/login page or directs the user to their user profile page
 * 
 * @author robert.hinds
 * 
 */
@Controller
@RequestMapping("/api/transactions")
public class TransactionsApiController {
	@Autowired TransactionService transactionService;

	@RequestMapping(value="", method = RequestMethod.GET)
	@ResponseBody
	public List<BankAccountTransaction> list(HttpServletRequest request) throws Exception {
		return transactionService.getTransactionsForLoggedInUser();
	}
}
