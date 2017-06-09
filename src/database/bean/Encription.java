package database.bean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by m1sho on 10.06.2017.
 * copied from our assignment
 */
public class Encription {

    public static String stringToSHA(String word) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(word.getBytes());
            byte[] res = messageDigest.digest();
            String hex = hexToString(res);
            return hex;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String hexToString(byte[] bytes) {
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int val = bytes[i];
            val = val & 0xff;  // remove higher bits, sign
            if (val < 16) buff.append('0'); // leading 0
            buff.append(Integer.toString(val, 16));
        }
        return buff.toString();
    }
}
