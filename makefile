MAKEFILE_PATH := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
GRADLE        := $(MAKEFILE_PATH)/gradlew

usage:
	@echo '==============================================================================================================='
	@echo 'usage (default)       : 显示本菜单'
	@echo 'clean                 : 清理项目构建产物'
	@echo 'clean-buildsrc        : 清理项目构建逻辑'
	@echo 'clean-all             : 清理构建产物和构建逻辑'
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
	@echo 'push-to-vcs           : 提交文件'
	@echo '==============================================================================================================='

clean:
	$(GRADLE) "clean"

clean-buildsrc:
	$(GRADLE) -p $(MAKEFILE_PATH)/buildSrc/ "clean"

clean-all: clean clean-buildsrc

refresh-dependencies:
	$(GRADLE) -U

compile:
	$(GRADLE) "classes"

install: add-license-header
	$(GRADLE) --no-parallel -x "test" -x "check" "publishToMavenLocal"

publish: install
	$(GRADLE) --no-parallel -x "test" -x "check" "publishToMavenCentralPortal"

setup-gradle-wrapper:
	$(GRADLE) "wrapper"

remove-gradle-wrapper:
	$(GRADLE) "removeWrapper"

add-license-header:
	$(GRADLE) "addLicenseHeader"

test:
	$(GRADLE) "test"

check:
	$(GRADLE) "check"

stop-gradle-daemon:
	$(GRADLE) --stop

push-to-vcs: add-license-header
	$(GRADLE) "pushToVcs"

.PHONY: usage \
	clean clean-buildsrc clean-all \
	refresh-dependencies \
	compile publish install \
	check test \
	setup-gradle-wrapper remove-gradle-wrapper \
	add-license-header \
	stop-gradle-daemon \
	push-to-vcs
