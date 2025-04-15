# spring-turbocharger

[![License](https://img.shields.io/badge/License-Apache%20v2.0-red?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0)
[![Author](https://img.shields.io/badge/yingzhor%40gmail.com-F0FF00?style=flat-square)](mailto:yingzhor@gmail.com)
[![Github](https://img.shields.io/badge/https%3A%2F%2Fgithub.com%2Fyingzhuo%2Fspring--turbocharger-8A2BE2?style=flat-square&link=https%3A%2F%2Fgithub.com%2Fyingzhuo%2Fspring-turbocharger)](https://github.com/yingzhuo/spring-turbocharger)

Another enhancement library of SpringBoot & SpringFramework. Have fun! :)

#### 依赖 (Gradle)

```gradle
// build.gradle

dependencies {
    implementation 'com.github.yingzhuo:spring-turbo-core:$springTurbochargerVersion'
    implementation 'com.github.yingzhuo:spring-turbo-captcha:$springTurbochargerVersion'
    implementation 'com.github.yingzhuo:spring-turbo-freemarker:$springTurbochargerVersion'
    implementation 'com.github.yingzhuo:spring-turbo-hocon:$springTurbochargerVersion'
    implementation 'com.github.yingzhuo:spring-turbo-idgen:$springTurbochargerVersion'
    implementation 'com.github.yingzhuo:spring-turbo-jackson:$springTurbochargerVersion'
    implementation 'com.github.yingzhuo:spring-turbo-jdbc:$springTurbochargerVersion'
    implementation 'com.github.yingzhuo:spring-turbo-jwt:$springTurbochargerVersion'
    implementation 'com.github.yingzhuo:spring-turbo-redis:$springTurbochargerVersion'
    implementation 'com.github.yingzhuo:spring-turbo-security:$springTurbochargerVersion'
    implementation 'com.github.yingzhuo:spring-turbo-webcli:$springTurbochargerVersion'
    implementation 'com.github.yingzhuo:spring-turbo-webmvc:$springTurbochargerVersion'
    implementation 'com.github.yingzhuo:spring-turbo-xxljob:$springTurbochargerVersion'
    implementation 'com.github.yingzhuo:spring-turbo-zxing:$springTurbochargerVersion'
}
```

#### 依赖 (Maven)

```xml
<!-- pom.xml -->
<project>
    <dependencies>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-core</artifactId>
            <version>${springTurbochargerVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-captcha</artifactId>
            <version>${springTurbochargerVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-freemarker</artifactId>
            <version>${springTurbochargerVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-hocon</artifactId>
            <version>${springTurbochargerVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-idgen</artifactId>
            <version>${springTurbochargerVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-jackson</artifactId>
            <version>${springTurbochargerVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-jdbc</artifactId>
            <version>${springTurbochargerVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-jwt</artifactId>
            <version>${springTurbochargerVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-redis</artifactId>
            <version>${springTurbochargerVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-security</artifactId>
            <version>${springTurbochargerVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-webcli</artifactId>
            <version>${springTurbochargerVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-webmvc</artifactId>
            <version>${springTurbochargerVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-xxljob</artifactId>
            <version>${springTurbochargerVersion}</version>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-zxing</artifactId>
            <version>${springTurbochargerVersion}</version>
        </dependency>
    </dependencies>
</project>
```

#### 示例程序

* [使用freemarker字符串模板](./projects-example/freemarker-stringtemplate)
* [生成QRCode](./projects-example/qrcode-gen)
* [生成有状态验证码](./projects-example/stateful-captcha)
* [生成无状态验证码](./projects-example/stateless-captcha)
* [使用HOCON格式的配置文件](./projects-example/hocon-config-file)
* [使用JWT技术集成SpringSecurity](./projects-example/spring-security-jwt)
* [多数据源代理](./projects-example/routing-data-source)
* [HttpClient](./projects-example/web-client)
* [ID生成器](./projects-example/id-generator)

#### 联系我

* [应卓](mailto:yingzhor@gmail.com)

#### 许可证

* [LICENSE](./LICENSE.txt)
