package org.oreframework.web.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Transient;

import org.oreframework.common.lang.date.DateFormatConstants;
import org.oreframework.common.lang.date.DateUtils;

/**
 * 数据模型基类
 * 
 * @author huangzz 2017年4月16日
 */
public class BaseModel implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    private Integer lastOperatorId;
    
    private String lastOperatorName;
    
    private Date createTime;
    
    private Date updateTime;
    
    @Transient
    private String createTimeFormat;
    
    @Transient
    private String updateTimeFormat;
    
    @Transient
    private String keyWork;
    
    public Integer getLastOperatorId()
    {
        return lastOperatorId;
    }
    
    public void setLastOperatorId(Integer lastOperatorId)
    {
        this.lastOperatorId = lastOperatorId;
    }
    
    public String getLastOperatorName()
    {
        return lastOperatorName;
    }
    
    public void setLastOperatorName(String lastOperatorName)
    {
        this.lastOperatorName = lastOperatorName;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public String getKeyWork()
    {
        return keyWork;
    }
    
    public void setKeyWork(String keyWork)
    {
        this.keyWork = keyWork;
    }
    
    public String getCreateTimeFormat()
    {
        if(null != createTime){
            createTimeFormat = DateUtils.convertDateToString(createTime, DateFormatConstants.DATE_FORMAT);
        }
        return createTimeFormat;
    }
    
    public void setCreateTimeFormat(String createTimeFormat)
    {
        this.createTimeFormat = createTimeFormat;
    }
    
    public String getUpdateTimeFormat()
    {
        if(null != updateTime){
            updateTimeFormat = DateUtils.convertDateToString(updateTime, DateFormatConstants.DATE_FORMAT);
        }
        return updateTimeFormat;
    }
    
    public void setUpdateTimeFormat(String updateTimeFormat)
    {
        this.updateTimeFormat = updateTimeFormat;
    }
    
}
