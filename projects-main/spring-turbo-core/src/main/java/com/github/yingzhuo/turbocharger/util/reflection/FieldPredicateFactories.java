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
package com.github.yingzhuo.turbocharger.util.reflection;

import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;

/**
 * @author 应卓
 * @see FieldUtils
 * @since 1.2.1
 */
public final class FieldPredicateFactories {

	/**
	 * 私有构造方法
	 */
	private FieldPredicateFactories() {
	}

	public static Predicate<Field> alwaysTrue() {
		return f -> true;
	}

	public static Predicate<Field> alwaysFalse() {
		return f -> false;
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static Predicate<Field> not(Predicate<Field> predicate) {
		Assert.notNull(predicate, "predicate is required");
		return f -> !predicate.test(f);
	}

	@SafeVarargs
	public static Predicate<Field> any(Predicate<Field>... predicates) {
		Assert.notNull(predicates, "predicates is required");
		Assert.isTrue(predicates.length >= 1, "predicates must greater than 1");
		return m -> {
			for (final Predicate<Field> predicate : predicates) {
				if (predicate.test(m)) {
					return true;
				}
			}
			return false;
		};
	}

	@SafeVarargs
	public static Predicate<Field> all(Predicate<Field>... predicates) {
		Assert.notNull(predicates, "predicates is required");
		Assert.isTrue(predicates.length >= 1, "predicates must greater than 1");
		return m -> {
			for (final Predicate<Field> predicate : predicates) {
				if (!predicate.test(m)) {
					return false;
				}
			}
			return true;
		};
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static Predicate<Field> isCopyableField() {
		return (f -> !(Modifier.isStatic(f.getModifiers()) || Modifier.isFinal(f.getModifiers())));
	}

	public static Predicate<Field> isNotCopyableField() {
		return not(isCopyableField());
	}

	public static Predicate<Field> withAnnotation(Class<? extends Annotation> annotationClass) {
		return m -> m.getAnnotation(annotationClass) != null;
	}

	public static Predicate<Field> withoutAnnotation(Class<? extends Annotation> annotationClass) {
		return not(withAnnotation(annotationClass));
	}

	public static Predicate<Field> isPublic() {
		return m -> Modifier.isPublic(m.getModifiers());
	}

	public static Predicate<Field> isNotPublic() {
		return not(isPublic());
	}

	public static Predicate<Field> isPrivate() {
		return m -> Modifier.isPrivate(m.getModifiers());
	}

	public static Predicate<Field> isNotPrivate() {
		return not(isPrivate());
	}

	public static Predicate<Field> isProtected() {
		return m -> Modifier.isProtected(m.getModifiers());
	}

	public static Predicate<Field> isNotProtected() {
		return not(isProtected());
	}

	public static Predicate<Field> isStatic() {
		return m -> Modifier.isStatic(m.getModifiers());
	}

	public static Predicate<Field> isNotStatic() {
		return not(isStatic());
	}

	public static Predicate<Field> isFinal() {
		return m -> Modifier.isFinal(m.getModifiers());
	}

	public static Predicate<Field> isNotFinal() {
		return not(isFinal());
	}

}
