package com.lobosstudios.crypter;

import java.util.Random;
import org.apache.commons.codec.digest.Crypt;

public class Encrypter {
	private final static int saltSize = 20;

	// SHA512crypt
	public static String encryptSha512(String unencoded, String ourSalt) {
		String hashChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		hashChars += "abcdefghijklmnopqrstuvwxyz";
		hashChars += "1234567890";

		int[] integers = new Random().ints(saltSize, 0, hashChars.length()-1).toArray();

		String salt;

		if (ourSalt == null) {
			StringBuilder saltBuilder = new StringBuilder();
			saltBuilder.append("$6$"); // SHA-512
			for (int i = 0; i < saltSize; i++) {
				saltBuilder.append(hashChars, integers[i], integers[i]+1);
			}
			salt = saltBuilder.toString();
		} else {
			salt = "$6$" + ourSalt;
		}

		String h = Crypt.crypt(unencoded, salt);
		return "{CRYPT}" +h;
	}

	public static boolean verify(String encrypted, String passwordToVerify) {
		String salt = encrypted.substring(10, 10 + (saltSize-4));
		return encrypted.equals(encryptSha512(passwordToVerify, salt));
	}
}
