package velasco.encryption;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.util.Arrays;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static velasco.encryption.CipherHelper.*;

public class SecretFile {

	private IvParameterSpec iv;
	private final String password;
	private byte[] salt;
	private byte[] digest;
	public static final String ENCRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding";
	public static final String DIGEST_ALGORITHM = "SHA-256";
	private final MessageDigest md;
	private final File file;
	private final Cipher cipher;


	public SecretFile(File file, String password, int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IOException {
		this.file = file;
		this.password = password;
		md = MessageDigest.getInstance(DIGEST_ALGORITHM);

		cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);

		if(mode == Cipher.ENCRYPT_MODE){
			iv = generateIv();
			salt = new byte[16];
			new SecureRandom().nextBytes(salt);

			SecretKey secretKey = getKeyFromPassword(password, salt);

			cipher.init(mode, secretKey, iv);
			calculateDigest();
		} else if (mode == Cipher.DECRYPT_MODE){
			loadFromFile();
		}

	}

	private void calculateDigest() throws IOException {
		byte[] buffer = new byte[4096];
		int bytesRead;
		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

		while((bytesRead = inputStream.read(buffer)) != -1) {
			md.update(buffer, 0, bytesRead);
		}

		digest = md.digest();
		inputStream.close();

	}
	public void encryptSecretFile(File outputFile) throws IOException, IllegalBlockSizeException, BadPaddingException {

		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
		outputStream.write(iv.getIV());
		outputStream.write(salt);
		outputStream.write(digest);

		byte[] buffer = new byte[4096];
		int bytesRead;

		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		while((bytesRead = inputStream.read(buffer)) != -1) {
			byte[] output = cipher.update(buffer, 0, bytesRead);

			if (output != null){
				outputStream.write(output);
			}
		}

		byte[] output = cipher.doFinal();
		if (output != null){
			outputStream.write(output);
		}

		inputStream.close();
		outputStream.close();

		if(!outputFile.setLastModified(file.lastModified())){
			throw new IOException(String.format("Cannot modify last modified time for %s", file.getAbsolutePath()));
		}
		Files.setAttribute(outputFile.toPath(), "basic:creationTime", Files.getAttribute(file.toPath(), "basic:creationTime", NOFOLLOW_LINKS), NOFOLLOW_LINKS);
	}
	public void decryptSecretFile(File outputFile) throws Exception {

		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

		byte[] buffer = new byte[16];
		byte[] outputBuffer = new byte[buffer.length];
		int bytesRead;
		int bytesWrote;

		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//        Skip the IV, salt, and SHA-256 digest
//        We've already read them
		inputStream.skip(16 + 16 + 32);

//        Prepare our message digest object
		md.reset();

		while((bytesRead = inputStream.read(buffer)) != -1) {
			bytesWrote = cipher.update(buffer, 0, bytesRead, outputBuffer);

			md.update(outputBuffer, 0, bytesWrote);
			if (bytesWrote > 0){
				outputStream.write(outputBuffer, 0, bytesWrote);
			}
		}

		byte[] output = cipher.doFinal();
		if (output != null){
			outputStream.write(output);

			bytesWrote = output.length;
			System.arraycopy(output, 0, outputBuffer, 0, bytesWrote);
			md.update(outputBuffer, 0, bytesWrote);

		}

		inputStream.close();
		outputStream.close();

		byte[] calculatedDigest = md.digest();

		if(!Arrays.equals(digest, calculatedDigest)){
			System.out.println("original " + toHexString(digest));
			System.out.println("md       " + toHexString(calculatedDigest));
			throw new Exception(String.format("Password for %s is wrong!", file.getAbsolutePath()));
		}

		if(!outputFile.setLastModified(file.lastModified())){
			throw new IOException(String.format("Cannot modify last modified time for %s", file.getAbsolutePath()));
		}
		Files.setAttribute(outputFile.toPath(), "basic:creationTime", Files.getAttribute(file.toPath(), "basic:creationTime", NOFOLLOW_LINKS), NOFOLLOW_LINKS);

	}

	private void loadFromFile() throws IOException, InvalidAlgorithmParameterException, InvalidKeyException {
		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

		byte[] buffer = new byte[16];

		inputStream.read(buffer);
		iv = new IvParameterSpec(buffer);


		inputStream.read(buffer);
		salt = new byte[16];
		System.arraycopy(buffer, 0, salt, 0, buffer.length);

		digest = new byte[32];
		inputStream.read(buffer);
		System.arraycopy(buffer, 0, digest, 0, buffer.length);
		inputStream.read(buffer);
		System.arraycopy(buffer, 0, digest, 16, buffer.length);


		SecretKey secretKey = getKeyFromPassword(password, salt);

		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

	}

	@Override
	public String toString() {
		return "SecretFile{" +
				"iv=" + toHexString(iv.getIV()) +
				", salt=" + toHexString(salt) +
				", digest=" + toHexString(digest) +
				", file=" + file +
				'}';
	}

	public static void main(String[] args) throws Exception {
		String password = "password";

		final String inputFile = "/Users/avelasco/Documents/Python/AndroidBackup/test_files/1234";
		final String encryptedFile = "/Users/avelasco/Documents/Python/AndroidBackup/test_files/1234.aes";
		final String decryptedFile = "/Users/avelasco/Documents/Python/AndroidBackup/test_files/1234_unencrypted";
		SecretFile sf = new SecretFile(new File(inputFile), password, Cipher.ENCRYPT_MODE);

		sf.encryptSecretFile(new File(encryptedFile));

		System.out.println(sf);
		SecretFile df = new SecretFile(new File(encryptedFile), password, Cipher.DECRYPT_MODE);
		System.out.println(df);
		df.decryptSecretFile(new File(decryptedFile));
	}

	/**
	 * This verifies that a password is correct for decrypting the file
	 * This just involves running a hash on the file after decryption and making sure that its hashes match
	 * @return if the password is correct
	 * @throws IOException if the file doesn't exist or cannot be read
	 * @throws ShortBufferException - won't happen because we use a long enough buffer
	 */
	public boolean verifyPassword() throws IOException, ShortBufferException {
		byte[] buffer = new byte[16];
		byte[] outputBuffer = new byte[buffer.length];
		int bytesRead;
		int bytesWrote;

		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//        Skip the IV, salt, and SHA-256 digest
//        We've already read them
		inputStream.skip(16 + 16 + 32);

//        Prepare our message digest object
		md.reset();

		while((bytesRead = inputStream.read(buffer)) != -1) {
//            Decrypt cipher
			bytesWrote = cipher.update(buffer, 0, bytesRead, outputBuffer);
//            Read in decrypted bytes
			md.update(outputBuffer, 0, bytesWrote);
		}

//        Get the final decrypted bits if we can
//        Errors are the result of a bad password
		try{

			byte[] output = cipher.doFinal();
			if (output != null){
				bytesWrote = output.length;
				System.arraycopy(output, 0, outputBuffer, 0, bytesWrote);
				md.update(outputBuffer, 0, bytesWrote);

			}
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			return false;
		}

		inputStream.close();

		byte[] calculatedDigest = md.digest();

		return Arrays.equals(digest, calculatedDigest);

	}

}
