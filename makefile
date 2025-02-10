usage:
	@echo '==============================================================================================================='
	@echo 'usage:                         显示本菜单'
	@echo 'clean:                         清理本项目'
	@echo 'build:                         构建项目'
	@echo 'install:                       安装到本地maven仓库'
	@echo 'publish:                       推送到 oss.sonatype.org'
	@echo 'gradle-wrapper:                初始化 gradle-wrapper'
	@echo 'github:                        提交文件'
	@echo '==============================================================================================================='

information:
	@$(CURDIR)/gradlew -q :information

clean:
	@$(CURDIR)/gradlew -q clean

build:
	@$(CURDIR)/gradlew -Dorg.gradle.parallel=true -x test build

install:
	@$(CURDIR)/gradlew -Dorg.gradle.parallel=false -x test publishToMavenLocal

publish:
	@$(CURDIR)/gradlew -Dorg.gradle.parallel=false -x test publish

gradle-wrapper:
	@gradle wrapper --gradle-distribution-url "https://mirrors.cloud.tencent.com/gradle/gradle-8.12.1-bin.zip"

github:
	@git status
	@git add .
	@git commit -m "$(shell /bin/date "+%F %T")"
	@git push

.PHONY: usage information clean build publish install gradle-wrapper github
