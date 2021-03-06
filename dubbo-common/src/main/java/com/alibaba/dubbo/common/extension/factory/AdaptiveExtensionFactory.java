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
package com.alibaba.dubbo.common.extension.factory;

import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.ExtensionFactory;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AdaptiveExtensionFactory
 */
@Adaptive// 自适应类
public class AdaptiveExtensionFactory implements ExtensionFactory {

    private final List<ExtensionFactory> factories;

    public AdaptiveExtensionFactory() {// Dubbo内置了两个ExtensionFactory(SpiExtensionFactory和SpringExtensionFactory)，分别从Dubbo自身的扩展机制和Spring容器中去寻找
        ExtensionLoader<ExtensionFactory> loader = ExtensionLoader.getExtensionLoader(ExtensionFactory.class);
        List<ExtensionFactory> list = new ArrayList<ExtensionFactory>();
        // spring=com.alibaba.dubbo.config.spring.extension.SpringExtensionFactory
        // spi=com.alibaba.dubbo.common.extension.factory.SpiExtensionFactory
        //因为adaptive=com.alibaba.dubbo.common.extension.factory.AdaptiveExtensionFactory是保存在cachedAdaptiveClass上的
        for (String name : loader.getSupportedExtensions()) {
            list.add(loader.getExtension(name));
        }
        factories = Collections.unmodifiableList(list);
    }

    /**
     *  只要分析清楚AdaptiveExtensionFactory类的getExtension方法，
     *  就可以明白这个IOC容器是如何取出需要的SPI实例依赖了
     * @param type object type.
     * @param name object name.
     * @param <T>
     * @return
     */
    @Override
    public <T> T getExtension(Class<T> type, String name) {
        for (ExtensionFactory factory : factories) {// 遍历所有的ExtensionFactory实现
            T extension = factory.getExtension(type, name);
            if (extension != null) {
                return extension;// 找到了就返回
            }
        }
        return null;
    }

}
