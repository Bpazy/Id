package com.github.bpazy.id;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ziyuan
 */
@Slf4j
public class IdTest {
    // 单节点每毫秒生成的最大数量
    private final static int MAX_TIMES = 4096;

    @Test
    public void nextTest() {
        Set<String> ids = new HashSet<>(MAX_TIMES);
        for (int i = 0; i < MAX_TIMES; i++) {
            ids.add(String.valueOf(Id.next()));
        }

        Assertions.assertEquals(MAX_TIMES, ids.size(), "Generate id failed in 1 millisecond");
    }

    @Test
    public void testNext() {
        Id.next();
    }

    @Test
    @SneakyThrows
    public void testSetAssigner() {
        Class.forName("com.github.bpazy.id.IpWorkerIdAssigner2");
        Id.next();
    }
}