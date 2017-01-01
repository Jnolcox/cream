package com.tmm.frm.statement;

import com.tmm.frm.core.exception.CustomException;
import com.tmm.frm.core.exception.CustomExceptionCode;
import com.tmm.frm.domain.enums.BankProviders;

public class StatementFileProcessorFactory {

	/**
	 * Creates a new instance of a statement processor based on the provider in use
	 * @param provider
	 * @return
	 * @throws CustomException 
	 */
	public static StatementFileProcessor createStatementFileProcessot(BankProviders provider) throws CustomException{
		switch (provider){
			case SANTANDER : return new SantanderStatementFileProcessor();
		}
		throw new CustomException(CustomExceptionCode.ACC001_INVALIDPROVIDER, "Statement processor cannot be created - invalid provider");
	}
	
}
