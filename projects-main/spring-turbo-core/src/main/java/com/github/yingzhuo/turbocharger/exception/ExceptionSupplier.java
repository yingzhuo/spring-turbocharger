package com.github.yingzhuo.turbocharger.exception;

import java.util.function.Supplier;

@FunctionalInterface
public interface ExceptionSupplier extends Supplier<Exception> {
}
