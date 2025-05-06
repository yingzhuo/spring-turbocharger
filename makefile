usage:
	@echo '==============================================================================================================='
	@echo 'usage                 : 显示本菜单'
	@echo 'clean                 : 清理项目构建产物'
	@echo 'clean-buildsrc        : 清理项目构建逻辑'
	@echo 'refresh-dependencies  : 更新依赖'
	@echo 'compile               : 编译项目'
	@echo 'install               : 安装到本地maven仓库'
	@echo 'publish               : 发布代码到maven中央仓库'
	@echo 'setup-gradle-wrapper  : 初始化 gradle wrapper'
	@echo 'remove-gradle-wrapper : 删除 gradle wrapper'
	@echo 'add-license-header    : 为源文件添加许可证头'
	@echo 'test                  : 执行单元测试'
	@echo 'check                 : 检查代码风格'
	@echo 'stop-gradle-daemon    : 停止gradle-daemon'
	@echo 'push-all-codes        : 提交文件'
	@echo '==============================================================================================================='

clean:
	gradle -q "clean"

clean-buildsrc:
	gradle -q -p $(CURDIR)/buildSrc/ "clean"

refresh-dependencies:
	gradle -U

compile:
	gradle "classes"

install: add-license-header
	gradle --no-parallel -x "test" -x "check" "publishToMavenLocal"

publish: install
	gradle --no-parallel -x "test" -x "check" "publishToMavenCentralPortal"

setup-gradle-wrapper:
	gradle "wrapper"

remove-gradle-wrapper:
	gradle "removeWrapper"

add-license-header:
	gradle -q "addLicenseHeader"

test:
	gradle "test"

check:
	gradle "check"

stop-gradle-daemon:
	gradle -q --stop

push-all-codes: add-license-header
	gradle -q "pushAllCodes"

.PHONY: \
	usage \
	clean clean-buildsrc \
	refresh-dependencies \
	compile publish install \
	check test \
	setup-gradle-wrapper remove-gradle-wrapper \
	add-license-header \
	stop-gradle-daemon \
	push-all-codes
