# spring-turbocharger

[![License](https://img.shields.io/badge/License-Apache%20v2.0-red?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0)
[![Author](https://img.shields.io/badge/yingzhor%40gmail.com-F0FF00?style=flat-square)](mailto:yingzhor@gmail.com)

Another enhancement library of SpringBoot & SpringFramework. Have fun! :)

#### 代码仓库

[![Github](https://img.shields.io/badge/https%3A%2F%2Fgithub.com%2Fyingzhuo%2Fspring--turbocharger-blue?style=flat-square&link=https%3A%2F%2Fgithub.com%2Fyingzhuo%2Fspring-turbocharger)](https://github.com/yingzhuo/spring-turbocharger)
[![Gitee](https://img.shields.io/badge/https%3A%2F%2Fgitee.com%2Fyingzhuo%2Fspring--turbocharger-red?style=flat-square&link=https%3A%2F%2Fgitee.com%2Fyingzhuo%2Fspring-turbocharger)](https://gitee.com/yingzhuo/spring-turbocharger)

#### 最新版本

[![Maven Central](https://img.shields.io/maven-central/v/com.github.yingzhuo/spring-turbocharger-bom.svg?label=Maven%20Central&style=flat-square)](https://search.maven.org/search?q=g:%22com.github.yingzhuo%22%20AND%20a:%22spring-turbocharger-bom%22)

#### 依赖 (Gradle)

```gradle
// Groovy DSL
dependencies {
    implementation platform("com.github.yingzhuo:spring-turbocharger-bom:$springTurbochargerVersion")
    implementation 'com.github.yingzhuo:spring-turbo-core'
    implementation 'com.github.yingzhuo:spring-turbo-captcha'
    implementation 'com.github.yingzhuo:spring-turbo-freemarker'
    implementation 'com.github.yingzhuo:spring-turbo-hocon'
    implementation 'com.github.yingzhuo:spring-turbo-idgen'
    implementation 'com.github.yingzhuo:spring-turbo-jackson'
    implementation 'com.github.yingzhuo:spring-turbo-jdbc'
    implementation 'com.github.yingzhuo:spring-turbo-jwt'
    implementation 'com.github.yingzhuo:spring-turbo-redis'
    implementation 'com.github.yingzhuo:spring-turbo-security'
    implementation 'com.github.yingzhuo:spring-turbo-webcli'
    implementation 'com.github.yingzhuo:spring-turbo-webmvc'
    implementation 'com.github.yingzhuo:spring-turbo-xxljob'
    implementation 'com.github.yingzhuo:spring-turbo-zxing'
}
```

#### 依赖 (Maven)

```xml
<!-- pom.xml -->
<project>
    <dependencies>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbocharger-bom</artifactId>
            <version>${springTurbochargerVersion}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-core</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-captcha</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-freemarker</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-hocon</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-idgen</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-jackson</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-jwt</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-security</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-webcli</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-webmvc</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-xxljob</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-zxing</artifactId>
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
