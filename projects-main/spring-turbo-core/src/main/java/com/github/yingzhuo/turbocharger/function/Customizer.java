package com.github.yingzhuo.turbocharger.function;

/**
 * 客制化器
 *
 * @author 应卓
 * @since 3.5.6
 */
@FunctionalInterface
public interface Customizer<T> {

	/**
	 * 客制某组建
	 *
	 * @param component 组建
	 */
	public void customize(T component);

}
