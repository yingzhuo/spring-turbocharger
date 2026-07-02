package com.github.yingzhuo.turbocharger.webmvc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ConfigurationProperties(prefix = "springturbo.webmvc")
public class SpringTurboWebMvcProperties implements Serializable {

	private boolean dataBinderInitializingAdvice = true;

}
