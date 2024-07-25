package com.lobosstudios.crypter;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.Arrays;

public class Main implements ClipboardOwner {
	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			new Main().encryptNewPassword(); // clipboard.setContents() can't be used in a static context)
			return;
		}

		if (args[0].equals("-v") || args[0].equals("--verify"))
			verifyPassword();
		else
			new Main().encryptNewPassword(); // clipboard.setContents() can't be used in a static context)
	}

	private void encryptNewPassword() {
		System.out.println();
		String passwordToEncrypt = getPassword(true);
		String encrypted = Encrypter.encryptSha512(passwordToEncrypt, null);

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection contents = new StringSelection(encrypted);
		clipboard.setContents(contents, this);

		System.out.println(encrypted + " copied to clipboard");
		System.out.println();
	}

	private static void verifyPassword() {
		System.out.println();
		System.out.print("Encrypted password: ");
		String encrypted = String.valueOf(System.console().readLine());
		System.out.println();
		System.out.print("Plaintext password: ");
		String plaintext = String.valueOf(System.console().readPassword());

		System.out.println();
		if (Encrypter.verify(encrypted, plaintext))
			System.out.println("Passwords match");
		else
			System.out.println("Passwords do not match");
		System.out.println();
	}

	private static String getPassword(boolean askForConfirmation) {
		boolean done = false;
		char[] password = null;

		while (!done) {
			System.out.println();
			System.out.print("Password: ");
			password = System.console().readPassword();
			if (!askForConfirmation) {
				done = true;
			} else {
				System.out.print("Confirm password: ");
				char[] confirmation = System.console().readPassword();

				if (Arrays.equals(password, confirmation)) done = true;
			}
		}
		return String.valueOf(password);
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {}
}