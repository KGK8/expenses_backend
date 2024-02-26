package com.example.expenses.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

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

	public long convertDateToUnix(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateParser = LocalDate.parse(date, formatter);
		Instant instant = dateParser.atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
		long unixTimestamp = instant.getEpochSecond();
		System.out.println();
		return unixTimestamp;
	}

	public String UnixTimeStampToDate(long unixDate) {
		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixDate), ZoneId.systemDefault());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedDate = dateTime.format(formatter);
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
