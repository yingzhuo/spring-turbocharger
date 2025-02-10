## spring-turbocharger

[![License](https://img.shields.io/badge/License-Apache%20v2.0-red?style=flat-square)](https://www.apache.org/licenses/LICENSE-2.0)
![Version](https://img.shields.io/badge/Maven--Central-v3.4.2.10003-blue?style=flat-square)
[![Author](https://img.shields.io/badge/yingzhor%40gmail.com-F0FF00?style=flat-square)](mailto:yingzhor@gmail.com)
[![Github](https://img.shields.io/badge/https%3A%2F%2Fgithub.com%2Fyingzhuo%2Fspring--turbocharger-8A2BE2?style=flat-square&link=https%3A%2F%2Fgithub.com%2Fyingzhuo%2Fspring-turbocharger)](https://github.com/yingzhuo/spring-turbocharger)
<br>
![SpringBoot](https://img.shields.io/badge/spring--boot-0000FF?style=plastic)
![SpringFramework](https://img.shields.io/badge/spring--framework-0000FF?style=plastic)
![SpringSecurity](https://img.shields.io/badge/spring--security-0000FF?style=plastic)
![jdbc](https://img.shields.io/badge/jdbc-0000FF?style=plastic)
![jwt](https://img.shields.io/badge/jwt-0000FF?style=plastic)
![json](https://img.shields.io/badge/json-0000FF?style=plastic)

#### 依赖 (Gradle)

```gradle
// build.gradle

dependencies {
    implementation "com.github.yingzhuo:spring-turbo-core:${springTurbochargerVersion}"
    implementation "com.github.yingzhuo:spring-turbo-webmvc:${springTurbochargerVersion}"
    implementation "com.github.yingzhuo:spring-turbo-jackson:${springTurbochargerVersion}"
    implementation "com.github.yingzhuo:spring-turbo-webmvc:${springTurbochargerVersion}"
    implementation "com.github.yingzhuo:spring-turbo-webcli:${springTurbochargerVersion}"
    implementation "com.github.yingzhuo:spring-turbo-security:${springTurbochargerVersion}"
    implementation "com.github.yingzhuo:spring-turbo-jwt:${springTurbochargerVersion}"
    implementation "com.github.yingzhuo:spring-turbo-redis:${springTurbochargerVersion}"
    implementation "com.github.yingzhuo:spring-turbo-jdbc:${springTurbochargerVersion}"
    implementation "com.github.yingzhuo:spring-turbo-misc:${springTurbochargerVersion}"
}
```

#### 依赖 (Maven)

```xml

<dependencies>
    <dependency>
        <groupId>com.github.yingzhuo</groupId>
        <artifactId>spring-turbo-core</artifactId>
        <version>${springTurbochargerVersion}</version>
    </dependency>
    <dependency>
        <groupId>com.github.yingzhuo</groupId>
        <artifactId>spring-turbo-webmvc</artifactId>
        <version>${springTurbochargerVersion}</version>
    </dependency>
    <dependency>
        <groupId>com.github.yingzhuo</groupId>
        <artifactId>spring-turbo-jackson</artifactId>
        <version>${springTurbochargerVersion}</version>
    </dependency>
    <dependency>
        <groupId>com.github.yingzhuo</groupId>
        <artifactId>spring-turbo-webcli</artifactId>
        <version>${springTurbochargerVersion}</version>
    </dependency>
    <dependency>
        <groupId>com.github.yingzhuo</groupId>
        <artifactId>spring-turbo-security</artifactId>
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
        <artifactId>spring-turbo-jdbc</artifactId>
        <version>${springTurbochargerVersion}</version>
    </dependency>
    <dependency>
        <groupId>com.github.yingzhuo</groupId>
        <artifactId>spring-turbo-misc</artifactId>
        <version>${springTurbochargerVersion}</version>
    </dependency>
</dependencies>
```

#### 安装

###### 开发环境

| 软件     | 版本         |
|--------|------------|
| OS     | macOS 15.3 |
| JDK    | 17         |
| Gradle | 8.12.1     |
| GnuPG  | 2.4.7      |

###### 脚本

```bash
# main分支为开发分支

gradle publishToMavenLocal -x test
```

#### 示例程序

* [生成QRCode](./examples/qrcode-gen)
* [生成有状态验证码](./examples/stateful-captcha)
* [生成无状态验证码](./examples/stateless-captcha)
* [使用HOCON格式的配置文件](./examples/use-hocon-as-config-file)
* [使用JWT技术集成SpringSecurity](./examples/spring-security-jwt)
* [多数据源代理](./examples/routing-data-source)
* [HttpClient](./examples/web-client)

#### 更新记录

* [CHANGELOG](./CHANGELOG.md)

#### 联系我

* [应卓](mailto:yingzhor@gmail.com)

#### 许可证

* [LICENSE](./LICENSE.txt)
