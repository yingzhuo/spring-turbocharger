package com.github.yingzhuo.turbocharger.zxing.autoconfiguration;

import com.github.yingzhuo.turbocharger.zxing.barcode.BarCodeGenerator;
import com.github.yingzhuo.turbocharger.zxing.barcode.BarCodeGeneratorImpl;
import com.github.yingzhuo.turbocharger.zxing.qrcode.QRCodeGenerator;
import com.github.yingzhuo.turbocharger.zxing.qrcode.QRCodeGeneratorImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ZxingGeneratorAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public QRCodeGenerator qrCodeGenerator() {
		return new QRCodeGeneratorImpl();
	}

	@Bean
	@ConditionalOnMissingBean
	public BarCodeGenerator barCodeGenerator() {
		return new BarCodeGeneratorImpl();
	}

}
