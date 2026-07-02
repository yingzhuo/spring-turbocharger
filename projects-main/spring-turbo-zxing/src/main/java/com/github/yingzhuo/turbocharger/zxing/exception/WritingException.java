package com.github.yingzhuo.turbocharger.zxing.exception;

/**
 * 生成二维码或条形码错误
 *
 * @author 应卓
 * @since 3.4.3
 */
public class WritingException extends RuntimeException {

	/**
	 * 构造方法
	 *
	 * @param exception 其他要检查异常
	 */
	public WritingException(Exception exception) {
		super(exception);
	}

}
