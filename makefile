MAKEFILE_PATH := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
GRADLE := $(shell which gradle)
GRADLEW := $(MAKEFILE_PATH)/gradlew

clean:
	@$(GRADLEW) "clean" -q

clean-buildsrc:
	@$(GRADLEW) ":gradle:clean" -q

purge: clean clean-buildsrc
	@find $(MAKEFILE_PATH) -type f -name ".DS_Store" -delete
	@find $(MAKEFILE_PATH) -type f -name "*.log" -delete
	@rm -rf $(MAKEFILE_PATH)/.gradle/
	@rm -rf $(MAKEFILE_PATH)/gradle/.gradle/

refresh-dependencies:
	@$(GRADLEW) -U

compile:
	@$(GRADLEW) "classes"

build:
	@$(GRADLEW) -x "check" -x "test" "build"

install: update-license-header
	@$(GRADLEW) -x "test" -x "check" "publishToMavenLocal" --no-parallel

publish:
	@$(GRADLEW) -x "test" -x "check" "publishToMavenCentralPortal" --no-parallel

setup-gradle-wrapper:
	@$(GRADLE) "wrapper" -q

update-license-header:
	@$(GRADLEW) "licenseFormat" -q

test:
	@$(GRADLEW) "test"

check:
	@$(GRADLEW) "check"

stop-gradle-daemon:
	@$(GRADLEW) --stop -q

push-to-vcs: update-license-header
	@$(GRADLEW) "pushToVcs"

.PHONY: clean clean-buildsrc purge \
	refresh-dependencies compile \
	build publish install check test \
	setup-gradle-wrapper update-license-header \
	stop-gradle-daemon push-to-vcs
