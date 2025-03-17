usage:
	@echo '==============================================================================================================='
	@echo 'usage               :          显示本菜单'
	@echo 'clean               :          清理项目构建产物'
	@echo 'compile             :          编译项目'
	@echo 'install             :          安装到本地maven仓库'
	@echo 'publish             :          推送到 oss.sonatype.org'
	@echo 'setup-gradle-wrapper:          初始化 setup-gradle-wrapper'
	@echo 'add-license-header  :          为源文件添加许可证头'
	@echo 'test                :          执行单元测试'
	@echo 'check               :          检查代码风格'
	@echo 'github              :          提交文件'
	@echo '==============================================================================================================='

clean:
	@$(CURDIR)/gradlew "clean"

compile:
	@$(CURDIR)/gradlew "classes"

install:
	@$(CURDIR)/gradlew -Dorg.gradle.parallel=false -x "test" -x "check" publishToMavenLocal

publish: install
	@$(CURDIR)/gradlew -Dorg.gradle.parallel=false -x "test" -x "check" publish

setup-gradle-wrapper:
	@$(CURDIR)/gradlew "wrapper"

add-license-header:
	@$(CURDIR)/gradlew "addLicenseHeader"

test:
	@$(CURDIR)/gradlew "test"

check:
	@$(CURDIR)/gradlew "check"

github: add-license-header
	@git status
	@git add .
	@git commit -m "$(shell /bin/date "+%F %T")"

.PHONY: \
	usage \
	clean compile publish install \
	check test \
	setup-gradle-wrapper \
	add-license-header \
	github
