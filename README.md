# Id
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.bpazy/id/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.bpazy/id)

分布式id生成，整体随时间递增。

最大支持1024个节点，每个节点每毫秒生成ID上限为4096个。

## Usage
```java
public class Main {
    public static void main(String[] args) {
        System.out.println(Id.next());
    }
}
```

reference by [https://segmentfault.com/a/1190000011282426](https://segmentfault.com/a/1190000011282426)
