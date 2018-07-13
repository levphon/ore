package org.oreframework.web.ui;

public class Pagination
{
    
    private int pageStart;
    
    private int currentPage;
    
    private int pageSize;
    
    public int getPageStart()
    {
        return pageStart;
    }
    
    public void setPageStart(int pageStart)
    {
        this.pageStart = pageStart;
    }
    
    public int getCurrentPage()
    {
        return currentPage;
    }
    
    public void setCurrentPage(int currentPage)
    {
        this.currentPage = currentPage;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
}
