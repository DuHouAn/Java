# 一、Kafka 的相关概念

Kafka 是一种高吞吐、分布式、基于**发布订阅模型**的消息系统。Kafka 用于离线和在线消息的消费。主要有以下 3 个功能：

- 消息队列：发布和订阅消息流
- 容错的持久化方式存储记录消息流：Kafka 将消息数据按顺序保存在磁盘上，并在集群内以副本的形式存储以防止数据丢失
- 流式处理平台：在消息发布的时候进行处理，Kafka 提供了一个完整的流式处理类库。

此外，Kafka 依赖 Zookeeper 进行集群的管理。

## Message

消息（Message）是 Kafka 中**最基本的数据单元**。Kafka 消息由一个**定长的 Header 和变长的字节数组**组成。

## Broker

Kafka 集群包含一个或多个服务器，这些服务器就被称为 Broker。

## Topic

Kafka 根据主题（Topic）对消息进行归类，**发布到 Kafka 集群的每条消息（Message）都需要指定一个 Topic**。

## Partition

Partition 即分区，每个 Topic 包含一个或多个分区。

消息发送时都被发送到一个 Topic 中，其本质就是一个目录，而 Topic 由是由一些 Partition Logs（分区日志）组成，其组织结构如下：

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/kafka/k_4.png"/></div>

**每个 Partition 中的消息都是有序的**，生产的消息被不断追加到 Partition Log 上，其中的每一个消息都被赋予了一个唯一的 offset 值，Kafka 通过 **offset 保证消息在分区内的顺序**，offset 的顺序性不跨分区，即 Kafka 只保证在同一个分区内的消息是有序的；同一 Topic 的多个分区内的消息，Kafka 并不保证其顺序性。

**Kafka 集群会保存所有的消息，不管消息有没有被消费**；可以设定消息的过期时间，只有过期的数据才会被自动清除以释放磁盘空间。比如设置消息过期时间为 2 天，那么这 2 天内的所有消息都会被保存到集群中，数据只有超过了 2 天才会被清除。

Kafka 需要维持的元数据只有一个，即消费消息在 Partition 中的 offset 值，Consumer 每消费一个消息，offset就会 +1。其实消息的状态完全是由 Consumer 控制的，**Consumer 可以跟踪和重设这个 offset 值，Consumer 就可以读取任意位置的消息**。

把消息日志以 Partition 的形式存放有多重考虑：

- 第一，方便在集群中扩展，每个 Partition 可以通过调整以适应它所在的机器，而一个 Topic 又可以由多个Partition 组成，因此整个集群就可以适应任意大小的数据了；
- 第二，可以提高并发，因为是**以 Partition 为单位进行读写**，Partition 的个数对应了消费者个生产者的并发度，比如 Partition 的个数为 3，则集群中最多同时有 3 个线程的消费者并发处理数据。

注意：Partition 并不是越多越好的（Partition 的个数不能超过 Broker 结点），原因如下：

- 分区越多，服务端和客户端需要使用的**内存**就越多。
- 会降低一定的可用性。某个 Leader 挂了，相比较较少分区的情况，重新选出 Leader，花的时间就会更长。

## Replication

Kafka 中每个 Partition 可以有多个副本（Replication），每个副本中包含的消息是一样的。

每个分区的副本集合中，都会选举出一个副本作为 Leader 副本，Kafka 在不同的场景下会采用不同的选举策略。**所有的读写请求都由选举出的 Leader 副本处理**，**Follower 副本仅仅是从 Leader 副本处把数据拉取（pull）到本地之后，同步更新到自己的 Log 中**。

一般情况下，同一分区的多个副本会被分配到不同的 Broker上。当 Leader 所在的 Broker 宕机之后，可以重新选举新的 Leader，继续对外提供服务。

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/kafka/k_1.png"/></div>

## Producer

消息生产者（Producer），向 Broker 发送消息的客户端。

**Producer 直接发送消息到 Broker上的 Leader Partition**，不需要经过任何中介或其他路由转发。

