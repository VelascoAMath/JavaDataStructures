package src.velasco.encryption;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Argon2PasswordEncoder {
	//
	private int saltLength = 16;
	private int hashLength = 32;
	private int parallelism = 8;
	private int memory = 65536;
	private int iterations = 4;

	private byte[] salt;



	public Argon2PasswordEncoder() {
		salt = new byte[saltLength];
		new SecureRandom().nextBytes(salt);
	}

	public Argon2PasswordEncoder(int saltLength, int hashLength, int parallelism, int memory, int iterations) {
		this.saltLength = saltLength;
		this.hashLength = hashLength;
		this.parallelism = parallelism;
		this.memory = memory;
		this.iterations = iterations;

		salt = new byte[saltLength];
		new SecureRandom().nextBytes(salt);
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = Arrays.copyOf(salt, salt.length);
		this.saltLength = salt.length;
	}

	public byte[] hash(String password) {
		Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
				.withVersion(Argon2Parameters.ARGON2_VERSION_13)
				.withIterations(iterations)
				.withMemoryAsKB(memory)
				.withParallelism(parallelism)
				.withSalt(salt);

		Argon2BytesGenerator generator = new Argon2BytesGenerator();
		generator.init(builder.build());
		byte[] results = new byte[hashLength];
		generator.generateBytes(password.getBytes(), results);
		return results;
	}

	public String encode(String password) {
		return String.format("$argon2id$v=19$m=%d,t=%d,p=%d$%s$%s", memory, iterations, parallelism, Base64.getEncoder().withoutPadding().encodeToString(salt), Base64.getEncoder().withoutPadding().encodeToString(hash(password)));
	}

	public boolean matches(String rawPassword, String encodedPassword) {
		System.out.println(rawPassword);
		System.out.println(encode(rawPassword));
		System.out.println(encodedPassword);
		return encode(rawPassword).equals(encodedPassword);
	}

	public boolean upgradeEncoding(String encodedPassword) {
		return !this.equals(fromDigest(encodedPassword));
	}

	public static Argon2PasswordEncoder fromDigest(String digest){

		Pattern pattern = Pattern.compile("\\$argon2(i|d|id)\\$v=(16|19)\\$m=(\\d+),t=(\\d+),p=(\\d+)\\$(.+?)\\$(.+)");
		Matcher matcher = pattern.matcher(digest);

		if (!matcher.matches()){
			throw new IllegalArgumentException("Invalid digest");
		}

		if(!matcher.group(1).equals("id")){
			throw new IllegalArgumentException("Argon2 digest must be Argon2id!");
		}

		if(!matcher.group(2).equals("19")){
			throw new IllegalArgumentException("Argon2 version must be 13 (will appear as 19 in the digest)!");
		}

		Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(0, Base64.getDecoder().decode(matcher.group(7)).length, Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
		encoder.setSalt(Base64.getDecoder().decode(matcher.group(6)));
		return encoder;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Argon2PasswordEncoder that)) return false;
		return saltLength == that.saltLength && hashLength == that.hashLength && parallelism == that.parallelism && memory == that.memory && iterations == that.iterations && Arrays.equals(getSalt(), that.getSalt());
	}

	@Override
	public int hashCode() {
		return Objects.hash(saltLength, hashLength, parallelism, memory, iterations, Arrays.hashCode(salt));
	}

	@Override
	public String toString() {
		return "Argon2PasswordEncoder{" +
				"saltLength=" + saltLength +
				", hashLength=" + hashLength +
				", parallelism=" + parallelism +
				", memory=" + memory +
				", iterations=" + iterations +
				", salt=" + Arrays.toString(salt) +
				'}';
	}

	public static void main(String[] args) {

		Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 26, 8, 1 << 16, 4);

		String password = "Password";
		String salt = "long-salt";
		encoder.setSalt(salt.getBytes());

//		String digest = encoder.encode(password);

//		System.out.println(digest.equals("$argon2id$v=19$m=65536,t=3,p=4$bG9uZy1zYWx0$rEk6l4gUjlrBQCz/EwBnUVmkqrRn6jY4b4Ln3aVNLUE"));
//		System.out.println(encoder);
//		System.out.println(Argon2PasswordEncoder.fromDigest(digest));
//		System.out.println(Argon2PasswordEncoder.fromDigest(digest).equals(encoder));

		System.out.println(encoder.matches(password, encoder.encode(password)));


	}

}
