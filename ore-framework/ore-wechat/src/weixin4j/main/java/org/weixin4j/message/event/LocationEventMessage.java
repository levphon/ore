package org.weixin4j.message.event;

import org.weixin4j.message.EventType;

/**
 * 上报地理位置事件
 *
 * @author qsyang
 * @version 1.0
 */
public class LocationEventMessage extends EventMessage {

    //地理位置纬度
    private String Latitude;
    //地理位置经度
    private String Longitude;
    //地理位置精度
    private String Precision;

    @Override
    public String getEvent() {
        return EventType.Location_Select.toString();
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

    public String getPrecision() {
        return Precision;
    }

    public void setPrecision(String Precision) {
        this.Precision = Precision;
    }

}
