MAKEFILE_PATH := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
GRADLE := $(shell which gradle)
GRADLEW := $(MAKEFILE_PATH)/gradlew

clean:
	$(GRADLEW) "clean"

clean-buildsrc:
	$(GRADLEW) ":gradle:clean"

purge: clean clean-buildsrc
	@find $(MAKEFILE_PATH) -type f -name ".DS_Store" -delete
	@find $(MAKEFILE_PATH) -type f -name "*.log" -delete
	@rm -rf .gradle/
	@rm -rf $(MAKEFILE_PATH)/.gradle/

refresh-dependencies:
	$(GRADLEW) -U

compile:
	$(GRADLEW) "classes"

build:
	$(GRADLEW) -x "check" -x "test" "build" --no-parallel

install: update-license-header
	$(GRADLEW) -x "test" -x "check" "publishToMavenLocal" --no-parallel

publish:
	$(GRADLEW) -x "test" -x "check" "publishToMavenCentralPortal" --no-parallel

setup-gradle-wrapper:
	$(GRADLE) "wrapper"

update-license-header:
	$(GRADLEW) "licenseFormat"

test:
	$(GRADLEW) "test"

check:
	$(GRADLEW) "check"

stop-gradle-daemon:
	$(GRADLEW) --stop

push-to-vcs: update-license-header
	$(GRADLEW) "pushToVcs"

.PHONY: \
clean clean-buildsrc purge \
refresh-dependencies compile \
build publish install check test \
setup-gradle-wrapper update-license-header \
stop-gradle-daemon push-to-vcs
