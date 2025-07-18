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
package com.github.yingzhuo.turbocharger.core;

import com.github.yingzhuo.turbocharger.aspect.AspectSpELTemplate;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * 简易SpEL相关工具 这个工具适用于一般绝大部分情况
 *
 * @author 应卓
 * @see AspectSpELTemplate
 * @since 3.4.0
 */
@SuppressWarnings("unchecked")
public final class SpELUtils {

	/**
	 * 私有构造方法
	 */
	private SpELUtils() {
		super();
	}

	/**
	 * 解析SpEL并获得其值
	 *
	 * @param expression 表达式
	 * @param <T>        最终值的类型
	 * @return 最终值或空值
	 */
	@Nullable
	public static <T> T getValue(String expression) {
		return getValue(expression, null, null);
	}

	/**
	 * 解析SpEL并获得其值
	 *
	 * @param expression 表达式
	 * @param rootObject 根数据
	 * @param <T>        最终值的类型
	 * @return 最终值或空值
	 */
	@Nullable
	public static <T> T getValue(String expression, @Nullable Object rootObject) {
		return getValue(expression, rootObject, null);
	}

	/**
	 * 解析SpEL并获得其值
	 *
	 * @param expression 表达式
	 * @param variables  其他变量
	 * @param <T>        最终值的类型
	 * @return 最终值或空值
	 */
	@Nullable
	public static <T> T getValue(String expression, @Nullable Map<String, ?> variables) {
		return getValue(expression, null, variables);
	}

	/**
	 * 解析SpEL并获得其值
	 *
	 * @param expression 表达式
	 * @param rootObject 根数据
	 * @param variables  其他变量
	 * @param <T>        最终值的类型
	 * @return 最终值或空值
	 */
	@Nullable
	public static <T> T getValue(String expression, @Nullable Object rootObject, @Nullable Map<String, ?> variables) {
		Assert.hasText(expression, "expression is required");

		var ctx = new StandardEvaluationContext(rootObject);
		if (!CollectionUtils.isEmpty(variables)) {
			for (var key : variables.keySet()) {
				var value = variables.get(key);
				ctx.setVariable(key, value);
			}
		}

		return (T) new SpelExpressionParser(new SpelParserConfiguration(true, true))
			.parseExpression(expression)
			.getValue(ctx);
	}

}
