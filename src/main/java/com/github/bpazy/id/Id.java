package com.github.bpazy.id;

import com.github.bpazy.id.workerid.WorkerIdAssignerManager;

/**
 * @author ziyuan
 */
public class Id {
    private static IdWorker idWorker = new IdWorker(WorkerIdAssignerManager.assigner().assignWorkerId(10L));

    public static long next() {
        return idWorker.nextId();
    }
}
