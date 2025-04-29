usage:
	@echo '==============================================================================================================='
	@echo 'usage                : 显示本菜单'
	@echo 'clean                : 清理项目构建产物'
	@echo 'clean-buildsrc       : 清理项目构建逻辑'
	@echo 'refresh-dependencies : 更新依赖'
	@echo 'compile              : 编译项目'
	@echo 'install              : 安装到本地maven仓库'
	@echo 'publish              : 发布代码到maven中央仓库'
	@echo 'setup-gradle-wrapper : 初始化 gradle wrapper'
	@echo 'remove-wrapper       : 移除 gradle wrapper'
	@echo 'add-license-header   : 为源文件添加许可证头'
	@echo 'test                 : 执行单元测试'
	@echo 'check                : 检查代码风格'
	@echo 'stop-gradle-daemon   : 停止gradle-daemon'
	@echo 'push-all-codes       : 提交文件'
	@echo '==============================================================================================================='

clean:
	gradlew -q "clean"

clean-buildsrc:
	gradlew -q -p $(CURDIR)/buildSrc/ "clean"

refresh-dependencies:
	gradlew -U

compile:
	gradlew "classes"

install: add-license-header
	gradlew --no-parallel -x "test" -x "check" "publishToMavenLocal"

publish: install
	gradlew --no-parallel -x "test" -x "check" "publishToMavenCentralPortal"

setup-gradle-wrapper:
	gradle "wrapper"

remove-wrapper:
	gradle "removeWrapper"

add-license-header:
	gradlew -q "addLicenseHeader"

test:
	gradlew "test"

check:
	gradlew "check"

stop-gradle-daemon:
	gradle -q --stop

push-all-codes: add-license-header
	gradlew -q "pushAllCodes"

.PHONY: \
	usage \
	clean clean-buildsrc \
	refresh-dependencies \
	compile publish install \
	check test \
	setup-gradle-wrapper remove-wrapper \
	add-license-header \
	stop-gradle-daemon \
	push-all-codes
