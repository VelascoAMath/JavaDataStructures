package src.velasco.encryption;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Scanner;

public class CipherHelper {

    public static final String ALGORITHM = "AES/CBC/PKCS5Padding";
//    public static final String ALGORITHM = "AES/CTR/NoPadding";

    public static SecretKey getKeyFromPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 655536, 256);
        SecretKey secretKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return secretKey;
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
            stringBuilder.append(String.format("%x", a));
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
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, IOException {
        String password = "Strong Password";
        String salt = "Y89KJmL&mggm&&lq";

        SecretKey secretKey = getKeyFromPassword(password, salt);

        IvParameterSpec ivParameterSpec = generateIv();

        String encryptedText = encrypt("Hidden message", secretKey, ivParameterSpec);
        System.out.println(encryptedText);
        System.out.println(decrypt(encryptedText, secretKey, ivParameterSpec));

    }
}
