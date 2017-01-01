package com.tmm.frm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
@RequestMapping("/upload")
public class UploadController {
	
	@Autowired ProfileService profileService;
	@Autowired BankAccountService bankAccountService;

	@RequestMapping(value="/statement", method = RequestMethod.POST)
	public ModelAndView uploadStatement(@RequestParam("bankAccount") String bankAccount, @RequestPart(value="statement") MultipartFile statement) throws Exception {
		if (!statement.isEmpty()) {
			try {
				bankAccountService.saveTransactions(statement, bankAccount);
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("redirect:/?errorfile");
			}
		} else {
			return new ModelAndView("redirect:/?emptyfile");
		}
		return new ModelAndView("redirect:/");
	}
}
