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

public final class TypeFilterFactories {

	private TypeFilterFactories() {
		super();
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static TypeFilter hasAnnotation(Class<? extends Annotation> annotationType) {
		return hasAnnotation(annotationType, true, true);
	}

	public static TypeFilter hasAnnotation(Class<? extends Annotation> annotationType, boolean considerMetaAnnotations,
										   boolean considerInterfaces) {
		Assert.notNull(annotationType, "annotationType is required");
		return new AnnotationTypeFilter(annotationType, considerMetaAnnotations, considerInterfaces);
	}

	public static TypeFilter assignable(Class<?> targetType) {
		return new AssignableTypeFilter(targetType);
	}

	public static TypeFilter fullyQualifiedNameEquals(String className) {
		return fullyQualifiedNameEquals(className, false);
	}

	public static TypeFilter fullyQualifiedNameEquals(String className, boolean ignoreCase) {
		Assert.hasText(className, "className is required");
		if (ignoreCase) {
			return (reader, readerFactory) -> className.equalsIgnoreCase(reader.getClassMetadata().getClassName());
		} else {
			return (reader, readerFactory) -> className.equals(reader.getClassMetadata().getClassName());
		}
	}

	public static TypeFilter fullyQualifiedNameMatches(Pattern pattern) {
		Assert.notNull(pattern, "pattern is required");
		return new RegexPatternTypeFilter(pattern);
	}

	public static TypeFilter isInterface() {
		return new AbstractClassTestingTypeFilter() {
			@Override
			protected boolean match(ClassMetadata metadata) {
				return metadata.isInterface();
			}
		};
	}

	public static TypeFilter isNotInterface() {
		return not(isInterface());
	}

	public static TypeFilter isAbstract() {
		return new AbstractClassTestingTypeFilter() {
			@Override
			protected boolean match(ClassMetadata metadata) {
				return metadata.isAbstract();
			}
		};
	}

	public static TypeFilter isConcrete() {
		return new AbstractClassTestingTypeFilter() {
			@Override
			protected boolean match(ClassMetadata metadata) {
				return metadata.isConcrete();
			}
		};
	}

	public static TypeFilter isAnnotation() {
		return new AbstractClassTestingTypeFilter() {
			@Override
			protected boolean match(ClassMetadata metadata) {
				return metadata.isAnnotation();
			}
		};
	}

	public static TypeFilter isNotAnnotation() {
		return not(isAnnotation());
	}

	public static TypeFilter isFinal() {
		return new AbstractClassTestingTypeFilter() {
			@Override
			protected boolean match(ClassMetadata metadata) {
				return metadata.isFinal();
			}
		};
	}

	public static TypeFilter isNotFinal() {
		return not(isFinal());
	}

	public static TypeFilter isIndependent() {
		return new AbstractClassTestingTypeFilter() {
			@Override
			protected boolean match(ClassMetadata metadata) {
				return metadata.isIndependent();
			}
		};
	}

	public static TypeFilter hasSuperClass() {
		return new AbstractClassTestingTypeFilter() {
			@Override
			protected boolean match(ClassMetadata metadata) {
				return metadata.hasSuperClass();
			}
		};
	}

	public static TypeFilter isInnerClass() {
		return new AbstractClassTestingTypeFilter() {
			@Override
			protected boolean match(ClassMetadata metadata) {
				return metadata.hasEnclosingClass();
			}
		};
	}

	public static TypeFilter isNotInnerClass() {
		return not(isInnerClass());
	}

	public static TypeFilter implementsInterface(final Class<?> interfaceType) {
		Assert.notNull(interfaceType, "annotationType is required");
		return new AbstractTypeHierarchyTraversingFilter(true, true) {
			@Override
			protected Boolean matchInterface(String interfaceName) {
				return interfaceType.getName().equals(interfaceName);
			}
		};
	}

	public static TypeFilter notImplementsInterface(final Class<?> interfaceType) {
		return not(implementsInterface(interfaceType));
	}

	// -----------------------------------------------------------------------------------------------------------------

	public static TypeFilter not(final TypeFilter f) {
		Assert.notNull(f, "filter is required");
		return (reader, readerFactory) -> !f.match(reader, readerFactory);
	}

	public static TypeFilter any(TypeFilter... filters) {
		Assert.notNull(filters, "filters is null");
		Assert.noNullElements(filters, "filters has null element(s)");
		return new Any(Arrays.asList(filters));
	}

	public static TypeFilter all(TypeFilter... filters) {
		Assert.notNull(filters, "filters is null");
		Assert.noNullElements(filters, "filters has null element(s)");
		return new All(Arrays.asList(filters));
	}

	public static TypeFilter alwaysTrue() {
		return (reader, readerFactory) -> true;
	}

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
