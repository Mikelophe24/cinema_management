package util;

import org.mindrot.jbcrypt.BCrypt;

public class Bcrypt {

	public static String hash(String plainPassword) {
		return BCrypt.hashpw(plainPassword, BCrypt.gensalt(10));
	}

	public static boolean compare(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);
	}
}
