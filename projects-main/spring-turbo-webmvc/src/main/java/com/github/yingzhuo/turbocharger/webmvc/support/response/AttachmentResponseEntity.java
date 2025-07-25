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
package com.github.yingzhuo.turbocharger.webmvc.support.response;

import com.github.yingzhuo.turbocharger.util.io.CloseUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 应卓
 * @see #builder()
 * @since 1.0.1
 */
public class AttachmentResponseEntity extends ResponseEntity<byte[]> {

	private AttachmentResponseEntity(byte[] body, MultiValueMap<String, String> headers, HttpStatus status) {
		super(body, headers, status);
	}

	/**
	 * 获取实例
	 *
	 * @return 实例
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * 创建器
	 */
	public final static class Builder {

		private HttpStatus status = HttpStatus.OK;

		private MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

		@Nullable
		private byte[] content;

		@Nullable
		private String attachmentName;

		private Builder() {
		}

		public Builder status(HttpStatus status) {
			this.status = status;
			return this;
		}

		public Builder mediaType(MediaType mediaType) {
			this.mediaType = mediaType;
			return this;
		}

		public Builder content(byte[] bytes) {
			this.content = bytes;
			return this;
		}

		public Builder content(Resource resource) {
			try {
				this.content = StreamUtils.copyToByteArray(resource.getInputStream());
				return this;
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			} finally {
				CloseUtils.closeQuietly(resource);
			}
		}

		public Builder content(File file) {
			return content(new FileSystemResource(file));
		}

		public Builder content(Path path) {
			return content(new FileSystemResource(path));
		}

		public Builder attachmentName(String name) {
			attachmentName = name;
			return this;
		}

		public AttachmentResponseEntity build() {
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.attachment()
				.filename(new String(attachmentName.getBytes(UTF_8), ISO_8859_1)).build());
			headers.add(HttpHeaders.CONTENT_TYPE, mediaType.toString());
			headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(content.length));
			return new AttachmentResponseEntity(content, headers, status);
		}
	}

}
