/*
 * Copyright 2022-2025 the original author or authors.
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
package com.github.yingzhuo.turbocharger.util;

/**
 * 字符串池
 *
 * @author 应卓
 * @see CharPool
 * @since 3.5.0
 */
public interface StringPool {

	String EMPTY = "";
	String SPACE = " ";
	String TAB = "\t";
	String COLON = ":";
	String SEMICOLON = ";";
	String DOT = ".";
	String DOT_DOT = "..";
	String SLASH = "/";
	String BACKSLASH = "\\";
	String CR = "\r";
	String LF = "\n";
	String QUESTION_MARK = "?";
	String HYPHEN = "-";
	String UNDERSCORE = "_";
	String COMMA = ",";
	String SHARP = "#";

	String OK = "OK";
	String NG = "NG";

}
