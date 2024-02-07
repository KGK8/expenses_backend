package com.example.expenses.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.expenses.models.accountModel;
import com.example.expenses.repos.accountsRepository;

public class utils {

	public long getTime() {
		return System.currentTimeMillis();
	}

	public String getDate() {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = currentDate.format(format);
		return formattedDate;
	}

	public String checkPasswordStrength(String password) {
		String returnStatement = "OK";
		String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=_]).{12,}$";
		Pattern pattern = Pattern.compile(regex);
		if (password.length() < 12) {
			returnStatement = "Password Should Contain At Least 12 Characters";
		} else if (!pattern.matcher(password).matches()) {
			returnStatement = "Password Should Contain At Least one lowercase letter,one uppercase letter,one digit & special character";
		} else {
			returnStatement = "OK";
		}
		return returnStatement;
	}

	public static byte[] convertSHA(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return md.digest(input.getBytes(StandardCharsets.UTF_8));
	}

	public static String toHexString(byte[] hash) {
		BigInteger number = new BigInteger(1, hash);
		StringBuilder hexString = new StringBuilder(number.toString(16));
		while (hexString.length() < 32) {
			hexString.insert(0, '0');
		}
		return hexString.toString();
	}

	public String encryptPassword(String password) {
		try {
			return toHexString(convertSHA(password));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
}
