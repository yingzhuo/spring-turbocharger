MAKEFILE_PATH := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
GRADLE := $(shell which gradle)
GRADLEW := $(MAKEFILE_PATH)/gradlew

clean:
	$(GRADLEW) "clean"

clean-buildsrc:
	$(GRADLEW) -p $(MAKEFILE_PATH)/gradle/ "clean"

refresh-dependencies:
	$(GRADLEW) -U

compile:
	$(GRADLEW) "classes"

build:
	$(GRADLEW) --no-parallel -x "check" -x "test" "build"

install: update-license-header
	$(GRADLEW) --no-parallel -x "test" -x "check" "publishToMavenLocal"

publish:
	$(GRADLEW) --no-parallel -x "test" -x "check" "publishToMavenCentralPortal"

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

.PHONY: usage clean clean-buildsrc refresh-dependencies compile build publish install check test \
	setup-gradle-wrapper update-license-header stop-gradle-daemon push-to-vcs
