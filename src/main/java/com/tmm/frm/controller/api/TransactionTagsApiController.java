package com.tmm.frm.controller.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.util.StringUtils;

import com.tmm.frm.domain.enums.TransactionTag;
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
@RequestMapping("/api/tags")
public class TransactionTagsApiController {
	@Autowired TransactionService transactionService;

	@RequestMapping(value="", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String,String>> list(HttpServletRequest request) throws Exception {
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		for ( TransactionTag t : TransactionTag.values()){
			Map<String,String> m = new HashMap<String,String>();
			m.put("value", t.toString());
			m.put("display", StringUtils.capitalize(t.toString().toLowerCase()) );
			results.add( m );
		}
		return results;
	}
}
