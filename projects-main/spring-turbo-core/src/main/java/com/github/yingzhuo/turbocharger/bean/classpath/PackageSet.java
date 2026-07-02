package com.github.yingzhuo.turbocharger.bean.classpath;

import com.github.yingzhuo.turbocharger.util.StringUtils;
import org.jspecify.annotations.Nullable;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

public final class PackageSet implements Iterable<String>, Serializable {

	private final SortedSet<String> innerSet = new TreeSet<>(Comparator.naturalOrder());

	public PackageSet() {
		super();
	}

	public PackageSet acceptPackages(@Nullable String... packages) {
		if (packages != null) {
			Stream.of(packages)
				.filter(StringUtils::isNotBlank)
				.map(String::trim)
				.forEach(innerSet::add);
		}
		return this;
	}

	public PackageSet acceptPackages(@Nullable Package... packages) {
		if (packages != null) {
			Stream.of(packages)
				.filter(Objects::nonNull)
				.map(Package::getName)
				.forEach(innerSet::add);
		}
		return this;
	}

	public PackageSet acceptBasePackageClasses(@Nullable Class<?>... baseClasses) {
		if (baseClasses != null) {
			Arrays.stream(baseClasses)
				.filter(Objects::nonNull)
				.map(c -> c.getPackage().getName())
				.forEach(innerSet::add);
		}
		return this;
	}

	@Override
	public Iterator<String> iterator() {
		return innerSet.iterator();
	}

	public PackageSet clear() {
		innerSet.clear();
		return this;
	}

	public boolean isEmpty() {
		return innerSet.isEmpty();
	}

	public int size() {
		return innerSet.size();
	}

	public SortedSet<String> asSet() {
		return Collections.unmodifiableSortedSet(innerSet);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		var strings = (PackageSet) o;
		return innerSet.equals(strings.innerSet);
	}

	@Override
	public int hashCode() {
		return Objects.hash(innerSet);
	}

	@Override
	public String toString() {
		return innerSet.toString();
	}

}
