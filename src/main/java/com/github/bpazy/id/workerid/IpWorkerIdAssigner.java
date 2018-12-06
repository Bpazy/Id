package com.github.bpazy.id.workerid;

import com.github.bpazy.id.IdException;
import lombok.SneakyThrows;
import lombok.val;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 根据机器IP获取工作进程Id,如果线上机器的IP二进制表示的最后10位不重复,建议使用此种方式。
 * <p>
 * 列如机器的IP为192.168.1.108,二进制表示:11000000 10101000 00000001 01101100，
 * 截取最后10位 01 01101100,转为十进制364,设置workerId为364.
 *
 * @author bingoohuang, ziyuan
 */
public class IpWorkerIdAssigner implements WorkerIdAssigner {
    @Override
    public int assignWorkerId(long workerIdBits) {
        return (int) (getIp() & workerIdBits);
    }

    private long getIp() {
        val inetAddress = getFirstNonLoopbackAddress();
        if (inetAddress != null) {
            return getIp(inetAddress);
        }

        throw new IdException("unable to get local host");
    }

    private long getIp(InetAddress inetAddress) {
        byte[] addr = inetAddress.getAddress();
        return ((addr[0] & 0xFFL) << (3 * 8))
                + ((addr[1] & 0xFFL) << (2 * 8))
                + ((addr[2] & 0xFFL) << (1 * 8))
                + (addr[3] & 0xFFL);
    }

    @SneakyThrows
    private InetAddress getFirstNonLoopbackAddress() {
        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface nextElement = en.nextElement();
            Enumeration<InetAddress> en2 = nextElement.getInetAddresses();
            while (en2.hasMoreElements()) {
                InetAddress addr = en2.nextElement();
                if (addr.isLoopbackAddress()) {
                    continue;
                }
                if (addr instanceof Inet4Address) {
                    return addr;
                }
            }
        }
        return InetAddress.getLocalHost();
    }
}