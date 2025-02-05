# SpringTurbocharger

### 依赖

```gradle
// build.gradle

ext {
    set('springTurbochargerVersion', '3.4.2')
}

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

### 安装

```bash
# main分支就是开发分支
./gradlew publishToMavenLocal -x"test"
```

### 示例程序

* [examples](./examples)

### 许可证

* [LICENSE](./LICENSE)
