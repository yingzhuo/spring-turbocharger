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
package com.github.yingzhuo.turbocharger.miscellaneous.pinyin;

import cn.hutool.extra.pinyin.PinyinUtil;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import static com.github.yingzhuo.turbocharger.util.StringPool.EMPTY;
import static java.util.Objects.requireNonNullElse;

/**
 * @author 应卓
 * @since 3.1.0
 */
public class PinyinServiceImpl implements PinyinService {

	@Override
	public String getPinyin(String text, @Nullable String separator) {
		Assert.hasText(text, "text is null or blank");
		return PinyinUtil.getPinyin(text, requireNonNullElse(separator, EMPTY));
	}

	@Override
	public String getFirstLetter(String text, @Nullable String separator) {
		Assert.hasText(text, "text is null or blank");
		return PinyinUtil.getFirstLetter(text, requireNonNullElse(separator, EMPTY));
	}

}
