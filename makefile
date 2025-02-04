clean:
	@gradlew clean

build:
	@gradlew build -x"test"

install: build
	@gradlew publishToMavenLocal -Dorg.gradle.parallel=true=false -x"test"

publish: install
	@gradlew publish -Dorg.gradle.parallel=true=false -x"test"

github: clean
	@git status
	@git add .
	@git commit -m "$(shell /bin/date "+%F %T")"
	@git push

wrapper:
	@gradle wrapper --gradle-distribution-url 'https://mirrors.cloud.tencent.com/gradle/gradle-8.12.1-bin.zip'

.PHONY: wrapper clean build publish install github