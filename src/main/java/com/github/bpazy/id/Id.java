package com.github.bpazy.id;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ziyuan
 */
@Slf4j
public class Id {
    // TODO custom workerId, datacenterId, sequence
    private static IdWorker idWorker = new IdWorker(1, 1, 1);

    public static long next() {
        return idWorker.nextId();
    }
}
