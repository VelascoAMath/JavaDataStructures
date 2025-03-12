package src.velasco.encryption;

import javax.crypto.Cipher;
import java.io.File;

import static src.velasco.encryption.CipherHelper.promptUserForPassword;


/**
 * This program verifies that a password can be used to decrypt a secret file
 * java VerifyDecyption -i input_file [-p password_file]
 * input_file is the encrypted file generated from SecretFile.encryptSecretFile(File)
 * password_file is a file that contains the password
 *
 */
public class VerifyDecryption {

    public static void main(String[] args) throws Exception {


        if(args.length < 2){
            System.out.println("""
                     * This program verifies that a password can be used to decrypt a secret file
                     * java VerifyDecryption -i input_file [-p password_file]
                     * input_file is the encrypted file generated from SecretFile.encryptSecretFile(File)
                     * password_file is a file that contains the password\
                    """);
            System.exit(-1);
        }

        String encryptedFileName = null;
        String passwordFileName = null;

        for(int i = 0; i < args.length; i++){
            if(args[i].equals("-i") && i + 1 < args.length){
                encryptedFileName = args[i + 1];
                i++;
            }
            else if (args[i].equals("-p") && i + 1 < args.length){
                passwordFileName = args[i + 1];
                i++;
            }
        }

        if(encryptedFileName == null){
            System.err.println("Must initialize the encrypted file!");
            System.exit(-1);
        }


        String password = promptUserForPassword(passwordFileName);

        SecretFile df = new SecretFile(new File(encryptedFileName), password, Cipher.DECRYPT_MODE);
        if(df.verifyPassword()){
            System.out.println("Success!");
            System.exit(0);
        }
        System.out.println("Failure!");
        System.exit(-1);

    }
}
