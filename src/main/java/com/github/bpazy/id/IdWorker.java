package com.github.bpazy.id;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ziyuan
 */
@Slf4j
public class IdWorker {

    private long workerId;
    private long datacenterId;
    private long sequence;
    private long twepoch = 1288834974657L;
    private long workerIdBits = 5L;
    private long datacenterIdBits = 5L;
    private long maxWorkerId = ~(-1L << workerIdBits);  // 计算workerIdBots位可以储存的最大正整数，这里及5位可以储存的最大正整数31
    private long maxDatacenterId = ~(-1L << datacenterIdBits); // 同上，31
    private long sequenceBits = 12L;
    private long workerIdShift = sequenceBits;
    private long datacenterIdShift = sequenceBits + workerIdBits;
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    private long sequenceMask = ~(-1L << sequenceBits); // 同maxWorkerId的计算方法，4095
    private long lastTimestamp = -1L;

    public IdWorker(long workerId, long datacenterId, long sequence) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        log.warn("worker starting. timestamp left shift {}, datacenter id bits {}, worker id bits {}, sequence bits {}, workerid{}",
                timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId);

        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            log.error("clock is moving backwards.  Rejecting requests until {}.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

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
                (datacenterId << datacenterIdShift) |
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
