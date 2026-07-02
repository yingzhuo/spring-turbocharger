package com.github.yingzhuo.turbocharger.zxing.qrcode;

import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.*;
import java.util.Objects;

public final class Logo implements Serializable {

	@Nullable
	private Image image;

	@Getter
	private boolean compress = true;

	private Logo() {
		super();
	}

	public static Builder builder() {
		return new Builder();
	}

	public Image getImage() {
		return Objects.requireNonNull(image);
	}

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
