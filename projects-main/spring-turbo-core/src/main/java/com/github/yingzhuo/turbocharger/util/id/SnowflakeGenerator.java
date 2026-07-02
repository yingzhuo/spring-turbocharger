package com.github.yingzhuo.turbocharger.util.id;

public class SnowflakeGenerator {

	private final long twepoch = 1420041600000L;

	private final long workerIdBits = 5L;

	private final long dataCenterIdBits = 5L;

	private final long maxWorkerId = ~(-1L << workerIdBits);

	private final long maxDataCenterId = ~(-1L << dataCenterIdBits);

	private final long sequenceBits = 12L;

	private final long workerIdShift = sequenceBits;

	private final long dataCenterIdShift = sequenceBits + workerIdBits;

	private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

	private final long sequenceMask = ~(-1L << sequenceBits);

	private final long workerId;

	private final long dataCenterId;

	private long sequence = 0L;

	private long lastTimestamp = -1L;

	public SnowflakeGenerator(long workerId, long dataCenterId) {
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(
				String.format("workerId can't be greater than %d or less than 0", maxWorkerId));
		}
		if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
			throw new IllegalArgumentException(
				String.format("data centerId can't be greater than %d or less than 0", maxDataCenterId));
		}
		this.workerId = workerId;
		this.dataCenterId = dataCenterId;
	}

	public synchronized Long nextId() {
		long timestamp = timeGen();

		// 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
		if (timestamp < lastTimestamp) {
			throw new RuntimeException(String.format(
				"Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		}

		// 如果是同一时间生成的，则进行毫秒内序列
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			// 毫秒内序列溢出
			if (sequence == 0) {
				// 阻塞到下一个毫秒,获得新的时间戳
				timestamp = tilNextMillis(lastTimestamp);
			}
		}
		// 时间戳改变，毫秒内序列重置
		else {
			sequence = 0L;
		}

		// 上次生成ID的时间截
		lastTimestamp = timestamp;

		// 移位并通过或运算拼到一起组成64位的ID
		return ((timestamp - twepoch) << timestampLeftShift) //
			| (dataCenterId << dataCenterIdShift) //
			| (workerId << workerIdShift) //
			| sequence;
	}

	private long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

}
