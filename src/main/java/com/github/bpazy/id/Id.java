package com.github.bpazy.id;

/**
 * @author ziyuan
 */
public class Id {
    private static IdWorker idWorker = new IdWorker(0);

    public static long next() {
        return idWorker.nextId();
    }
}
