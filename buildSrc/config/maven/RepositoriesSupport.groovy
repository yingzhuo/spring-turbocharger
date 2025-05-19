/*
 *
 * Copyright 2022-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

def apply(settings) {
	settings.dependencyResolutionManagement.repositories {
		mavenLocal()
		maven {
			name = '阿里云'
			url = 'https://maven.aliyun.com/repository/public/'
		}
		maven {
			name = '腾讯云'
			url = 'https://mirrors.cloud.tencent.com/nexus/repository/maven-public/'
		}
		maven {
			name = '华为云'
			url = 'https://repo.huaweicloud.com/repository/maven/'
		}
		maven {
			name = '京东云'
			url = 'https://maven.jdcloud.com/repos/content/groups/public/'
		}
		maven {
			name = '网易云'
			url = 'https://mirrors.163.com/maven/repository/maven-public/'
		}
		mavenCentral()
		gradlePluginPortal()
		maven {
			name = 'spring-release'
			url = 'https://repo.spring.io/release'
		}
		maven {
			name = 'spring-milestone'
			url = 'https://repo.spring.io/milestone'
		}
		maven {
			name = 'spring-snapshot'
			url = 'https://repo.spring.io/snapshot'
		}
		google()
	}
}

return this
