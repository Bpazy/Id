package com.github.bpazy.id;

import lombok.NoArgsConstructor;

/**
 * @author ziyuan
 */
@NoArgsConstructor
public class IdException extends RuntimeException {
    public IdException(String msg) {
        super(msg);
    }
}
