/*
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
 */
package com.github.yingzhuo.turbocharger.jwt;

/**
 * Json Web Token 服务 <br>
 * 核心服务
 *
 * @author 应卓
 * @since 3.1.1
 */
public interface JwtService {

	/**
	 * 生成JWT令牌
	 *
	 * @param data 令牌数据
	 * @return JWT令牌
	 */
	public String createToken(JwtData data);

	/**
	 * 验证令牌是否合法
	 *
	 * @param token 令牌
	 * @return 验证结果
	 * @see ValidatingResult
	 */
	public ValidatingResult validateToken(String token);

}
