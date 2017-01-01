package com.tmm.frm.statement;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.tmm.frm.core.exception.CustomException;
import com.tmm.frm.domain.BankAccountTransaction;

public interface StatementFileProcessor {
	
	/**
	 * Method that handles parsing a provided file into the
	 * necessary transactions ready to be used
	 * @param statement
	 * @return 
	 * @throws CustomException 
	 */
	public Set<BankAccountTransaction> processFile(MultipartFile statement) throws CustomException;

}