**Producer 客户端自己控制着消息被推送（push）到哪些 Partition**。Kafka 提供了接口供用户实现自定义的 Partition，用户可以为每个消息指定一个 Partition Key，通过这个 Key 来实现一些 Hash 分区算法。比如，把 userid 作为 Partition Key 的话，相同 userid 的消息将会被推送到同一个 Partition。**Producer 可以通过随机或者 Hash 等方式将消息平均发送到多个 Partition 上以实现负载均衡**。

### 批量发送消息

批量发送消息是提高吞吐量的重要方式。Kafka Producer 可以将消息在内存中累计到一定数量后作为一个**批量发送请求**。批量发送的数量大小可以通过Producer 的参数控制，参数值可以设置为累计的消息的数量（如 500 条）、累计的时间间隔（如 100ms ）或者累计的数据大小(64 KB)。通过增加 Batch 的大小，可以减少网络请求和磁盘 I / O 的次数，当然具体参数设置需要在**效率**和**时效性**方面做一个权衡。

### 压缩消息

Producer 端可通过 gzip 或者 snappy 格式对消息集合进行压缩。消息在 Producer 端进行压缩，在 Consumer 端进行解压。压缩的好处是减少网络传输的数据量，减轻对网络带宽传输的压力。

### 异步并行发送消息

Producer 可以**异步地并行地**向 Kafka发送消息，但是通常 Producer 在发送完消息之后会得到一个 future 响应，返回的是 offset 值或者发送过程中遇到的错误。通过 request.required.acks 参数来设置 Leader Partition 收到确认的副本个数。ack 参数的具体说明如下：

| ack  |                             说明                             |
| :--: | :----------------------------------------------------------: |
|  0   | Producer **不会等待 Broker 的响应**<br/>Producer 无法知道消息是否发送成功， 这样**可能会导致数据丢失**，但会得到最大的系统吞吐量。 |
|  1   | Producer 会在 **Leader Partition** 收到消息时得到 Broker 的一个确认<br/> 这样会有更好的可靠性，因为客户端会等待直到 Broker 确认收到消息。 |
|  -1  | Producer 会在**所有备份的 Partition** 收到消息时得到 Broker 的确认<br/> 这个设置可以得到最高的可靠性保证。 |

发布消息时，Kafka Client 先构造一条消息，将消息加入到消息集 set 中（Kafka支持批量发布，可以往消息集合中添加多条消息，一次行发布），send 消息时，Producer Client 需指定消息所属的 Topic。

## Consumer

消息消费者（Consumer），从 Broker 读取消息的客户端。

消费者（Consumer）的主要工作是从 Topic 中拉取消息，并对消息进行消费。某个消费者消费到 Partition 的哪个位置（offset）的相关信息，是 Consumer 自己维护的。Consumer 可以自己决定如何读取 Kafka 中的数据。比如，Consumer 可以通过重设 offset 值来重新消费已消费过的数据。不管有没有被消费，Kafka 会保存数据一段时间，这个时间周期是可配置的，只有到了过期时间，Kafka 才会删除这些数据。

这样设计非常巧妙，**避免了 Kafka Server 端维护消费者消费位置的开销**，尤其是在消费数量较多的情况下。另一方面，如果是由 Kafka Server 端管理每个 Consumer 消费状态，一旦 Kafka Server 端出现延时或是消费状态丢失，将会影响大量的 Consumer。另一方面，这一设计也提高了 Consumer 的灵活性，Consumer 可以按照自己需要的顺序和模式拉取消息进行消费。例如：Consumer 可以通过修改其消费的位置实现针对某些特殊 key 的消息进行反复消费，或是跳过某些消息的需求。

Kafka 提供了两套 Consumer Api，分为 Simple Api 和 High-Level Api。

- Simple Api 是一个底层的 API，它维持了一个和单一 Broker 的连接，并且这个 API 是完全无状态的，每次请求都需要指定 offset 值，因此，这套 API 也是最灵活的。

- High-Level API 封装了对集群中一系列 Broker 的访问，可以透明的消费一个 Topic。它自己维持了已消费消息的状态，即每次消费的都是下一个消息。

  High-Level API 还支持以组的形式消费 Topic，如果 Consumers 有同一个组名，那么 Kafka 就相当于一个队列消息服务，而各个 Consumer 均衡地消费相应 Partition 中的数据。若 Consumers 有不同的组名，那么此时 Kafka 就相当于一个广播服务，会把 Topic 中的所有消息广播到每个 Consumer。

  <div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/kafka/k_5.png"/></div>

