/*
 * 微信公众平台(JAVA) SDK
 *
 * Copyright (c) 2014, Ansitech Network Technology Co.,Ltd All rights reserved.
 * 
 * http://www.weixin4j.org/sdk/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.weixin4j.ticket;

/**
 * 二维码类型
 *
 * @author weixin4j<weixin4j@ansitech.com>
 * @version 1.0
 */
public enum TicketType {

    /**
     * 临时二维码
     */
    QR_SCENE("QR_SCENE"),
    /**
     * 永久二维码
     */
    QR_LIMIT_SCENE("QR_LIMIT_SCENE");

    private String value = "";

    TicketType(String value) {
        this.value = value;
    }

    /**
     * 返回二维码类型值
     *
     * @return the TicketType
     */
    @Override
    public String toString() {
        return value;
    }
}
