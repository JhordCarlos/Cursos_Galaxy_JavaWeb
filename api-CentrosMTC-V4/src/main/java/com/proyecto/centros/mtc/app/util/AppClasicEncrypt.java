package com.proyecto.centros.mtc.app.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AppClasicEncrypt {

	public static String encrypt(String clave) {

		String encrypt = null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] salt = new byte[16];
			md.update(salt);
			byte[] bytes = md.digest(clave.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
			return encrypt;
		}
	}

	public static void main(String[] args) {
		System.out.println(encrypt("1234"));
	}
}
