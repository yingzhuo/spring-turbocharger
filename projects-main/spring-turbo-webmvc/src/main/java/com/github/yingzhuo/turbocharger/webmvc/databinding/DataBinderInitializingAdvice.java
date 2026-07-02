package com.github.yingzhuo.turbocharger.webmvc.databinding;

import com.github.yingzhuo.turbocharger.databinding.DirectMessageCodesResolver;
import com.github.yingzhuo.turbocharger.databinding.SmartBindingErrorProcessor;
import com.github.yingzhuo.turbocharger.webmvc.autoconfiguration.WebMvcAutoConfiguration;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class DataBinderInitializingAdvice {

	@InitBinder
	public void init(WebDataBinder dataBinder) {
		dataBinder.setMessageCodesResolver(DirectMessageCodesResolver.getInstance());
		dataBinder.setBindingErrorProcessor(SmartBindingErrorProcessor.getInstance());
	}

}
