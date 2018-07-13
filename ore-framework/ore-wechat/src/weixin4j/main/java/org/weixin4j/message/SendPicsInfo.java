package org.weixin4j.message;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 发送的图片信息
 *
 * @author qsyang
 * @version 1.0
 */
@XmlRootElement(name = "SendPicsInfo")
public class SendPicsInfo {

    //发送的图片数量
    private int Count;
    //图片列表
    private List<PicList> PicList;

    public int getCount() {
        return Count;
    }

    @XmlElement(name = "Count")
    public void setCount(int Count) {
        this.Count = Count;
    }

    public List<PicList> getPicList() {
        return PicList;
    }

    @XmlElementWrapper(name = "PicList")
    @XmlElement(name = "item")
    public void setPicList(List<PicList> PicList) {
        this.PicList = PicList;
    }
}
