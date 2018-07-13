package org.oreframework.common.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author huangzz
 * @version [1.0.0, 2015-3-24]
 */
public abstract class BaseEntity implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    public static final String TIMEF_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    private Integer Id;
    
    // 创建时间
    private Date createTime;
    
    // 创建时间
    private String createTimeStr;
    
    // 修改时间
    private Date updateTime;
    
    // 修改时间
    private String updateTimeStr;
    
    // 最后修改人
    private String lastOperatorName;
    
    // 最后修改人ID
    private Integer lastOperatorId = 0;
    
    // 描述实体
    private String descString;
    
    private Integer totalRows;
    
    // 用于搜索开始时间
    private String startDate;
    
    // 用于搜索结束时间
    private String endDate;
    
    // 搜索关键字
    private String keyWord;
    
    public Integer getId()
    {
        return Id;
    }
    
    public void setId(Integer id)
    {
        Id = id;
    }
    
    public String getDescString()
    {
        return descString;
    }
    
    public void setDescString(String descString)
    {
        this.descString = descString;
    }
    
    public String getLastOperatorName()
    {
        return lastOperatorName;
    }
    
    public String getUpdateTimeStr()
    {
        if (null != updateTime)
        {
            updateTimeStr = new SimpleDateFormat(TIMEF_FORMAT).format(updateTime);
        }
        return updateTimeStr;
    }
    
    public void setUpdateTimeStr(String updateTimeStr)
    {
        this.updateTimeStr = updateTimeStr;
    }
    
    public String getCreateTimeStr()
    {
        if (null != createTime)
        {
            createTimeStr = new SimpleDateFormat(TIMEF_FORMAT).format(createTime);
        }
        return createTimeStr;
    }
    
    public void setCreateTimeStr(String createTimeStr)
    {
        this.createTimeStr = createTimeStr;
    }
    
    public void setLastOperatorName(String lastOperatorName)
    {
        this.lastOperatorName = lastOperatorName;
    }
    
    public long getLastOperatorId()
    {
        return lastOperatorId;
    }
    
    public void setLastOperatorId(Integer lastOperatorId)
    {
        this.lastOperatorId = lastOperatorId;
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
    
    public Integer getTotalRows()
    {
        return totalRows;
    }
    
    public void setTotalRows(Integer totalRows)
    {
        this.totalRows = totalRows;
    }
    
    public String getStartDate()
    {
        return startDate;
    }
    
    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }
    
    public String getEndDate()
    {
        return endDate;
    }
    
    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }
    
    public String getKeyWord()
    {
        return keyWord;
    }
    
    public void setKeyWord(String keyWord)
    {
        this.keyWord = keyWord;
    }
    
}
