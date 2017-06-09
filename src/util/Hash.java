package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	/**
	 * Encodes given string with SHA-256 algorithm.
	 *
	 * @param word string
	 * @return encoded byte[]
	 */
	public static String encode(String word) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(word.getBytes());
			byte[] res = messageDigest.digest();
			return hexToString(res);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * Given a byte[] array, produces a hex String,
	 * such as "234a6f". with 2 chars for each byte in the array.
	 *
	 * @param bytes raw
	 * @return hex string
	 */
	private static String hexToString(byte[] bytes) {
		StringBuilder buff = new StringBuilder();
		for (byte aByte : bytes) {
			int val = aByte;
			val = val & 0xff;  // remove higher bits, sign
			if (val < 16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
}
