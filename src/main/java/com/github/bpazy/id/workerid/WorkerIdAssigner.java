package com.github.bpazy.id.workerid;

/**
 * worker id assigner.
 * Implement this interface to custom assigner.
 *
 * @author ziyuan
 */
public interface WorkerIdAssigner {
    /**
     * @return workerId, [0, 1023]
     */
    int assignWorkerId(long workerIdBits);
}
