package com.github.yingzhuo.turbocharger.zxing.exception;

import com.google.zxing.WriterException;

public class WritingException extends RuntimeException {

	public WritingException(WriterException exception) {
		super(exception);
	}

}
