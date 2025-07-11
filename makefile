MAKEFILE_PATH := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
GRADLE        := $(MAKEFILE_PATH)/gradlew

clean:
	$(GRADLE) "clean"

clean-buildsrc:
	$(GRADLE) -p $(MAKEFILE_PATH)/buildSrc/ "clean"

refresh-dependencies:
	$(GRADLE) -U

compile:
	$(GRADLE) "classes"

build:
	$(GRADLE) --no-parallel -x "check" -x "test" "build"

install: add-license-header
	$(GRADLE) --no-parallel -x "test" -x "check" "publishToMavenLocal"

publish:
	$(GRADLE) --no-parallel -x "test" -x "check" "publishToMavenCentralPortal"

setup-gradle-wrapper:
	gradle "wrapper"

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

.PHONY: usage clean clean-buildsrc refresh-dependencies compile build publish install check test \
	setup-gradle-wrapper add-license-header stop-gradle-daemon push-to-vcs
