package ru.itmo.lab4.util.jwt;

import java.security.Key;

public interface KeyGenerator {
    Key generateKey();
}
