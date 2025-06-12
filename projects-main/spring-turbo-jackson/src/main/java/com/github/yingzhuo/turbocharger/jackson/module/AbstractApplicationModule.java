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
package com.github.yingzhuo.turbocharger.jackson.module;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

/**
 * @author 应卓
 * @since 3.5.0
 */
public abstract class AbstractApplicationModule extends Module {

	@Override
	public final String getModuleName() {
		return getClass().getName();
	}

	@Override
	public final Version version() {
		return Version.unknownVersion();
	}

	@Override
	public abstract void setupModule(SetupContext context);

}
