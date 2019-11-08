# Kafka 的高可用原理

Kafka 集群由若干个 Broker 组成，Topic 由若干个 Partition 组成，每个 Partition 可存在不同的 Broker 上。可以这样说，一个 Topic 的数据，分散在多个机器上，即每个机器上都存放一部分数据。

## Kafka 0.8 以前

Kafka 0.8 以前是没有高可用机制的。

假设一个 Topic，由 3 个 Partiton 组成。3 个 Partition 在不同机器上，如果其中某一台机器宕掉了，则 Topic 的部分数据就丢失了。

## Kafka 0.8 以后

Kafka 0.8 以后，通过**副本机制**来实现高可用。

每个 Partition 的数据都会同步到其他机器上，形成多个 Partition 副本。从每个 Partition 的副本集合中，选举出 Leader，其他的都是 Follower。Producer 和 Consumer 就和 Leader 打交道：

- 写数据时，Leader 会将所用数据都同步到 Follower 上
- 读数据时，直接读取 Leader 上的数据

这样，若某个 Broker 宕机了，Broker 上的 Partition 在其他机器上是有副本的；若宕机的 Broker 上面有某个 Partition 的 Leader，则此时会从 Follower 中重新选择一个新的 Leader 出来。

注意：Leader 的读写细节

- 写数据时，Producer 向 Leader 写入，接着其他 Follower 主动从 Leader 中 pull 数据，当所有 Follower 同步好数据，就发送确认信息给 Leader，Leader 收到 Follower 的确认后，就返回写成功消息给 Producer。
- 读数据时，只会从 Leader 中读取，但当只有一个消息已被所有 Follower 都同步成功，返回确认后时，这个消息才被消费者读到。
