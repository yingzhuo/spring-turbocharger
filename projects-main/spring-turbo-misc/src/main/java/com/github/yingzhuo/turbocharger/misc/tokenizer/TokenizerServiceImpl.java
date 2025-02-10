/*
 *
 * Copyright 2022-2025 the original author or authors.
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

package com.github.yingzhuo.turbocharger.misc.tokenizer;

import cn.hutool.extra.tokenizer.Result;
import cn.hutool.extra.tokenizer.TokenizerEngine;
import cn.hutool.extra.tokenizer.TokenizerUtil;
import cn.hutool.extra.tokenizer.Word;
import com.github.yingzhuo.turbocharger.util.StringUtils;
import org.springframework.lang.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 * 简易分词服务实现类
 *
 * @author 应卓
 * @since 3.1.1
 */
public class TokenizerServiceImpl implements TokenizerService {

	private final TokenizerEngine engine = TokenizerUtil.createEngine();

	@Override
	public List<String> parse(@Nullable String text) {
		if (StringUtils.isBlank(text)) {
			return List.of();
		}

		Result result = engine.parse(text);

		List<Word> words = new LinkedList<>();
		for (Word word : result) {
			words.add(word);
		}

		return words.stream().map(Word::getText).toList();
	}

}
