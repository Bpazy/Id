package com.github.bpazy.id;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ziyuan
 */
@Slf4j
public class IdWorker {

    private long workerId;
    private long sequence = 0; // 毫秒内序列，用来记录统一毫秒内生成的id，4095
    private long twepoch = 1288834974657L;
    private long workerIdBits = 10L;
    private long maxWorkerId = ~(-1L << workerIdBits);  // 计算workerIdBots位可以储存的最大正整数，这里及10位可以储存的最大正整数1023
    private long sequenceBits = 12L;
    private long workerIdShift = sequenceBits;
    private long timestampLeftShift = sequenceBits + workerIdBits;
    private long sequenceMask = ~(-1L << sequenceBits); // 同maxWorkerId的计算方法，4095
    private long lastTimestamp = -1L;

    /**
     * @param workerId, [0,1023]
     */
    public IdWorker(long workerId) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        log.warn("Id worker starting. timestamp left shift {}, worker id bits {}, sequence bits {}, worker id {}",
                timestampLeftShift, workerIdBits, sequenceBits, workerId);

        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            log.error("clock is moving backwards.  Rejecting requests until {}.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask; // 通过位运算保证sequence的范围始终在[0, sequenceMask(4095)]
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) |
                (workerId << workerIdShift) |
                sequence;
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
