/*
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
 */
package com.github.yingzhuo.turbocharger.captcha.google.font;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @since 1.0.0
 */
public class RandomFontFactory implements FontFactory {

	protected List<String> families;
	protected int minSize;
	protected int maxSize;
	protected boolean randomStyle;

	public RandomFontFactory() {
		families = new ArrayList<>();
		families.add("Verdana");
		families.add("Tahoma");
		minSize = 45;
		maxSize = 45;
	}

	public RandomFontFactory(List<String> families) {
		this();
		this.families = families;
	}

	public RandomFontFactory(String[] families) {
		this();
		this.families = Arrays.asList(families);
	}

	public RandomFontFactory(int size, List<String> families) {
		this(families);
		minSize = maxSize = size;
	}

	public RandomFontFactory(int size, String[] families) {
		this(families);
		minSize = maxSize = size;
	}

	public void setFamilies(List<String> families) {
		this.families = families;
	}

	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public void setRandomStyle(boolean randomStyle) {
		this.randomStyle = randomStyle;
	}

	@Override
	public Font getFont(int index) {
		Random r = new Random();
		String family = families.get(r.nextInt(families.size()));
		boolean bold = r.nextBoolean() && randomStyle;
		int size = minSize;
		if (maxSize - minSize > 0) {
			size += r.nextInt(maxSize - minSize);
		}
		return new Font(family, bold ? Font.BOLD : Font.PLAIN, size);
	}

}
