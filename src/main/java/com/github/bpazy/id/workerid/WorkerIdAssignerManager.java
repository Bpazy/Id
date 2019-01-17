package com.github.bpazy.id.workerid;

import lombok.Setter;

/**
 * @author ziyuan
 */
public class WorkerIdAssignerManager {
    @Setter
    private static WorkerIdAssigner assigner;

    public static WorkerIdAssigner assigner() {
        if (assigner != null) {
            return assigner;
        }
        return new IpWorkerIdAssigner();
    }
}
