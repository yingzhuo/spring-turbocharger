package com.github.yingzhuo.turbocharger.webmvc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 2024-07-10
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ConfigurationProperties(prefix = "springturbo.webmvc")
public class SpringTurboWebMvcProperties implements Serializable {

	private boolean dataBinderInitializingAdvice = true;

}
