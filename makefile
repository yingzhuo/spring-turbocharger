MAKEFILE_PATH := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
GRADLE := $(shell which gradle)
GRADLEW := $(MAKEFILE_PATH)/gradlew

.PHONY: \
clean clean-buildsrc purge rebuild-build-logic \
update-dependencies compile build publish install check test \
update-gradle-wrapper update-license-header \
stop-gradle-daemon \
push-to-vcs

clean:
	@$(GRADLEW) "clean" -q

clean-buildsrc:
	@$(GRADLEW) ":buildSrc:clean" -q

purge: clean clean-buildsrc
	@rm -rf $(MAKEFILE_PATH)/.gradle/
	@rm -rf $(MAKEFILE_PATH)/buildSrc/.gradle/

rebuild-build-logic:
	@$(GRADLEW) ':buildSrc:clean' -q
	@$(GRADLEW) ':buildSrc:jar' -q

update-dependencies:
	@$(GRADLEW) -U

compile:
	@$(GRADLEW) "classes"

build:
	@$(GRADLEW) -x "check" -x "test" "build"

install: update-license-header stop-gradle-daemon
	@$(GRADLEW) -x "test" -x "check" "publishToMavenLocal" --no-parallel

publish:
	@$(GRADLEW) -x "test" -x "check" "publishToMavenCentralPortal" --no-parallel

update-gradle-wrapper:
	@$(GRADLE) "wrapper" -q

update-license-header:
	@$(GRADLEW) "applyLicenses" -q

test:
	@$(GRADLEW) "test"

check:
	@$(GRADLEW) "check"

stop-gradle-daemon:
	@$(GRADLEW) --stop -q

push-to-vcs: update-license-header
	@$(GRADLEW) "pushToVcs"
