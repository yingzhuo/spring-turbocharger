# spring-turbocharger

[![License](https://img.shields.io/badge/License-Apache%20v2.0-red?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0)
[![Author](https://img.shields.io/badge/yingzhor%40gmail.com-F0FF00?style=flat-square)](mailto:yingzhor@gmail.com)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.yingzhuo/spring-turbocharger-bom.svg?label=Maven%20Central&style=flat-square)](https://search.maven.org/search?q=g:%22com.github.yingzhuo%22%20AND%20a:%22spring-turbocharger-bom%22)

<br>

Another enhancement library of SpringBoot & SpringFramework. Have fun! :)

#### 依赖 (Gradle)

```gradle
// Groovy DSL
dependencies {
    implementation platform("com.github.yingzhuo:spring-turbocharger-bom:$springTurbochargerVersion")
    implementation 'com.github.yingzhuo:spring-turbo-core'
    implementation 'com.github.yingzhuo:spring-turbo-captcha'
    implementation 'com.github.yingzhuo:spring-turbo-freemarker'
    implementation 'com.github.yingzhuo:spring-turbo-hocon'
    implementation 'com.github.yingzhuo:spring-turbo-toml'
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
    implementation 'com.github.yingzhuo:spring-turbo-properties'
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
            <artifactId>spring-turbo-toml</artifactId>
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
        <dependency>
            <groupId>com.github.yingzhuo</groupId>
            <artifactId>spring-turbo-properties</artifactId>
        </dependency>
    </dependencies>
</project>
```

#### 代码仓库

* [github.com](https://github.com/yingzhuo/spring-turbocharger)
* [gitee.com](https://gitee.com/yingzhuo/spring-turbocharger)

#### 从源代码安装

```bash
./gradlew --no-parallel -x 'test' -x 'check' 'publishToMavenLocal'
```

#### 阅读源码

* 建议通过[deepwiki.com](https://deepwiki.com/yingzhuo/spring-turbocharger)阅读

#### 示例程序

* [使用freemarker字符串模板](./projects-example/example-freemarker-stringtemplate)
* [生成QRCode](./projects-example/example-qrcode-gen)
* [生成有状态验证码](./projects-example/example-stateful-captcha)
* [生成无状态验证码](./projects-example/example-stateless-captcha)
* [使用HOCON格式的配置文件](./projects-example/example-hocon-config-file)
* [使用TOML格式的配置文件](./projects-example/example-toml-config-file)
* [使用JWT技术集成SpringSecurity](./projects-example/example-spring-security-jwt)
* [多数据源代理](./projects-example/example-routing-data-source)
* [HttpClient](./projects-example/example-web-client)
* [ID生成器](./projects-example/example-id-generator)

#### 许可证

* [LICENSE](./LICENSE.txt)

#### 联系我

* [应卓](mailto:yingzhor@gmail.com)