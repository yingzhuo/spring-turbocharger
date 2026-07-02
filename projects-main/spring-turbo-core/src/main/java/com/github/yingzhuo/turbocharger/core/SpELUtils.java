package com.github.yingzhuo.turbocharger.core;

import com.github.yingzhuo.turbocharger.aspect.AspectSpELTemplate;
import org.jspecify.annotations.Nullable;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@SuppressWarnings("unchecked")
public final class SpELUtils {

	private SpELUtils() {
		super();
	}

	@Nullable
	public static <T> T getValue(String expression) {
		return getValue(expression, null, null);
	}

	@Nullable
	public static <T> T getValue(String expression, @Nullable Object rootObject) {
		return getValue(expression, rootObject, null);
	}

	@Nullable
	public static <T> T getValue(String expression, @Nullable Map<String, ?> variables) {
		return getValue(expression, null, variables);
	}

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
