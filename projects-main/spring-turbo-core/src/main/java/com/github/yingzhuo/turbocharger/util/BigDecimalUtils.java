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

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Collection;

import static org.springframework.util.NumberUtils.convertNumberToTargetClass;

/**
 * {@link BigDecimal} 相关工具
 *
 * @author 应卓
 * @since 1.0.8
 */
public final class BigDecimalUtils {

	/**
	 * 私有构造方法
	 */
	private BigDecimalUtils() {
	}

	/**
	 * 绝对值
	 *
	 * @param number 要取绝对值的数
	 * @return 结果
	 */
	public static BigDecimal abs(BigDecimal number) {
		Assert.notNull(number, "number is required");
		return number.abs();
	}

	/**
	 * 加
	 *
	 * @param number1 第一个数
	 * @param number2 第二个数
	 * @return 结果
	 */
	public static BigDecimal add(BigDecimal number1, Number number2) {
		Assert.notNull(number1, "number1 is required");
		Assert.notNull(number2, "number2 is required");
		return number1.add(convertNumberToTargetClass(number2, BigDecimal.class));
	}

	/**
	 * 减
	 *
	 * @param number1 第一个数
	 * @param number2 第二个数
	 * @return 结果
	 */
	public static BigDecimal subtract(BigDecimal number1, Number number2) {
		Assert.notNull(number1, "number1 is required");
		Assert.notNull(number2, "number2 is required");
		return number1.subtract(convertNumberToTargetClass(number2, BigDecimal.class));
	}

	/**
	 * 乘
	 *
	 * @param number1 第一个数
	 * @param number2 第二个数
	 * @return 结果
	 */
	public static BigDecimal multiply(BigDecimal number1, Number number2) {
		Assert.notNull(number1, "number1 is required");
		Assert.notNull(number2, "number2 is required");
		return number1.multiply(convertNumberToTargetClass(number2, BigDecimal.class));
	}

	/**
	 * 除
	 *
	 * @param number1 第一个数
	 * @param number2 第二个数
	 * @return 结果
	 */
	public static BigDecimal divide(BigDecimal number1, Number number2) {
		return divide(number1, number2, 2, RoundingMode.HALF_UP);
	}

	/**
	 * 除
	 *
	 * @param number1      第一个数
	 * @param number2      第二个数
	 * @param scale        保留小数点后的位数
	 * @param roundingMode rounding模式
	 * @return 结果
	 */
	public static BigDecimal divide(BigDecimal number1, Number number2, int scale, RoundingMode roundingMode) {
		Assert.notNull(number1, "number1 is required");
		Assert.notNull(number2, "number2 is required");
		Assert.notNull(roundingMode, "roundingMode is required");
		return number1.divide(convertNumberToTargetClass(number2, BigDecimal.class), scale, roundingMode);
	}

	/**
	 * 乘方
	 *
	 * @param number1 第一个数
	 * @param n       指数
	 * @return 结果
	 */
	public static BigDecimal pow(BigDecimal number1, int n) {
		Assert.notNull(number1, "number1 is required");
		return number1.pow(n);
	}

	/**
	 * 找出两个数中较小的一个
	 *
	 * @param number1 数字1
	 * @param number2 数字2
	 * @return 参数中最小的一个
	 */
	public static BigDecimal min(BigDecimal number1, BigDecimal number2) {
		Assert.notNull(number1, "number1 is required");
		Assert.notNull(number2, "number2 is required");
		return number1.compareTo(number2) < 0 ? number1 : number2;
	}

	/**
	 * 找出若干个数中最小的一个
	 *
	 * @param numbers 数字
	 * @return 参数中最小的一个
	 */
	public static BigDecimal min(BigDecimal... numbers) {
		Assert.notNull(numbers, "numbers is required");
		Assert.notEmpty(numbers, "numbers is empty");
		Assert.noNullElements(numbers, "numbers has null element(s)");

		BigDecimal min = numbers[0];

		for (BigDecimal number : numbers) {
			min = min(min, number);
		}
		return min;
	}

	/**
	 * 找出若干个数中最小的一个
	 *
	 * @param numbers 数字
	 * @return 参数中最小的一个
	 */
	public static BigDecimal min(Collection<BigDecimal> numbers) {
		Assert.notNull(numbers, "numbers is null");
		return min(numbers.toArray(new BigDecimal[0]));
	}

