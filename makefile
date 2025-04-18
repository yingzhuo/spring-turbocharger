usage:
	@echo '==============================================================================================================='
	@echo 'usage                :          显示本菜单'
	@echo 'clean                :          清理项目构建产物'
	@echo 'compile              :          编译项目'
	@echo 'install              :          安装到本地maven仓库'
	@echo 'publish              :          推送到 oss.sonatype.org'
	@echo 'setup-gradle-wrapper :          初始化 gradle wrapper'
	@echo 'remove-wrapper       :          移除 gradle wrapper'
	@echo 'add-license-header   :          为源文件添加许可证头'
	@echo 'test                 :          执行单元测试'
	@echo 'check                :          检查代码风格'
	@echo 'github               :          提交文件'
	@echo '==============================================================================================================='

clean:
	@$(CURDIR)/gradlew --project-dir $(CURDIR) --quiet "clean"
	@$(CURDIR)/gradlew --project-dir $(CURDIR)/buildSrc/ --quiet "clean"

compile:
	@$(CURDIR)/gradlew --project-dir $(CURDIR) "classes"

install: add-license-header
	@$(CURDIR)/gradlew --project-dir $(CURDIR) -Dorg.gradle.parallel=false -x "test" -x "check" "publishToMavenLocal"

publish:
	@$(CURDIR)/gradlew --project-dir $(CURDIR) -Dorg.gradle.parallel=false -x "test" -x "check" "uploadToSonatype"

setup-gradle-wrapper:
	@gradle --project-dir $(CURDIR) "wrapper"

remove-wrapper:
	@gradle --project-dir $(CURDIR) "removeWrapper"

add-license-header:
	@$(CURDIR)/gradlew --project-dir $(CURDIR) "addLicenseHeader"

test:
	@$(CURDIR)/gradlew --project-dir $(CURDIR) "test"

check:
	@$(CURDIR)/gradlew --project-dir $(CURDIR) "check"

github: add-license-header
	@$(CURDIR)/gradlew --project-dir $(CURDIR) "github"

.PHONY: \
	usage \
	clean \
	compile publish install \
	check test \
	setup-gradle-wrapper remove-wrapper \
	add-license-header \
	github
