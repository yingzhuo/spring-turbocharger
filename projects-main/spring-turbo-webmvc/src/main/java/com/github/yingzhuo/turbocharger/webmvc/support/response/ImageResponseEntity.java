package com.github.yingzhuo.turbocharger.webmvc.support.response;

import com.github.yingzhuo.turbocharger.util.io.ImageUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.awt.image.BufferedImage;

public final class ImageResponseEntity extends ResponseEntity<byte[]> {

	private ImageResponseEntity(byte[] body, HttpHeaders headers, HttpStatus status) {
		super(body, headers, status);
	}

	public static Builder builder() {
		return new Builder();
	}

	public final static class Builder {

		private HttpStatus status = HttpStatus.OK;

		private String format = "png";

		@Nullable
		private BufferedImage image;

		private Builder() {
		}

		public Builder status(HttpStatus status) {
			this.status = status;
			return this;
		}

		public Builder image(BufferedImage image) {
			this.image = image;
			return this;
		}

		public Builder format(String format) {
			this.format = format;
			return this;
		}

		public ImageResponseEntity build() {
			final byte[] bytes = ImageUtils.toByteArray(image, format);
			final int size = bytes.length;

			final HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_TYPE, "image/" + format);
			headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(size));
			return new ImageResponseEntity(bytes, headers, status);
		}
	}

}
