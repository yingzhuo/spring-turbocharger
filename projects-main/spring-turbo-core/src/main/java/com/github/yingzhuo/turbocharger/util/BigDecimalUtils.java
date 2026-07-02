package com.github.yingzhuo.turbocharger.util;

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Collection;

import static org.springframework.util.NumberUtils.convertNumberToTargetClass;

public final class BigDecimalUtils {

	private BigDecimalUtils() {
		super();
	}

	public static BigDecimal abs(BigDecimal number) {
		Assert.notNull(number, "number is required");
		return number.abs();
	}

	public static BigDecimal add(BigDecimal number1, Number number2) {
		Assert.notNull(number1, "number1 is required");
		Assert.notNull(number2, "number2 is required");
		return number1.add(convertNumberToTargetClass(number2, BigDecimal.class));
	}

	public static BigDecimal subtract(BigDecimal number1, Number number2) {
		Assert.notNull(number1, "number1 is required");
		Assert.notNull(number2, "number2 is required");
		return number1.subtract(convertNumberToTargetClass(number2, BigDecimal.class));
	}

	public static BigDecimal multiply(BigDecimal number1, Number number2) {
		Assert.notNull(number1, "number1 is required");
		Assert.notNull(number2, "number2 is required");
		return number1.multiply(convertNumberToTargetClass(number2, BigDecimal.class));
	}

	public static BigDecimal divide(BigDecimal number1, Number number2) {
		return divide(number1, number2, 2, RoundingMode.HALF_UP);
	}

	public static BigDecimal divide(BigDecimal number1, Number number2, int scale, RoundingMode roundingMode) {
		Assert.notNull(number1, "number1 is required");
		Assert.notNull(number2, "number2 is required");
		Assert.notNull(roundingMode, "roundingMode is required");
		return number1.divide(convertNumberToTargetClass(number2, BigDecimal.class), scale, roundingMode);
	}

	public static BigDecimal pow(BigDecimal number1, int n) {
		Assert.notNull(number1, "number1 is required");
		return number1.pow(n);
	}

	public static BigDecimal min(BigDecimal number1, BigDecimal number2) {
		Assert.notNull(number1, "number1 is required");
		Assert.notNull(number2, "number2 is required");
		return number1.compareTo(number2) < 0 ? number1 : number2;
	}

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

	public static BigDecimal min(Collection<BigDecimal> numbers) {
		Assert.notNull(numbers, "numbers is null");
		return min(numbers.toArray(new BigDecimal[0]));
	}

	public static BigDecimal max(BigDecimal number1, BigDecimal number2) {
		Assert.notNull(number1, "number1 is required");
		Assert.notNull(number2, "number2 is required");
		return number1.compareTo(number2) > 0 ? number1 : number2;
	}

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

	public static BigDecimal max(Collection<BigDecimal> numbers) {
		Assert.notNull(numbers, "numbers is required");
		return max(numbers.toArray(new BigDecimal[0]));
	}

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

	public static BigDecimal nullSafeAdd(@Nullable Collection<BigDecimal> numbers) {
		if (numbers == null) {
			return BigDecimal.ZERO;
		}
		return nullSafeAdd(numbers.toArray(new BigDecimal[0]));
	}

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

	public static BigDecimal nullSafeMultiply(@Nullable Collection<BigDecimal> numbers) {
		if (numbers == null) {
			return BigDecimal.ONE;
		}
		return nullSafeMultiply(numbers.toArray(new BigDecimal[0]));
	}

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
