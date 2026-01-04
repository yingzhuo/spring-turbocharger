/*
 * Copyright 2022-2026 the original author or authors.
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
 */
package com.github.yingzhuo.turbocharger.jwt;

import com.auth0.jwt.interfaces.Verification;
import com.github.yingzhuo.turbocharger.function.Customizer;

/**
 * {@link Verification} 客制化工具
 *
 * @author 应卓
 * @since 3.5.0
 */
public interface VerificationCustomizer extends Customizer<Verification> {
}
