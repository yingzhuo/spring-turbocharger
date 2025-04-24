usage:
	@echo '==============================================================================================================='
	@echo 'usage                : 显示本菜单'
	@echo 'clean                : 清理项目构建产物'
	@echo 'compile              : 编译项目'
	@echo 'install              : 安装到本地maven仓库'
	@echo 'publish              : 发布代码到maven中央仓库'
	@echo 'setup-gradle-wrapper : 初始化 gradle wrapper'
	@echo 'remove-wrapper       : 移除 gradle wrapper'
	@echo 'add-license-header   : 为源文件添加许可证头'
	@echo 'test                 : 执行单元测试'
	@echo 'check                : 检查代码风格'
	@echo 'push-all-codes       : 提交文件'
	@echo 'show-sonatype-info   : 显示sonatype用户名和口令'
	@echo '==============================================================================================================='

clean:
	@$(CURDIR)/gradlew -q "clean"
	@$(CURDIR)/gradlew --project-dir $(CURDIR)/buildSrc/ -q "clean"

compile:
	@$(CURDIR)/gradlew "classes"

install: add-license-header
	@$(CURDIR)/gradlew -Dorg.gradle.parallel=false -x "test" -x "check" "publishToMavenLocal"

publish: install
	@$(CURDIR)/gradlew -Dorg.gradle.parallel=false -x "test" -x "check" "publishToMavenCentralPortal"

setup-gradle-wrapper:
	@gradle "wrapper"

remove-wrapper:
	@gradle "removeWrapper"

add-license-header:
	@$(CURDIR)/gradlew "addLicenseHeader"

test:
	@$(CURDIR)/gradlew "test"

check:
	@$(CURDIR)/gradlew "check"

push-all-codes: add-license-header
	@$(CURDIR)/gradlew "pushAllCodes"

show-sonatype-info:
	@$(CURDIR)/gradlew -q ":showSonatypeInfo"

.PHONY: \
	usage \
	clean compile publish install \
	check test \
	setup-gradle-wrapper remove-wrapper \
	add-license-header \
	push-all-codes show-sonatype-info
