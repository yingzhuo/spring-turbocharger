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
package com.github.yingzhuo.turbocharger.bean.classpath;

import com.github.yingzhuo.turbocharger.util.collection.CollectionUtils;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.*;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 本类包含一系列静态方法创建TypeFilter的实例
 *
 * @author 应卓
 * @see TypeFilter
 * @see AbstractClassTestingTypeFilter
 * @see AbstractTypeHierarchyTraversingFilter
 * @see ClassPathScanner
 * @since 1.0.0
 */
public final class TypeFilterFactories {

	/**
	 * 私有构造方法
	 */
	private TypeFilterFactories() {
		super();
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 通过元注释来过滤类型
	 *
	 * @param annotationType 元注释类型
	 * @return TypeFilter的实例
	 */
	public static TypeFilter hasAnnotation(Class<? extends Annotation> annotationType) {
		return hasAnnotation(annotationType, true, true);
	}

	/**
	 * 通过元注释来过滤类型
	 *
	 * @param annotationType          元注释类型
	 * @param considerMetaAnnotations 考虑Meta元注释的情形
	 * @param considerInterfaces      考虑元注释在接口上而不仅仅在实现类上的情形
	 * @return TypeFilter的实例
	 */
	public static TypeFilter hasAnnotation(Class<? extends Annotation> annotationType, boolean considerMetaAnnotations,
										   boolean considerInterfaces) {
		Assert.notNull(annotationType, "annotationType is required");
		return new AnnotationTypeFilter(annotationType, considerMetaAnnotations, considerInterfaces);
	}

	/**
	 * 通过可赋值性过滤类型
	 *
	 * @param targetType 赋值目标类型
	 * @return TypeFilter的实例
	 */
	public static TypeFilter assignable(Class<?> targetType) {
		return new AssignableTypeFilter(targetType);
	}

	/**
	 * 通过类名过滤类型
	 *
	 * @param className 指定的类型
	 * @return TypeFilter的实例
	 */
	public static TypeFilter fullyQualifiedNameEquals(String className) {
		return fullyQualifiedNameEquals(className, false);
	}

	/**
	 * 通过类名过滤类型
	 *
	 * @param className  指定的类型
	 * @param ignoreCase 是否忽略大小写
	 * @return TypeFilter的实例
	 */
	public static TypeFilter fullyQualifiedNameEquals(String className, boolean ignoreCase) {
		Assert.hasText(className, "className is required");
		if (ignoreCase) {
			return (reader, readerFactory) -> className.equalsIgnoreCase(reader.getClassMetadata().getClassName());
		} else {
			return (reader, readerFactory) -> className.equals(reader.getClassMetadata().getClassName());
		}
	}

	/**
	 * 通过正则表达式匹配FQN过滤类型
	 *
	 * @param pattern 正则表达式
	 * @return TypeFilter的实例
	 */
	public static TypeFilter fullyQualifiedNameMatches(Pattern pattern) {
		Assert.notNull(pattern, "pattern is required");
		return new RegexPatternTypeFilter(pattern);
	}

	/**
	 * 过滤是接口的类型
	 *
	 * @return TypeFilter的实例
	 * @see #isNotInterface()
	 */
	public static TypeFilter isInterface() {
		return new AbstractClassTestingTypeFilter() {
			@Override
			protected boolean match(ClassMetadata metadata) {
				return metadata.isInterface();
			}
		};
	}

	/**
	 * 过滤是不是接口的类型
	 *
	 * @return TypeFilter的实例
	 * @see #isInterface()
	 */
	public static TypeFilter isNotInterface() {
		return not(isInterface());
	}

	/**
	 * 过滤抽象的类型。包含接口和抽象类。
	 *
	 * @return TypeFilter的实例
	 * @see #isConcrete()
	 */
	public static TypeFilter isAbstract() {
		return new AbstractClassTestingTypeFilter() {
			@Override
			protected boolean match(ClassMetadata metadata) {
				return metadata.isAbstract();
			}
		};
	}

	/**
	 * 过滤具象的类型
	 *
	 * @return TypeFilter的实例
	 * @see #isAbstract()
	 */
	public static TypeFilter isConcrete() {
		return new AbstractClassTestingTypeFilter() {
			@Override
			protected boolean match(ClassMetadata metadata) {
				return metadata.isConcrete();
			}
		};
	}

	/**
	 * 过滤是元注释的类型
	 *
	 * @return TypeFilter的实例
	 * @see #isNotAnnotation()
	 */
	public static TypeFilter isAnnotation() {
		return new AbstractClassTestingTypeFilter() {
			@Override
			protected boolean match(ClassMetadata metadata) {
				return metadata.isAnnotation();
			}
		};
	}

	/**
	 * 过滤不是元注释的类型
	 *
	 * @return TypeFilter实例
	 * @see #isAnnotation()
	 */
	public static TypeFilter isNotAnnotation() {
		return not(isAnnotation());
	}

	/**
	 * 过滤是Final的类型
	 *
	 * @return TypeFilter的实例
	 * @see #isNotFinal()
	 */
	public static TypeFilter isFinal() {
		return new AbstractClassTestingTypeFilter() {
			@Override
			protected boolean match(ClassMetadata metadata) {
				return metadata.isFinal();
			}
		};
	}

	/**
	 * 过滤是不是Final的类型
	 *
	 * @return TypeFilter的实例
	 * @see #isFinal()
	 */
	public static TypeFilter isNotFinal() {
		return not(isFinal());
	}

	/**
	 * 过滤是顶级类型或静态内部类
	 *
	 * @return TypeFilter的实例
	 */
	public static TypeFilter isIndependent() {
		return new AbstractClassTestingTypeFilter() {
			@Override
			protected boolean match(ClassMetadata metadata) {
				return metadata.isIndependent();
			}
		};
	}

	/**
	 * 过滤有父类型的类型
	 *
	 * @return TypeFilter的实例
	 */
	public static TypeFilter hasSuperClass() {
		return new AbstractClassTestingTypeFilter() {
			@Override
			protected boolean match(ClassMetadata metadata) {
				return metadata.hasSuperClass();
			}
		};
	}

	/**
	 * 过滤内部类的类型
	 *
	 * @return TypeFilter的实例
	 * @see #isNotInnerClass()
	 */
	public static TypeFilter isInnerClass() {
		return new AbstractClassTestingTypeFilter() {
			@Override
			protected boolean match(ClassMetadata metadata) {
				return metadata.hasEnclosingClass();
			}
		};
	}

	/**
	 * 过滤非内部类型
	 *
	 * @return TypeFilter的实例
	 * @see #isInnerClass()
	 */
	public static TypeFilter isNotInnerClass() {
		return not(isInnerClass());
	}

	/**
	 * 过滤实现了指定接口的类型
	 *
	 * @param interfaceType 指定接口
	 * @return TypeFilter的实例
	 * @see #notImplementsInterface(Class)
	 */
	public static TypeFilter implementsInterface(final Class<?> interfaceType) {
		Assert.notNull(interfaceType, "annotationType is required");
		return new AbstractTypeHierarchyTraversingFilter(true, true) {
			@Override
			protected Boolean matchInterface(String interfaceName) {
				return interfaceType.getName().equals(interfaceName);
			}
		};
	}

	/**
	 * 过滤没有实现指定接口的类型
	 *
	 * @param interfaceType 指定接口
	 * @return TypeFilter的实例
	 * @see #implementsInterface(Class)
	 */
	public static TypeFilter notImplementsInterface(final Class<?> interfaceType) {
		return not(implementsInterface(interfaceType));
	}

	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * 逻辑取反
	 *
	 * @param f 代理的TypeFilter实例
	 * @return 装饰后的TypeFilter实例
	 */
	public static TypeFilter not(final TypeFilter f) {
		Assert.notNull(f, "filter is required");
		return (reader, readerFactory) -> !f.match(reader, readerFactory);
	}

	/**
	 * 被装饰的所有TypeFilter任意一个返回true，整体返回true，否则返回false
	 *
	 * @param filters 代理的TypeFilter实例
	 * @return 装饰后的TypeFilter实例
	 */
	public static TypeFilter any(TypeFilter... filters) {
		Assert.notNull(filters, "filters is null");
		Assert.noNullElements(filters, "filters has null element(s)");
		return new Any(Arrays.asList(filters));
	}

	/**
	 * 被装饰的所有TypeFilter任意一个返回false，整体返回false，否则返回true
	 *
	 * @param filters 代理的TypeFilter实例
	 * @return 装饰后的TypeFilter实例
	 */
	public static TypeFilter all(TypeFilter... filters) {
		Assert.notNull(filters, "filters is null");
		Assert.noNullElements(filters, "filters has null element(s)");
		return new All(Arrays.asList(filters));
	}

	/**
	 * 总是返回true的TypeFilter
	 *
	 * @return TypeFilter实例
	 */
	public static TypeFilter alwaysTrue() {
		return (reader, readerFactory) -> true;
	}

	/**
	 * 总是返回false的TypeFilter
	 *
	 * @return TypeFilter实例
	 */
	public static TypeFilter alwaysFalse() {
		return (reader, readerFactory) -> false;
	}

	// -----------------------------------------------------------------------------------------------------------------

	private static final class All implements TypeFilter {

		private final List<TypeFilter> list = new LinkedList<>();

		public All(List<TypeFilter> list) {
			CollectionUtils.nullSafeAddAll(this.list, list);
			Assert.isTrue(this.list.size() >= 2, "list size must greater than 1");
		}

		@Override
		public boolean match(MetadataReader reader, MetadataReaderFactory readerFactory) throws IOException {
			if (CollectionUtils.isEmpty(list)) {
				return false;
			}
			for (TypeFilter filter : list) {
				if (!filter.match(reader, readerFactory)) {
					return false;
				}
			}
			return true;
		}
	}

	private static final class Any implements TypeFilter {
		private final List<TypeFilter> list = new LinkedList<>();

		public Any(List<TypeFilter> list) {
			CollectionUtils.nullSafeAddAll(this.list, list);
			Assert.isTrue(this.list.size() >= 2, "list size must greater than 1");
		}

		@Override
		public boolean match(MetadataReader reader, MetadataReaderFactory readerFactory) throws IOException {
			if (CollectionUtils.isEmpty(list)) {
				return false;
			}
			for (TypeFilter filter : list) {
				if (filter.match(reader, readerFactory)) {
					return true;
				}
			}
			return false;
		}
	}

}
