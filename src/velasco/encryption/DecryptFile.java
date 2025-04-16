package velasco.encryption;

import org.apache.commons.io.FilenameUtils;

import javax.crypto.Cipher;
import java.io.File;

import static velasco.encryption.CipherHelper.promptUserForPassword;


/**
 * This program verifies that a password can be used to decrypt a secret file
 * java DecryptFile -i input_file [-o output_file] [-p password_file]
 * input_file is the file to encrypt
 * output_file is the location to which we'll encrypt file. If not specified, it'll be input_file appended with a .aes extension
 * password_file is a file that contains the password. If omitted, the user will be prompted for the password
 *
 */
public class DecryptFile {

	public static void main(String[] args) throws Exception {


		if(args.length < 2){
			System.out.println("""
                     * This program verifies that a password can be used to decrypt a secret file
                     * java DecryptFile -i input_file [-o output_file] [-p password_file]
                     * input_file is the file to encrypt
                     * output_file is the location to which we'll encrypt file. If not specified, it'll be input_file appended with a .aes extension
                     * password_file is a file that contains the password. If omitted, the user will be prompted for the password\
                    """);
			System.exit(-1);
		}

		String encryptedFileName = null;
		String decryptedFileName = null;
		String passwordFileName = null;

		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-i") && i + 1 < args.length){
				encryptedFileName = args[i + 1];
				i++;
			}
			if(args[i].equals("-o") && i + 1 < args.length){
				decryptedFileName = args[i + 1];
				i++;
			}
			else if (args[i].equals("-p") && i + 1 < args.length){
				passwordFileName = args[i + 1];
				i++;
			}
		}

		if(encryptedFileName == null){
			System.err.println("Must initialize the input file!");
			System.exit(-1);
		}
		if(decryptedFileName == null){
			if(FilenameUtils.getExtension(encryptedFileName).equals("aes")){
				decryptedFileName = FilenameUtils.removeExtension(encryptedFileName);
			} else {
				System.err.printf("%s is not an .aes file! Output file must be provided!%n", encryptedFileName);
				System.exit(-1);
			}
		}


		String password = promptUserForPassword(passwordFileName);


		SecretFile df = new SecretFile(new File(encryptedFileName), password, Cipher.DECRYPT_MODE);
		df.decryptSecretFile(new File(decryptedFileName));

		System.exit(0);

	}

}
