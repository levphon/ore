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
package org.weixin4j.message;

/**
 * 事件类型
 *
 * @author weixin4j<weixin4j@ansitech.com>
 */
public enum EventType {

    /**
     * 订阅
     */
    Subscribe("subscribe"),
    /**
     * 取消订阅
     */
    Unsubscribe("unsubscribe"),
    /**
     * 已关注用户扫描带参数二维码
     */
    Scan("scan"),
    /**
     * 上报地理位置
     */
    Location("location"),
    /**
     * 点击自定义菜单
     */
    Click("click"),
    /**
     * 查看菜单
     */
    View("view"),
    /**
     * 扫码推事件
     */
    Scancode_Push("scancode_push"),
    /**
     * 扫码推事件
     */
    Scancode_Waitmsg("scancode_waitmsg"),
    /**
     * 弹出系统拍照发图的事件
     */
    Pic_Sysphoto("pic_sysphoto"),
    /**
     * 弹出拍照或者相册发图的事件
     */
    Pic_Photo_OR_Album("pic_photo_or_album"),
    /**
     * 弹出微信相册发图器的事件
     */
    Pic_Weixin("pic_weixin"),
    /**
     * 弹出地理位置选择器的事件
     */
    Location_Select("location_select");

    private String value = "";

    EventType(String value) {
        this.value = value;
    }

    /**
     * @return the msgType
     */
    @Override
    public String toString() {
        return value;
    }
}