	/**
	 * 找出两个数中较大的一个
	 *
	 * @param number1 数字1
	 * @param number2 数字2
	 * @return 参数中最小的一个
	 */
	public static BigDecimal max(BigDecimal number1, BigDecimal number2) {
		Assert.notNull(number1, "number1 is required");
		Assert.notNull(number2, "number2 is required");
		return number1.compareTo(number2) > 0 ? number1 : number2;
	}

	/**
	 * 找出若干个数中最大的一个
	 *
	 * @param numbers 数字
	 * @return 参数中最大的一个
	 */
	public static BigDecimal max(BigDecimal... numbers) {
		Assert.notNull(numbers, "numbers is required");
		Assert.notEmpty(numbers, "numbers is empty");
		Assert.noNullElements(numbers, "numbers has null element(s)");

		BigDecimal max = numbers[0];

		for (BigDecimal number : numbers) {
			max = max(max, number);
		}
		return max;
	}

	/**
	 * 找出若干个数中最大的一个
	 *
	 * @param numbers 数字
	 * @return 参数中最大的一个
	 */
	public static BigDecimal max(Collection<BigDecimal> numbers) {
		Assert.notNull(numbers, "numbers is required");
		return max(numbers.toArray(new BigDecimal[0]));
	}

	/**
	 * 求和 (空值不参与求和)
	 *
	 * @param numbers 若干个数
	 * @return 和
	 */
	public static BigDecimal nullSafeAdd(@Nullable BigDecimal... numbers) {
		BigDecimal x = BigDecimal.ZERO;
		if (numbers != null) {
			for (BigDecimal number : numbers) {
				if (number != null) {
					x = x.add(number);
				}
			}
		}
		return x;
	}

	/**
	 * 求和 (空值不参与求和)
	 *
	 * @param numbers 若干个数
	 * @return 和
	 */
	public static BigDecimal nullSafeAdd(@Nullable Collection<BigDecimal> numbers) {
		if (numbers == null) {
			return BigDecimal.ZERO;
		}
		return nullSafeAdd(numbers.toArray(new BigDecimal[0]));
	}

	/**
	 * 求积 (空值不参与求积)
	 *
	 * @param numbers 若干个数
	 * @return 积
	 */
	public static BigDecimal nullSafeMultiply(@Nullable BigDecimal... numbers) {
		BigDecimal x = BigDecimal.ONE;
		if (numbers != null) {
			for (BigDecimal number : numbers) {
				if (number != null) {
					x = x.multiply(number);
				}
			}
		}
		return x;
	}

	/**
	 * 求积 (空值不参与求积)
	 *
	 * @param numbers 若干个数
	 * @return 积
	 */
	public static BigDecimal nullSafeMultiply(@Nullable Collection<BigDecimal> numbers) {
		if (numbers == null) {
			return BigDecimal.ONE;
		}
		return nullSafeMultiply(numbers.toArray(new BigDecimal[0]));
	}

	/**
	 * 将{@code BigDecimal}对象转换成其他类型
	 *
	 * @param number     数据
	 * @param numberType 目标类型
	 * @param <T>        目标类型泛型
	 * @return 转换目标
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Number> T getValue(BigDecimal number, Class<T> numberType) {
		Assert.notNull(number, "number is required");
		Assert.notNull(numberType, "numberType is required");

		if (numberType == Byte.class) {
			return (T) Byte.valueOf(number.byteValue());
		}

		if (numberType == Short.class) {
			return (T) Short.valueOf(number.shortValue());
		}

		if (numberType == Integer.class) {
			return (T) Integer.valueOf(number.intValue());
		}

		if (numberType == Long.class) {
			return (T) Long.valueOf(number.longValue());
		}

		if (numberType == Float.class) {
			return (T) Float.valueOf(number.floatValue());
		}

		if (numberType == Double.class) {
			return (T) Double.valueOf(number.doubleValue());
		}

		if (numberType == BigInteger.class) {
			return (T) number.toBigInteger();
		}

		if (numberType == BigDecimal.class) {
			return (T) number;
		}

		throw new IllegalArgumentException(StringFormatter.format("unsupported number type: {}", numberType.getName()));
	}

}
