package com.tmm.frm.statement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.CharMatcher;
import com.tmm.frm.core.exception.CustomException;
import com.tmm.frm.core.exception.CustomExceptionCode;
import com.tmm.frm.domain.BankAccountTransaction;

public class SantanderStatementFileProcessor implements StatementFileProcessor {

	@Override public Set<BankAccountTransaction> processFile(MultipartFile statement) throws CustomException {
		Set<BankAccountTransaction> transactions = new HashSet<BankAccountTransaction>();
		try {
			InputStream inputStream = statement.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			
			BankAccountTransaction transaction = null;
			while ((line = bufferedReader.readLine()) != null){
				if (line.startsWith("Date: ")) {
					transaction = new BankAccountTransaction();
					transactions.add(transaction);
				}
				processLine(transaction,line);
				
			}
		} catch (IOException | ParseException e) {
			throw new CustomException(CustomExceptionCode.UPLOAD01_FILEERROR, e.getMessage());
		}

		return transactions;
	}

	private void processLine(BankAccountTransaction transaction, String line) throws ParseException {
		if (line.startsWith("Date:")){
			line = line.replace("Date:", "");
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date date = formatter.parse(CharMatcher.WHITESPACE.removeFrom(line));
			transaction.setDateOfTransaction(date);
		}
		if (line.startsWith("Amount:")){
			line = line.replace("Amount:", "");
			transaction.setAmount(Double.valueOf(CharMatcher.WHITESPACE.removeFrom(line)));
		}
		if (line.startsWith("Balance:")){
			line = line.replace("Balance:", "");
			transaction.setBalance(Double.valueOf(CharMatcher.WHITESPACE.removeFrom(line)));
		}
		if (line.startsWith("Description:")){
			line = line.replace("Description:", "");
			String[] cols = line.split(",");
			transaction.setDescription(cols[0]);
		}
		
	}

}