## Consumer Group

Consumer Group 即消费者组，多个 Consumer 可以组成一个 Consumer Group，一个 Consumer 只能属于一个 Consumer Group。**同一 Consumer Group 中的多个 Consumer 不能同时消费同一个 Partition 上的数据。**如果不同 Consumer Group 订阅了同一 Topic，Consumer Group 彼此之间不会干扰。这样，如果要实现一个消息可以被多个消费者同时消费（“广播”）的效果，则将每个消费者放入单独的一个 Consumer Group；如果要实现一个消息只被一个消费者消费（“独占”）的效果，则将所有的 Consumer 放入一个 Consumer Group 中。

注意：Consumer Group 中消费者的数量并不是越多越好，当其中消费者数量超过分区的数量时，会导致有消费者分配不到分区，从而造成消费者的浪费。

Producer、Consumer 和 Consumer Group 之间的关系如下图：

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/kafka/k_8.png"/></div>



# 二、Kafka 与 Zookeeper 关系

Zookeeper 为 Kafka 提供集群的管理。不仅保存着集群的 Broker、Topic、Partition 等元数据，还负责 Broker 故障发现、Leader 选举、负载均衡等。

- Broker 元数据管理

  在 Zookeeper 上会有一个专门**用来进行 Broker 服务器列表记录**的节点。每个 Broker 在启动时，都会到 Zookeeper 上进行注册，即到 `/brokers/ids` 下创建属于自己的节点，每个 Broker 会将自己的 IP 地址和端口等信息记录到该节点中去。

- Topic 元数据管理

  在 Kafka 中，同一个Topic 的消息会被分成多个分区并将其分布在多个 Broker 上，这些分区信息及与 Broker 的对应关系由 Zookeeper 维护。比如 my-topic 的 Topic 有 2 个分区，对应到 Zookeeper 中会创建这些文件夹：`/brokers/topics/my-topic/Partitions/0`、`/brokers/topics/my-topic/Partitions/1`

- 负载均衡

  对于同一个 Topic 的不同 Partition，Kafka 会将这些 Partition 分布到不同的 Broker 服务器上，生产者产生消息后也会尽量投递到不同 Broker 的 Partition 中，当 Consumer 消费的时候，Zookeeper 可以根据当前的 Partition 数量以及 Consumer 数量来实现动态负载均衡。

# 三、Kafka 集群

## 典型拓扑结构

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/kafka/k_6.png" width="700px"/></div>

Kafka 集群包含若干个 Producer，若干个 Broker （Kafka 集群支持水平扩展，一般 Broker 数量越多，整个 Kafka 集群的吞吐量也就越高），若干个 Consumer Group，以及一个 Zookeeper 集群。

Kafka 通过 Zookeeper 管理集群配置。

Producer 使用 Push 模式将消息发布到 Broker 上，Consumer 使用 Pull 模式从 Broker 上订阅并消费消息。

## Kafka 数据流

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/kafka/k_3.png"/></div>

Producers 往 Brokers 中指定的 Topic Push 消息，Consumers 从 Brokers 里面 Pull 指定 Topic 的消息，然后进行业务处理。 

图中有两个 Topic，并且每个 Partition 上色数据在其他 Broker 服务器上都有一份副本：

- Topic-0 有两个 Partition，Partition-0 和 Partition-1；
- Topic-1 有一个 Partition。

可以看到 Consumer-Group-1 中的 Consumer-2 没有分到 Partition 处理，这是有可能出现的。

# 四、Kafka 的数据存储设计

## Segment 数据文件

Partition 在物理上由多个 Segment 数据文件组成，每个 Segment 数据文件大小相等、按顺序读写。每个 Segment 数据文件的第一个文件名从 0 开始，后续每个 Segment 文件名为上一个全局 Partition 的最大offset，文件扩展名为 .log。在查找指定 Offset 的 Message 中，用二分查找就可以定位到该 Message 在哪个 Segment 数据文件中。

