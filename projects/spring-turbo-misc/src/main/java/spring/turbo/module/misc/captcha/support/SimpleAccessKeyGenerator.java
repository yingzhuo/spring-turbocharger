package spring.turbo.module.misc.captcha.support;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import spring.turbo.util.StringUtils;
import spring.turbo.util.UUIDGenerators;

import static spring.turbo.util.StringPool.EMPTY;

/**
 * {@link AccessKeyGenerator}的默认实现
 *
 * @author 应卓
 * @since 1.0.1
 */
public class SimpleAccessKeyGenerator implements AccessKeyGenerator, InitializingBean {

//    @ApplicationName
    @Value("spring.application.name:unknown-application")
    private String applicationName;

    @Override
    public String generate() {
        return applicationName + "-captcha-access-key-" + UUIDGenerators.classic32();
    }

    @Override
    public void afterPropertiesSet() {
        this.applicationName = StringUtils.isBlank(this.applicationName) ? EMPTY : applicationName;
    }

}
