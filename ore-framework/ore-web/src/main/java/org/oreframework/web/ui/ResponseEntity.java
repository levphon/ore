package org.oreframework.web.ui;

import java.util.List;

public class ResponseEntity<T>
{
    /**
     * 请求序号（DataTables强烈建议将此参数强制转换为int型，以阻止可能的XSS攻击）
     */
    private int draw;
    
    /**
     * 过滤之前的总数据量
     */
    private long recordsTotal;
    
    /**
     * 过滤之后的总数据量
     */
    private long recordsFiltered;
    
    
    /**
     * 错误信息，可选参数
     */
    private String error;
    
    //private Pagination pagination;
    
    /**
     * 需要在表格中显示的数据
     */
    private List<T> data;
    
    public List<T> getData()
    {
        return data;
    }

    public void setData(List<T> data)
    {
        this.data = data;
    }

  
    public int getDraw()
    {
        return draw;
    }

    public void setDraw(int draw)
    {
        this.draw = draw;
    }

    public long getRecordsTotal()
    {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal)
    {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered()
    {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered)
    {
        this.recordsFiltered = recordsFiltered;
    }

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }
    
    
    
}

