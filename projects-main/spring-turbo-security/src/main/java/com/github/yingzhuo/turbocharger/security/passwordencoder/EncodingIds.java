package com.github.yingzhuo.turbocharger.security.passwordencoder;

public interface EncodingIds {

	String noop = "noop";

	String bcrypt = "bcrypt";

	String ldap = "ldap";

	String pbkdf2 = "pbkdf2";

	String scrypt = "scrypt";

	String argon2 = "argon2";

	String SHA_1 = "SHA-1";

	String SHA_256 = "SHA-256";

	String MD4 = "MD4";

	String MD5 = "MD5";

}
