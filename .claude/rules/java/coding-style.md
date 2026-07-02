---
description: 编码风格
paths: "**/*.java"
---

# 编码风格

## 语言级别

- Java 17, 禁止使用 preview 特性(除非项目明确开启)
- 禁止 raw type,必须带泛型参数
- 禁止 `java.util.Date` / `java.util.Calendar`, 统一使用 `java.time.*`
- 当不影响可读性时 局部变量优先使用 `var` 关键字
- 遵循 Google Java Style Guide(使用 google-java-format)
- 缩进 4 空格, 行宽 100
- 禁止使用 tab, 禁止尾随空格
- 导入顺序: static → java* → javax* → org* → com* → 其他
- 标记型接口要使用 `@FunctionalInterface`

## Jar 依赖

- 禁止直接引入未在 BOM 子项目中管理的第三方依赖

## 版权声明策略

- 禁止在任何文件中添加版权声明, 许可证头部注释
- 禁止模仿项目已有文件中的版权声明样式
- 项目合规信息统一放在根目录 LICENSE.txt 文件，不分散到每个源文件

## 文档注释

- 不要生成任何文档注释

## 命名

- 类名 UpperCamelCase,方法/变量 lowerCamelCase
- 常量 `UPPER_SNAKE_CASE`
- 禁止魔法值,提取为常量或枚举型

## Lombok

- 使用Lombok

## POJO

- DTO, VO, BO 不允许使用 Record
- 使用 Lombok 元注释

## 返回值

- 集合返回 empty-list等,禁止返回 null
- 单值可能不存在 → Optional<T>, 但禁止字段类型用 Optional,禁止方法参数用 Optional<T>

## 避免NPE

- 确保每个package都有`package-info.java`, 使用`org.jspecify.annotations.NullMarked`元注释
- 确保 `org.jspecify.annotations.Nullable` 被使用, 不使用其他变体
