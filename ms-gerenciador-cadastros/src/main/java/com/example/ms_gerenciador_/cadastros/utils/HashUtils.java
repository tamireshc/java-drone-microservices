package com.example.ms_gerenciador_.cadastros.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtils {
    public static String gerarHashSenha(String senha, String algoritmo) throws NoSuchAlgorithmException {
        // 1. Obter instância do algoritmo de hash
        MessageDigest md = MessageDigest.getInstance(algoritmo);

        // 2. Adicionar salt aleatório à senha
        byte[] salt = gerarSalt();
        md.update(salt);

        // 3. Gerar hash da senha com salt
        byte[] hash = md.digest(senha.getBytes(StandardCharsets.UTF_8));

        // 4. Converter hash e salt para String hexadecimal
        String hashHex = bytesToHex(hash);
        String saltHex = bytesToHex(salt);

        // 5. Retornar hash e salt concatenados
        return hashHex + ":" + saltHex;
    }

    private static byte[] gerarSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

    }
}
