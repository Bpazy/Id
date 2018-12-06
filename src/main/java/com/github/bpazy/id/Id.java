package com.github.bpazy.id;

import com.github.bpazy.id.workerid.IpWorkerIdAssigner;
import com.github.bpazy.id.workerid.WorkerIdAssigner;

/**
 * @author ziyuan
 */
public class Id {
    private static IdWorker idWorker = new IdWorker(findAssigner().assignWorkerId(10L));

    public static long next() {
        return idWorker.nextId();
    }

    private static WorkerIdAssigner findAssigner() {
        return new IpWorkerIdAssigner();
    }
}
