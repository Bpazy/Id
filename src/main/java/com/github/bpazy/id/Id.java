package com.github.bpazy.id;

import com.github.bpazy.id.workerid.IpWorkerIdAssigner;
import com.github.bpazy.id.workerid.WorkerIdAssigner;
import lombok.Setter;

/**
 * @author ziyuan
 */
public class Id {
    @Setter
    private static WorkerIdAssigner assigner;
    private static IdWorker idWorker = new IdWorker(findAssigner().assignWorkerId(10L));

    public static long next() {
        return idWorker.nextId();
    }

    private static WorkerIdAssigner findAssigner() {
        if (assigner != null) {
            return assigner;
        }
        return new IpWorkerIdAssigner();
    }
}
