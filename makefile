ifeq ($(OS), Windows_NT)
	MAKEFILE_PATH := $(dir $(abspath $(lastword $(MAKEFILE_LIST))))
	GRADLEW := $(MAKEFILE_PATH)/gradlew.bat
else
	MAKEFILE_PATH := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
	GRADLEW := $(MAKEFILE_PATH)/gradlew
endif

.DEFAULT_GOAL := purge

.PHONY: \
	clean clean-buildsrc purge rebuild-build-logic \
	update-dependencies compile build publish install check test \
	update-gradle-wrapper update-license-header \
	stop-gradle-daemon \
	push-to-vcs

.SILENT:

clean:
	$(GRADLEW) "clean" -q

clean-buildsrc:
	$(GRADLEW) ":buildSrc:clean" -q

purge: clean clean-buildsrc
	rm -rf $(MAKEFILE_PATH)/.gradle/
	rm -rf $(MAKEFILE_PATH)/buildSrc/.gradle/

rebuild-build-logic:
	$(GRADLEW) ':buildSrc:clean' -q
	$(GRADLEW) ':buildSrc:jar' -q

update-dependencies:
	$(GRADLEW) -U

compile:
	$(GRADLEW) "classes"

build:
	$(GRADLEW) -x "check" -x "test" "build"

install: update-license-header stop-gradle-daemon
	$(GRADLEW) -x "test" -x "check" "publishToMavenLocal" --no-parallel

publish:
	echo "警告：即将发布到 Maven 中央仓库！"
	read -p "确认继续？(yes/no) " confirm && [ $$confirm = "yes" ] || exit 1
	$(GRADLEW) -x "test" -x "check" "publishToMavenCentralPortal" --no-parallel

update-gradle-wrapper:
	$(GRADLEW) "wrapper" -q

update-license-header:
	$(GRADLEW) "applyLicenses" -q

test:
	$(GRADLEW) "test"

check:
	$(GRADLEW) "check"

stop-gradle-daemon:
	$(GRADLEW) --stop -q

push-to-vcs: update-license-header
	$(GRADLEW) 'pushToVcs' -q
