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
package com.github.yingzhuo.turbocharger.zxing.barcode;

import com.github.yingzhuo.turbocharger.zxing.exception.WritingException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import java.awt.image.BufferedImage;
import java.util.EnumMap;

/**
 * @author 应卓
 * @since 3.4.3
 */
public class BarCodeGeneratorImpl implements BarCodeGenerator {

	@Override
	public BufferedImage generate(String content, int width, int height) {
		try {
			var hints = new EnumMap<>(EncodeHintType.class);
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.MARGIN, 1);

			// 生成条形码矩阵
			var bitMatrix = new MultiFormatWriter()
				.encode(
					content,
					BarcodeFormat.CODE_128, // 格式：CODE_128、EAN_13 等
					width,
					height,
					hints
				);

			return MatrixToImageWriter.toBufferedImage(bitMatrix);
		} catch (WriterException e) {
			throw new WritingException(e);
		}
	}

}
