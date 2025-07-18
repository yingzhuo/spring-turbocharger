/*
 * Copyright 2022-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