Segment 数据文件会首先被存储在内存中，当 Segment 上的消息条数达到配置值或消息发布时间超过阈值时，Segment 上的消息会被 flush 到磁盘，只有 flush 到磁盘上的消息才能被 Consumer 消费，Segment 达到一定的大小（默认是 1 GB，可配置）后将不会再往该 Segment写数据，Broker 会创建新的 Segment。

## Segment 索引文件

Kafka 为每个 Segment 数据文件都建立了索引文件，索引文件的文件名与数据文件的文件名一致，不同的是索引文件的扩展名为 .index。比如：

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/kafka/k_12.png"/></div>

Segment 索引文件索引文件并不会为数据文件中的每条 Message 都建立索引，而是采用系数索引的方式，每个一定字节建立一条索引。这样可有效降低索引文件大小，方便将索引文件加载到内存中，提高集群的吞吐量。

如下图所示，000000000000036869.index 文件中记录了 (3,497) ，在数据文件中表示第 3 个 Message（在全局Partition 表示第 368772 个 Message)，该 Message 的物理偏移地址为 497。

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/kafka/k_11.png"/></div>

## Partition 中通过 Offset 查找 Message

比如读取 Offset=368776 的 Message，需要通过下面 2 个步骤进行查找：

- 查找 Segment 文件

  其中00000000000000000000.index 表示最开始的文件，起始 Offset=0。第二个文件 00000000000000368769.index 文件的起始偏移量为 368770 (368769 + 1)。同样，第三个文件00000000000000737337.index 文件的起始偏移量为 737338 (737337 + 1) ，其他后续文件依次类推，以起始偏移量命名并排序这些文件，根据 Offset **二分查找**文件列表，快速定位到 00000000000000368769.index 文件。

- 通过 Segment 文件查找 Message

  当 Offset=368776 时，依次定位到 00000000000000368769.index 的元数据物理位置和 00000000000000368769.log 的物理偏移地址，然后再通过 00000000000000368769.log 顺序查找直到 Offset=368776为止。

# 五、Kafka 的高可用原理

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

# 六、Kafka 中一些常见的问题

## 消息消费的顺序问题

消息在被追加到 Partition 的时候都会分配一个特定的偏移量（offset），Kafka 通过偏移量（offset）来保证消息在分区内的顺序性。为了保证 Kafka 中消息消费的顺序，可以采用以下 2 种方法：

- 设置 1 个 Topic 只对应一个 Partition

  破坏了 Kafka 的设计初衷，不推荐使用。

- 发送消息的时候指定 key

  同一个 key 的消息可以保证只发送到同一个 Partition。

