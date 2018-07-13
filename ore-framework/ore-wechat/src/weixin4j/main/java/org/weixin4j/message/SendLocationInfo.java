package org.weixin4j.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 发送的位置信息
 *
 * @author qsyang
 * @version 1.0
 */
@XmlRootElement(name = "SendLocationInfo")
public class SendLocationInfo {

    //X坐标信息
    private double Location_X;
    //Y坐标信息
    private double Location_Y;
    //精度，可理解为精度或者比例尺、越精细的话 scale越高
    private int Scale;
    //地理位置的字符串信息
    private String Label;
    //朋友圈POI的名字，可能为空
    private String Poiname;

    public double getLocation_X() {
        return Location_X;
    }

    @XmlElement(name = "Location_X")
    public void setLocation_X(double Location_X) {
        this.Location_X = Location_X;
    }

    public double getLocation_Y() {
        return Location_Y;
    }

    @XmlElement(name = "Location_Y")
    public void setLocation_Y(double Location_Y) {
        this.Location_Y = Location_Y;
    }

    public int getScale() {
        return Scale;
    }

    @XmlElement(name = "Scale")
    public void setScale(int Scale) {
        this.Scale = Scale;
    }

    public String getLabel() {
        return Label;
    }

    @XmlElement(name = "Label")
    public void setLabel(String Label) {
        this.Label = Label;
    }

    public String getPoiname() {
        return Poiname;
    }

    @XmlElement(name = "Poiname")
    public void setPoiname(String Poiname) {
        this.Poiname = Poiname;
    }

}
