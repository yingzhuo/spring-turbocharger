/*
 *
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
 *
 */
package com.github.yingzhuo.turbocharger.util;

/**
 * 字符串池
 *
 * @author 应卓
 * @see CharPool
 * @since 1.0.0
 */
public final class StringPool {

	public static final String EMPTY = "";
	public static final String SPACE = " ";
	public static final String TAB = "\t";
	public static final String COLON = ":";
	public static final String SEMICOLON = ";";
	public static final String DOT = ".";
	public static final String DOT_DOT = "..";
	public static final String SLASH = "/";
	public static final String BACKSLASH = "\\";
	public static final String CR = "\r";
	public static final String LF = "\n";
	public static final String QUESTION_MARK = "?";
	public static final String HYPHEN = "-";
	public static final String UNDERSCORE = "_";
	public static final String COMMA = ",";
	public static final String SHARP = "#";

	public static final String OK = "OK";
	public static final String NG = "NG";

	/**
	 * 私有构造方法
	 */
	private StringPool() {
	}

	public static boolean isOK(String value) {
		return OK.equalsIgnoreCase(value);
	}

	public static boolean isNG(String value) {
		return NG.equalsIgnoreCase(value);
	}

}
