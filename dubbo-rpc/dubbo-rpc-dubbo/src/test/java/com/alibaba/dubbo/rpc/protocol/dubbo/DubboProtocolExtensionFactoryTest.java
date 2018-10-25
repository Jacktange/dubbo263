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
package com.alibaba.dubbo.rpc.protocol.dubbo;


import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.rpc.Protocol;
import com.alibaba.dubbo.rpc.ProxyFactory;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.protocol.dubbo.support.*;
import com.alibaba.dubbo.rpc.service.EchoService;
import junit.framework.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

/**
 * <code>ProxiesTest</code>
 */

public class DubboProtocolExtensionFactoryTest {

    /**
     *  获取某个SPI接口实例其实就两步：
     *  (1) 获取相应的SPI接口的ExtensionLoader实例
     *  (2) 通过ExtensionLoader实例获取相应的SPI接口实例
     *
     *  要求：
     *  a. 每个Dubbo的SPI接口上都要有SPI的类注解 - @SPI
     *  b. 每个Dubbo的SPI接口的具体实例，可通过调用其所属的ExtensionLoader实例的getExtension方法来获取
     *  c. 每个Dubbo的SPI接口的ExtensionLoader实例又是如何产生的呢？
     *  d. 每个ExtensionLoader实例中的实例属性objectFactory的作用 - ExtensionFactory
     */
    @Test
    public void testGetExtensionLoader() {
        ExtensionLoader extensionLoader = ExtensionLoader.getExtensionLoader(Protocol.class);
        Protocol protocol = (Protocol) extensionLoader.getExtension("dubbo");
        System.out.println(protocol);
    }

}