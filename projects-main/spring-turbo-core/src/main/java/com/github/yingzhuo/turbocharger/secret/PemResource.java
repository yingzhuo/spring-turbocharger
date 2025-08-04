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
package com.github.yingzhuo.turbocharger.secret;

import org.springframework.boot.ssl.pem.PemContent;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.List;

/**
 * @author 应卓
 * @since 3.5.4
 */
@SuppressWarnings("unchecked")
public class PemResource extends AbstractResource implements Serializable {

    private final @NonNull Resource delegatingResource;
    private final @NonNull PemContent pemContent;
    private final @Nullable String keypass;

    /**
     * 构造方法
     *
     * @param delegatingResource 代理的资源
     */
    public PemResource(Resource delegatingResource) {
        this(delegatingResource, null);
    }

    /**
     * 构造方法
     *
     * @param delegatingResource 代理的资源
     * @param keypass            私钥密码
     */
    public PemResource(Resource delegatingResource, @Nullable String keypass) {
        Assert.notNull(delegatingResource, "delegatingResource must not be null");

        try {
            this.delegatingResource = delegatingResource;
            this.pemContent = PemContent.of(delegatingResource.getContentAsString(StandardCharsets.UTF_8));
            this.keypass = keypass;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return delegatingResource.getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InputStream getInputStream() throws IOException {
        return delegatingResource.getInputStream();
    }

    @Nullable
    public String getKeypass() {
        return keypass;
    }

    public PemContent getPemContent() {
        return pemContent;
    }

    public List<X509Certificate> getCertificates() {
        return pemContent.getCertificates();
    }

    public X509Certificate getCertificate() {
        var certs = pemContent.getCertificates();
        if (certs.size() != 1) {
            throw new IllegalArgumentException("not unique certificate");
        }
        return certs.get(0);
    }

    public <T extends Key> T getKey() {
        return (T) pemContent.getPrivateKey(keypass);
    }

    public KeyPair getKeyPair() {
        return new KeyPair(getCertificate().getPublicKey(), pemContent.getPrivateKey(keypass));
    }

}
