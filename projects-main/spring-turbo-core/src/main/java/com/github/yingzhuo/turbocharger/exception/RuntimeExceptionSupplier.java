package com.github.yingzhuo.turbocharger.exception;

import java.util.function.Supplier;

@FunctionalInterface
public interface RuntimeExceptionSupplier extends Supplier<RuntimeException> {
}
