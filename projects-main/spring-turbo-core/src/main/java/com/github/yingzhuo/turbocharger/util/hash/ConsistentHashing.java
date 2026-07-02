package com.github.yingzhuo.turbocharger.util.hash;

import org.jspecify.annotations.Nullable;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashing {

	private final int countOfReplicas;
	private final SortedMap<Integer, String> hashCircle = new TreeMap<>();
	private final HashFunction hashFunction;

	public ConsistentHashing(int countOfReplicas) {
		this(countOfReplicas, DigestHashFunction.md5());
	}

	public ConsistentHashing(int countOfReplicas, @Nullable HashFunction hashFunction) {
		Assert.isTrue(countOfReplicas >= 1, "countOfReplicas must be greater or equals 1");
		this.countOfReplicas = countOfReplicas;
		this.hashFunction = Objects.requireNonNullElseGet(hashFunction, DigestHashFunction::md5);
	}

	public ConsistentHashing addNode(String nodeName) {
		for (int i = 0; i < this.countOfReplicas; i++) {
			var virtualNode = nodeName + "&&VN" + i;
			int hash = hashFunction.apply(virtualNode);
			this.hashCircle.put(hash, nodeName);
		}
		return this;
	}

	public String getNode(String key) {
		if (this.hashCircle.isEmpty()) {
			throw new IllegalArgumentException("you should add node first");
		}

		var hash = hashFunction.apply(key);
		var tailMap = hashCircle.tailMap(hash);
		var targetHash = tailMap.isEmpty() ? hashCircle.firstKey() : tailMap.firstKey();
		return hashCircle.get(targetHash);
	}

}
