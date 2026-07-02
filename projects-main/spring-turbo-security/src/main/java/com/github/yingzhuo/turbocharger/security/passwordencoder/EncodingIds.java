package com.github.yingzhuo.turbocharger.security.passwordencoder;

/**
 * @author 应卓
 * @since 1.1.0
 */
public interface EncodingIds {

	/**
	 * Noop
	 */
	String noop = "noop";

	/**
	 * bcrypt
	 */
	String bcrypt = "bcrypt";

	/**
	 * ldap
	 */
	String ldap = "ldap";

	/**
	 * pbkdf2
	 */
	String pbkdf2 = "pbkdf2";

	/**
	 * scrypt
	 */
	String scrypt = "scrypt";

	/**
	 * argon2
	 */
	String argon2 = "argon2";

	/**
	 * sha1
	 */
	String SHA_1 = "SHA-1";

	/**
	 * sha256
	 */
	String SHA_256 = "SHA-256";

	/**
	 * MD4
	 */
	String MD4 = "MD4";

	/**
	 * MD5
	 */
	String MD5 = "MD5";

}
