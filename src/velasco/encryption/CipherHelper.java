package velasco.encryption;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class CipherHelper {

    public static final String ALGORITHM = "AES/CBC/PKCS5Padding";


    public static SecretKey getKeyFromPassword(String password, String salt) {
        return getKeyFromPassword(password, salt.getBytes());
    }

    public static SecretKey getKeyFromPassword(String password, byte[] salt) {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder();
        encoder.setSalt(salt);
        return new SecretKeySpec(encoder.hash(password), "AES");
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String encrypt(String input, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String cipherText, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));

        return new String(plainText);
    }

    public static String toHexString(byte[] array){
        StringBuilder stringBuilder = new StringBuilder();
        for(byte a: array){
            stringBuilder.append(String.format("%02x", a));
        }
        return stringBuilder.toString();
    }

    static String promptUserForPassword(String passwordFileName) throws FileNotFoundException {
        Console console = System.console();

        String password;

        if(passwordFileName == null){
            System.out.println("Type in the file password:");
            if(console == null){
                Scanner input = new Scanner(System.in);
                password = input.nextLine();
            } else {
                password = new String(console.readPassword());
            }
        } else {
            Scanner input = new Scanner(new File(passwordFileName));
            password = input.nextLine();
        }
        return password;
    }
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException {
        String password = "Strong Password";
        String salt = "super-long-salt";

        SecretKey secretKey = getKeyFromPassword(password, salt);

        IvParameterSpec ivParameterSpec = generateIv();

        String encryptedText = encrypt("Hidden message", secretKey, ivParameterSpec);
        System.out.println(encryptedText);
        System.out.println(decrypt(encryptedText, secretKey, ivParameterSpec));

    }
}