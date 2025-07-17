/*
 * Copyright 2025-present the original author or authors.
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
package com.github.yingzhuo.turbocharger.zxing.qrcode;

import lombok.Getter;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.*;
import java.util.Objects;

/**
 * Logo
 *
 * @author 应卓
 * @see #builder()
 * @see java.awt.image.BufferedImage
 * @since 1.0.0
 */
public final class Logo implements Serializable {

	@Nullable
	private Image image;

	@Getter
	private boolean compress = true;

	/**
	 * 私有构造方法
	 */
	private Logo() {
		super();
	}

	public static Builder builder() {
		return new Builder();
	}

	public Image getImage() {
		return Objects.requireNonNull(image);
	}

	/**
	 * 创建器
	 */
	public static final class Builder {

		@Nullable
		private Image image;

		private boolean compress = true;

		private Builder() {
		}

		public Builder image(Image image) {
			this.image = image;
			return this;
		}

		public Builder image(Resource resource) {
			try {
				return image(resource.getFile());
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}

		public Builder image(InputStream inputStream) {
			try {
				this.image = ImageIO.read(inputStream);
				return this;
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}

		public Builder image(ImageInputStream inputStream) {
			try {
				this.image = ImageIO.read(inputStream);
				return this;
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}

		public Builder image(File file) {
			try {
				this.image = ImageIO.read(file);
				return this;
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}

		public Builder compress(boolean compress) {
			this.compress = compress;
			return this;
		}

		public Logo build() {
			Assert.notNull(this.image, "image is not set");
			Logo logo = new Logo();
			logo.image = Objects.requireNonNull(image);
			logo.compress = this.compress;
			return logo;
		}
	}

}
