package com.github.yingzhuo.turbocharger.webmvc.databinding;

import com.github.yingzhuo.turbocharger.databinding.DirectMessageCodesResolver;
import com.github.yingzhuo.turbocharger.databinding.SmartBindingErrorProcessor;
import com.github.yingzhuo.turbocharger.webmvc.autoconfiguration.WebMvcAutoConfiguration;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author 应卓
 * @see WebMvcAutoConfiguration
 * @since 3.3.1
 */
@ControllerAdvice
public class DataBinderInitializingAdvice {

	@InitBinder
	public void init(WebDataBinder dataBinder) {
		dataBinder.setMessageCodesResolver(DirectMessageCodesResolver.getInstance());
		dataBinder.setBindingErrorProcessor(SmartBindingErrorProcessor.getInstance());
	}

}
