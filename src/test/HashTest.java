package test;

import org.junit.Test;
import util.Hash;

import static org.junit.Assert.assertTrue;

/**
 * Created by m1sho on 10.06.2017.
 */
public class HashTest {

    @Test
    public void hashTest() {
        String password = "hello world!";
        String hashed = "7509E5BDA0C762D2BAC7F90D758B5B2263FA01CCBC542AB5E3DF163BE08E6CA9".toLowerCase();
        assertTrue(Hash.encode(password).equals(hashed));

        String password1 = "Works fine:)";
        String hashed1 = "9E134C6773B40504E8760108A5372EF5503A5319678F66D41EA9030C3229A5AA".toLowerCase();
        assertTrue(Hash.encode(password1).equals(hashed1));
    }
}
