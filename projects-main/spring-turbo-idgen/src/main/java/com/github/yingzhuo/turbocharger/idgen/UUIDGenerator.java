package com.github.yingzhuo.turbocharger.idgen;

import java.util.UUID;

/**
 * @author 应卓
 * @since 3.4.3
 */
public interface UUIDGenerator {

	public UUID v1();

	public UUID v2(int localIdentifier);

	public UUID v3(String namespace);

	public UUID v4();

	public UUID v5(String namespace);

	public UUID v6();

	public UUID v7();

}
