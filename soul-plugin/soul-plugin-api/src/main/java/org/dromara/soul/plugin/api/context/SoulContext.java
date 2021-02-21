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

package org.dromara.soul.plugin.api.context;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * the soul context.
 *
 * @author xiaoyu(Myth)
 */
public class SoulContext implements Serializable {

    /**
     * is module data.
     */
    private String module;

    /**
     * is method name .
     */
    private String method;

    /**
     * is rpcType data. now we only support "http","dubbo","springCloud","sofa".
     */
    private String rpcType;

    /**
     * httpMethod now we only support "get","post" .
     */
    private String httpMethod;

    /**
     * this is sign .
     */
    private String sign;

    /**
     * timestamp .
     */
    private String timestamp;

    /**
     * appKey .
     */
    private String appKey;

    /**
     * path.
     */
    private String path;
    
    /**
     * the contextPath.
     */
    private String contextPath;

    /**
     * realUrl.
     */
    private String realUrl;

    /**
     * this is dubbo params.
     */
    private String dubboParams;

    /**
     * startDateTime.
     */
    private LocalDateTime startDateTime;

    public String getModule() {
        return module;
    }

    public SoulContext setModule(String module) {
        this.module = module;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public SoulContext setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getRpcType() {
        return rpcType;
    }

    public SoulContext setRpcType(String rpcType) {
        this.rpcType = rpcType;
        return this;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public SoulContext setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public SoulContext setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public SoulContext setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public SoulContext setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public String getPath() {
        return path;
    }

    public SoulContext setPath(String path) {
        this.path = path;
        return this;
    }

    public String getContextPath() {
        return contextPath;
    }

    public SoulContext setContextPath(String contextPath) {
        this.contextPath = contextPath;
        return this;
    }

    public String getRealUrl() {
        return realUrl;
    }

    public SoulContext setRealUrl(String realUrl) {
        this.realUrl = realUrl;
        return this;
    }

    public String getDubboParams() {
        return dubboParams;
    }

    public SoulContext setDubboParams(String dubboParams) {
        this.dubboParams = dubboParams;
        return this;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public SoulContext setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
    }
}