**提升：[如何保证消息的顺序性？](https://doocs.github.io/advanced-java/#/./docs/high-concurrency/how-to-ensure-the-order-of-messages)**

## 消息丢失问题

### Producer 丢失数据

如果 Producer 端设置了 `acks=all`，则不会丢失数据。

Leader 在所有的 Follower 都同步到了消息之后，才认为本次写成功。如果没满足这个条件，生产者会进行无限次重试。

### Consumer 丢失数据

默认情况下，Kafka 会自动提交 Offset，Kafka 认为 Consumer 已经处理消息了，但是 Consumer 可能在处理消息的过程中挂掉了。重启系统后，Consumer 会根据提交的 Offset 进行消费，也就丢失了一部分数据。

解决：关闭自动提交 Offset，在处理完之后自己手动提交 Offset，就可以保证数据不会丢失。但可能会存在消息重复消费问题。

### Broker 丢失数据

比较常见的一个场景：Kafka 某个Leader 所在的 Broker 宕机，需要重新选举新的 Leader ，但此时其他的 Follower 部分数据尚未同步完成，选举某个 Follower 成 Leader 后就丢失一部分数据。

所以此时一般设置如下 4 个参数：

- Topic 设置 `replication.factor` 参数

  参数值必须大于 1，要求每个 Partition 必须有至少 2 个副本。

- Kafka 服务端设置 `min.insync.replicas` 参数

  参数值必须大于 1，要求每个 Partition 必须有至少 2 个副本。

- Producer 设置 `acks=all`

  要求每条数据，必须是**写入所有副本，才认为写成功**。

- Producer 端设置 `retries=MAX`

  MAX 即是一个超级大的数字，表示无限次重试。`retries=MAX `要求一旦写入数据失败，就无限重试。

## 消息重复消费问题

Consumer 消费了数据后，每个一段时间，会将已消费过的消息的 Offset 进行提交，这样，重启后，可以继续从上次消费过的 Offset 来继续消费。测试时，直接 kill 进程，然后再重启后，会导致 Consumer 将有些消息处理了，但是还未来得及提交 Offset，重启后，少数消息会再消费一次。

解决：需要结合具体业务来思考，可从以下几个思路来考虑：

- 如果要将数据写入数据库中，先根据主键查查询，如果这数据已存在，就不用插入数据了。
- 向 Redis 中写入数据，可以使用 set，这样数据不会重复
- 基于数据库的唯一键来保证重复数据不会重复插入多条。因为有唯一键约束了，重复数据插入只会报错，不会导致数据库中出现脏数据。

# 七、Kafka 特性总结

Kafka 特性可总结如下：

## 1. 高可用

Kafka 0.8 以前是没有高可用机制的。

Kafka 0.8 以后，通过**副本机制**来实现高可用，基于**副本机制**实现 Kafka 的高可用。

## 2. 持久性

Kafka 集群接收到 Producer 发过来的消息后，将其持久化到磁盘。此外，还支持数据备份。

## 3. 数据不易丢失

通过合理的配置，Kafka 消息不易丢失。

## 4. 高吞吐量

Kafka 高吞吐量的原因：分区、批量发送和压缩消息、顺序读写、零拷贝

### 分区

当生产者向对应 Topic 传递消息，消息通过**负载均衡机制**传递到不同的 Partition 以减轻单个服务器实例的压力；

一个 Consumer Group 中可以有多个 Consumer，多个 Consumer 可以同时消费不同 Partition 的消息，大大的提高了消费者的并行消费能力。

### 批量发送和压缩消息

- 批量发送：在发送消息的时候，Kafka 不会直接将少量数据发送出去，否则每次发送少量的数据会增加网络传输频率，降低网络传输效率。Kafka 会先将消息缓存在内存中，当超过一个的大小或者超过一定的时间，那么会将这些消息进行批量发送。 
- 端到端压缩消息： Kfaka会将这些批量的数据进行压缩，将一批消息打包后进行压缩，发送给 Broker 服务器后，最终这些数据还是提供给消费者用，所以数据在服务器上还是保持压缩状态，不会进行解压，而且频繁的压缩和解压也会降低性能，最终还是以压缩的方式传递到消费者的手上，在 Consumer 端进行解压。

### 顺序读写

Kafka 是个可持久化的日志服务，它将数据以数据日志的形式进行追加，最后持久化在磁盘中。

Kafka 消息存储时依赖于**文件系统**。为了利用数据的**局部相关性**：操作系统从磁盘中**以数据块为单位**读取数据，将一个数据块读入内存中，如果有相邻的数据，就不用再去磁盘中读取。所以，在某些情况下，**顺序磁盘访问能比随机内存访问还要快**。同时在写数据的时候也是将一整块数据块写入磁盘中，大大提升 I / O 效率。

### 零拷贝

普通的数据拷贝 read & write：

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/kafka/k_9.png" width="450px"/></div>

零拷贝主要的任务是**避免 CPU 做大量的数据拷贝任务，减少不必要的拷贝**。

**内存映射文件（Memory Mapped Files，mmap）**在 64 位操作系统中一般可以表示 20G 的数据文件，它的工作原理是直接利用操作系统的页缓存来实现文件到物理内存的直接映射。

<div align="center"><img src="https://github.com/DuHouAn/ImagePro/raw/master/kafka/k_10.png" width="450px"/></div>

显然，使用 mmap 替代 read 很明显减少了 1 次拷贝，当拷贝数据量很大时，无疑提升了效率。

# 补充

- [Kafka史上最详细原理总结](https://blog.csdn.net/lingbo229/article/details/80761778?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-0.no_search_link&spm=1001.2101.3001.4242)