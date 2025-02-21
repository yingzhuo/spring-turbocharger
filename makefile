usage:
	@echo '==============================================================================================================='
	@echo 'usage:                         显示本菜单'
	@echo 'clean:                         清理本项目'
	@echo 'clean-build-source:            清理构建逻辑'
	@echo 'compile:                       编译项目'
	@echo 'build:                         构建项目'
	@echo 'install:                       安装到本地maven仓库'
	@echo 'publish:                       推送到 oss.sonatype.org'
	@echo 'setup-gradle-wrapper:          初始化 setup-gradle-wrapper'
	@echo 'add-java-license-header:       为源文件添加许可证头'
	@echo 'github:                        提交文件'
	@echo '==============================================================================================================='

clean:
	@$(CURDIR)/gradlew -q clean

clean-build-source:
	@$(CURDIR)/gradlew -q -p $(CURDIR)/build-src/ clean

compile:
	@$(CURDIR)/gradlew classes

build:
	@$(CURDIR)/gradlew -Dorg.gradle.parallel=true -x test build

install:
	@$(CURDIR)/gradlew -Dorg.gradle.parallel=false -x test publishToMavenLocal

publish:
	@$(CURDIR)/gradlew -Dorg.gradle.parallel=false -x test publish

setup-gradle-wrapper:
	@gradle wrapper

add-java-license-header:
	@$(CURDIR)/gradlew addJavaLicenseHeader

github:
	@git status
	@git add .
	@git commit -m "$(shell /bin/date "+%F %T")"
	@git push

.PHONY: usage \
	clean compile build publish install \
	setup-gradle-wrapper \
	add-java-license-header \
	github
