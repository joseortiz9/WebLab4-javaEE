package ru.itmo.lab4.util.jwt;

import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;
import java.security.Key;

@ApplicationScoped
public class KeyGeneratorImpl implements KeyGenerator {
    @Override
    public Key generateKey() {
        String keyString = "mySuperMegaKey";
        return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
    }
}
