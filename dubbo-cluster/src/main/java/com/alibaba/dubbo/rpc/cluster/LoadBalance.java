/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.rpc.cluster;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.cluster.loadbalance.RandomLoadBalance;

import java.util.List;

/**
 * Dubbo 负载均衡策略提供下列四种方式：
 * (1)Random LoadBalance 随机，按权重设置随机概率。 Dubbo的默认负载均衡策略
 * 在一个截面上碰撞的概率高，但调用量越大分布越均匀，而且按概率使用权重后也比较均匀，有利于动态调整提供者权重。
 * (2)RoundRobin LoadBalance 轮循，按公约后的权重设置轮循比率。
 * 存在慢的提供者累积请求问题，比如：第二台机器很慢，但没挂，当请求调到第二台时就卡在那，久而久之，所有请求都卡在调到第二台上。
 * (3)LeastActive LoadBalance 最少活跃调用数，相同活跃数的随机，活跃数指调用前后计数差。
 * 使慢的提供者收到更少请求，因为越慢的提供者的调用前后计数差会越大。
 * (4)ConsistentHash LoadBalance 一致性Hash，相同参数的请求总是发到同一提供者。
 * 当某一台提供者挂时，原本发往该提供者的请求，基于虚拟节点，平摊到其它提供者，不会引起剧烈变动。
 */

/**
 * LoadBalance. (SPI, Singleton, ThreadSafe)
 * <p>
 * <a href="http://en.wikipedia.org/wiki/Load_balancing_(computing)">Load-Balancing</a>
 *
 * @see com.alibaba.dubbo.rpc.cluster.Cluster#join(Directory)
 */
@SPI(RandomLoadBalance.NAME)
public interface LoadBalance {

    /**
     * 从 invokers 列表中选取一个
     *
     * @param invokers   invokers.
     * @param url        refer url
     * @param invocation invocation.
     * @return selected invoker.
     */
    @Adaptive("loadbalance")
    <T> Invoker<T> select(List<Invoker<T>> invokers, URL url, Invocation invocation) throws RpcException;

}