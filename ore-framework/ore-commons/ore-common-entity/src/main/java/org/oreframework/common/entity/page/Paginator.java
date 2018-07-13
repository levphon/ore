package org.oreframework.common.entity.page;

import java.util.List;

/**
 * @author huangzz
 * @version [1.0.0, 2015-3-23]
 */
public class Paginator implements java.io.Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    // 每页默认10条数据
    protected int pageSize = 20;
    
    // 当前页
    protected int currentPage = 1;
    
    // 总页数
    protected int totalPages = 0;
    
    // 总数据数
    protected int totalRows = 0;
    
    // 每页的起始行数
    protected int pageStartRow = 0;
    
    // 每页显示数据的终止行数
    protected int pageEndRow = 0;
    
    // 是否分页
    protected boolean pagination = false;
    
    // 是否有下一页
    boolean hasNextPage = false;
    
    // 是否有前一页
    boolean hasPreviousPage = false;
    
    // 用于页面显示
    protected String pagedView;
    
    protected String action;
    
    // 参数对象与返回对象
    Object obj;
    
    // List<Object> resultList; // 返回的结果
    // Map<String,Object> param; //查询入参
    
    // 结果集
    private List<?> resultSet;
    
    public Paginator(int rows, int pageSize)
    {
        this.init(rows, pageSize);
    }
    
    public void initPage(int totalCount, int pageSize, int currentPage)
    {
        this.totalPages = totalCount;
        this.pageSize = pageSize;
        
        totalPages = (totalCount / pageSize) + (totalCount % pageSize > 0 ? 1 : 0);
    }
    
    public Paginator()
    {
        
    }
    
    /**
     * 初始化分页参数:需要先设置totalRows
     * 
     */
    public void init(int rows, int pageSize)
    {
        
        this.pageSize = pageSize;
        
        this.totalRows = rows;
        
        if ((totalRows % pageSize) == 0)
        {
            totalPages = totalRows / pageSize;
        }
        else
        {
            totalPages = totalRows / pageSize + 1;
        }
        
    }
    
    public void init(int rows, int pageSize, int currentPage)
    {
        
        this.pageSize = pageSize;
        
        this.totalRows = rows;
        
        if ((totalRows % pageSize) == 0)
        {
            totalPages = totalRows / pageSize;
        }
        else
        {
            totalPages = totalRows / pageSize + 1;
        }
        if (currentPage != 0)
            gotoPage(currentPage);
        setPagedView();
    }
    
    /**
     * 计算当前页的取值范围：pageStartRow和pageEndRow
     * 
     */
    private void calculatePage()
    {
        if ((currentPage - 1) > 0)
        {
            hasPreviousPage = true;
        }
        else
        {
            hasPreviousPage = false;
        }
        
        if (currentPage >= totalPages)
        {
            hasNextPage = false;
        }
        else
        {
            hasNextPage = true;
        }
        
        if (currentPage * pageSize < totalRows)
        { // 判断是否为最后一页
            pageEndRow = currentPage * pageSize;
            pageStartRow = pageEndRow - pageSize;
        }
        else
        {
            pageEndRow = totalRows;
            pageStartRow = pageSize * (totalPages - 1);
        }
        
    }
    
    /**
     * 直接跳转到指定页数的页面
     * 
     * @param page
     */
    public void gotoPage(int page)
    {
        
        currentPage = page;
        
        calculatePage();
        
        // debug1();
    }
    
    public void debug1()
    {
        
        System.out.println("要显示的页面数据已经封装,具体信息如下：");
        String debug =
            "共有数据数:" + totalRows + "共有页数:" + totalPages + "当前页数为:" + currentPage + "是否有前一页:" + hasPreviousPage
                + "是否有下一页:" + hasNextPage + "开始行数:" + pageStartRow + "终止行数:" + pageEndRow;
        System.out.println(debug);
    }
    
    public void setPagedView(String path)
    {
        
        StringBuffer sb = new StringBuffer();
        sb.append("<TABLE width='100%'  class='content9'>");
        sb.append("<TBODY>");
        sb.append("<TR>");
        sb.append("<TD align=left width='50%'>");
        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
        
        if (hasPreviousPage)
        {
            sb.append("<a href='" + path + "page=1'><IMG title='第一页' src='../../images/0.gif' border=0></a>");
            sb.append("&nbsp;&nbsp;");
            sb.append("<a href='" + path + "page=" + (currentPage - 1)
                + "'><IMG title='上一页' src='../../images/1.gif' border=0></a>");
        }
        else
        {
            sb.append("<IMG title='第一页' src='../../images/0.gif' border=0>");
            sb.append("&nbsp;&nbsp;");
            sb.append("<IMG title='上一页' src='../../images/1.gif' border=0>");
        }
        sb.append("&nbsp;&nbsp;");
        
        if (hasNextPage)
        {
            sb.append("<a href='" + path + "page=" + (currentPage + 1)
                + "'><IMG title='下一页' src='../../images/2.gif' border=0></a>");
            sb.append("&nbsp;&nbsp;");
            sb.append("<a href='" + path + "page=" + totalPages
                + "'><IMG title='最末页' src='../../images/3.gif' border=0></a>");
        }
        else
        {
            sb.append("<IMG title='下一页' src='../../images/2.gif' border=0>");
            sb.append("&nbsp;&nbsp;");
            sb.append("<IMG title='最末页' src='../../images/3.gif' border=0>");
        }
        sb.append("</TD>");
        sb.append("<TD align=right width='50%'>");
        sb.append("&nbsp;每页<INPUT type=text size=5 name=pageSize value=" + pageSize
            + " class='form' style='width:30px;'>");
        sb.append("文档总数: " + totalRows + ", 共" + totalPages + "页, 第" + currentPage + "页, 转到 ");
        sb.append("<INPUT type=text size=5 name=page value=" + currentPage + ">");
        sb.append("&nbsp;");
        sb.append("<INPUT onclick='submit_pagedForm()' type=button class='button2' value=' GO '>");
        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
        sb.append("<INPUT name=totalPages type=hidden value='" + totalPages + "'>");
        sb.append("&nbsp;&nbsp;");
        sb.append("<INPUT name=totalRows type=hidden value='" + totalRows + "'>");
        sb.append("&nbsp;&nbsp;");
        
        sb.append("</TD>");
        sb.append("</TR>");
        sb.append("</TBODY>");
        sb.append("</TABLE>");
        
        sb.append("<script type=\"text/javascript\">\n");
        sb.append("function submit_pagedForm(){\n");
        sb.append(" var page = document.forms[0].page.value;\n");
        sb.append("var reg=eval('/^[0-9]+$/');\n");
        sb.append("var flag = reg.test(page);\n");
        sb.append("if(!flag){\n" + "alert('跳转页必须是数字');\n" + "return false;}\n");
        sb.append("var pageSize = document.forms[0].pageSize.value;\n");
        sb.append("var reg=eval('/^[0-9]+$/');\n");
        sb.append("var flag = reg.test(pageSize);\n");
        sb.append("if(!flag){alert('每页显示数必须是数字');\n" + "return false;}\n");
        sb.append("var totalPages = 0;\n");
        sb.append("totalPages = document.forms[0].totalPages.value;\n");
        sb.append("if (parseInt(page) > parseInt(totalPages) || parseInt(page) <1) {page =1;}\n");
        sb.append("document.forms[0].submit();\n");
        sb.append("}\n");
        sb.append("function search_pagedForm(page) {\n");
        sb.append("document.pagedForm.page.value = page;\n");
        sb.append("document.pagedForm.target='_self';\n");
        sb.append("document.pagedForm.submit();\n");
        sb.append("}\n");
        sb.append("</script>\n");
        pagedView = sb.toString();
        
    }
    
    public void setPagedView()
    {
        
        StringBuffer sb = new StringBuffer();
        sb.append("<TABLE width='100%' class='content9' cellpadding='0' cellspacing='0'>");
        sb.append("<TBODY>");
        sb.append("<TR>");
        sb.append("<TD align=left width='40%'>");
        sb.append("&nbsp;&nbsp;");
        if (hasPreviousPage)
        {
            // sb.append("<a href='#' onclick='search_pagedForm(1);return false;'><IMG title='第一页' src='<%=basePath%>resource/images/firstbtn.png' border=0></a>");
            sb.append("<input type='button' value='首页' class='button' onclick='search_pagedForm(1);return false;'>");
            sb.append("&nbsp;&nbsp;");
            sb.append("<input type='button' value='上一页' class='button' onclick='search_pagedForm(" + (currentPage - 1)
                + ");return false;'>");
            // sb.append("<a href='#' onclick='search_pagedForm("
            // + (currentPage - 1)
            // +
            // ");return false;'><IMG title='上一页' src='../../images/1.gif' border=0></a>");
        }
        else
        {
            sb.append("<input type='button' value='首页' class='button' onclick='search_pagedForm(1);return false;' disabled='disabled'>");
            sb.append("&nbsp;&nbsp;");
            sb.append("<input type='button' value='上一页' class='button' onclick='search_pagedForm(" + (currentPage - 1)
                + ");return false;' disabled='disabled'>");
            // sb.append("<IMG title='第一页' src='../../images/0.gif' border=0>");
            // sb.append("&nbsp;&nbsp;");
            // sb.append("<IMG title='上一页' src='../../images/1.gif' border=0>");
        }
        sb.append("&nbsp;&nbsp;");
        if (hasNextPage)
        {
            sb.append("<input type='button' value='下一页' class='button' onclick='search_pagedForm(" + (currentPage + 1)
                + ");return false;'>");
            // sb.append("<a href='#' onclick='search_pagedForm("
            // + (currentPage + 1)
            // +
            // ");return false;'><IMG title='下一页' src='../../images/2.gif' border=0></a>");
            sb.append("&nbsp;&nbsp;");
            sb.append("<input type='button' value='末页' class='button' onclick='search_pagedForm(" + totalPages
                + ");return false;'>");
            // sb.append("<a href='#' onclick='search_pagedForm("
            // + totalPages
            // +
            // ");return false;'><IMG title='最末页' src='../../images/3.gif' border=0></a>");
        }
        else
        {
            sb.append("<input type='button' value='下一页' class='button' onclick='search_pagedForm(" + (currentPage + 1)
                + ");return false;' disabled='disabled'>");
            sb.append("&nbsp;&nbsp;");
            sb.append("<input type='button' value='末页' class='button' onclick='search_pagedForm(" + totalPages
                + ");return false;' disabled='disabled'>");
            // sb.append("<IMG title='下一页' src='../../images/2.gif' border=0>");
            // sb.append("&nbsp;&nbsp;");
            // sb.append("<IMG title='最末页' src='../../images/3.gif' border=0>");
        }
        sb.append("</TD>");
        sb.append("<TD align=right width='60%'>");
        
        sb.append("文档总数: " + totalRows + ", 共" + totalPages + "页, 第" + currentPage + "页, 转到 ");
        sb.append("<INPUT type=text size=5 name=page value=" + currentPage + " class='form'  style='width:30px;'>");
        sb.append("&nbsp;");
        sb.append("&nbsp;每页<INPUT type=text size=5 name=pageSize value=" + pageSize
            + " class='form' style='width:30px;'>");
        sb.append("<INPUT name=totalPages type=hidden value='" + totalPages + "'>");
        sb.append("&nbsp;&nbsp;");
        sb.append("<INPUT name=totalRows type=hidden value='" + totalRows + "'>");
        sb.append("&nbsp;&nbsp;");
        sb.append("<INPUT onclick='submit_pagedForm()' type=button value='GO' class='button2'>");
        sb.append("&nbsp;&nbsp;");
        sb.append("</TD>");
        sb.append("</TR>");
        sb.append("</TBODY>");
        sb.append("</TABLE>");
        
        sb.append("<script type=\"text/javascript\">\n");
        sb.append("function submit_pagedForm(){\n");
        sb.append(" var page = document.forms[0].page.value;\n");
        sb.append("var reg=eval('/^[0-9]+$/');\n");
        sb.append("var flag = reg.test(page);\n");
        sb.append("if(!flag){\n" + "alert('跳转页必须是数字');\n" + "return false;}\n");
        sb.append("var pageSize = document.forms[0].pageSize.value;\n");
        sb.append("var reg=eval('/^[0-9]+$/');\n");
        sb.append("var flag = reg.test(pageSize);\n");
        sb.append("if(!flag){alert('每页显示数必须是数字');\n" + "return false;}\n");
        sb.append("var totalPages = 0;\n");
        sb.append("totalPages = document.forms[0].totalPages.value;\n");
        sb.append("if (parseInt(page) > parseInt(totalPages) ) {document.pagedForm.page.value =totalPages;}\n");
        sb.append("if(parseInt(page) <1) {document.pagedForm.page.value =1;}\n");
        sb.append("document.forms[0].submit();\n");
        sb.append("}\n");
        sb.append("function search_pagedForm(page) {\n");
        sb.append("document.pagedForm.page.value = page;\n");
        sb.append("document.pagedForm.target='_self';\n");
        sb.append("document.pagedForm.submit();\n");
        sb.append("}\n");
        sb.append("</script>\n");
        pagedView = sb.toString();
    }
    
    public void setPagedView1()
    {
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("<TABLE width='100%' class='content9'>");
        sb.append("<TBODY>");
        sb.append("<TR>");
        sb.append("<TD align=left width='20%'>");
        sb.append("&nbsp;&nbsp;");
        if (hasPreviousPage)
        {
            sb.append("<a href='#' onclick='search_pagedForm(1);return false;'><IMG title='第一页' src='../images/0.gif' border=0></a>");
            sb.append("&nbsp;&nbsp;");
            sb.append("<a href='#' onclick='search_pagedForm(" + (currentPage - 1)
                + ");return false;'><IMG title='上一页' src='../images/1.gif' border=0></a>");
        }
        else
        {
            sb.append("<IMG title='第一页' src='../images/0.gif' border=0>");
            sb.append("&nbsp;&nbsp;");
            sb.append("<IMG title='上一页' src='../images/1.gif' border=0>");
        }
        sb.append("&nbsp;&nbsp;");
        if (hasNextPage)
        {
            sb.append("<a href='#' onclick='search_pagedForm(" + (currentPage + 1)
                + ");return false;'><IMG title='下一页' src='../images/2.gif' border=0></a>");
            sb.append("&nbsp;&nbsp;");
            sb.append("<a href='#' onclick='search_pagedForm(" + totalPages
                + ");return false;'><IMG title='最末页' src='../images/3.gif' border=0></a>");
        }
        else
        {
            sb.append("<IMG title='下一页' src='../images/2.gif' border=0>");
            sb.append("&nbsp;&nbsp;");
            sb.append("<IMG title='最末页' src='../images/3.gif' border=0>");
        }
        sb.append("</TD>");
        sb.append("<TD align=right width='80%'>");
        sb.append("文档总数: " + totalRows + ", 共" + totalPages + "页, 第" + currentPage + "页, 转到 ");
        sb.append("<INPUT type=text size=5 name=page value=" + currentPage + " class='form'  style='width:30px;'>");
        sb.append("&nbsp;");
        sb.append("&nbsp;每页<INPUT type=text size=5 name=pageSize value=" + pageSize
            + " class='form' style='width:30px;'>");
        sb.append("<INPUT name=totalPages type=hidden value='" + totalPages + "'>");
        sb.append("&nbsp;&nbsp;");
        sb.append("<INPUT name=totalRows type=hidden value='" + totalRows + "'>");
        sb.append("&nbsp;&nbsp;");
        sb.append("<INPUT onclick='submit_pagedForm()' type=button value=' GO ' class='button2'>");
        sb.append("&nbsp;&nbsp;");
        sb.append("</TD>");
        sb.append("</TR>");
        sb.append("</TBODY>");
        sb.append("</TABLE>");
        
        sb.append("<script type=\"text/javascript\">\n");
        sb.append("function submit_pagedForm(){\n");
        sb.append(" var page = document.forms[0].page.value;\n");
        sb.append("var reg=eval('/^[0-9]+$/');\n");
        sb.append("var flag = reg.test(page);\n");
        sb.append("if(!flag){\n" + "alert('跳转页必须是数字');\n" + "return false;}\n");
        sb.append("var pageSize = document.forms[0].pageSize.value;\n");
        sb.append("var reg=eval('/^[0-9]+$/');\n");
        sb.append("var flag = reg.test(pageSize);\n");
        sb.append("if(!flag){alert('每页显示数必须是数字');\n" + "return false;}\n");
        sb.append("var totalPages = 0;\n");
        sb.append("totalPages = document.forms[0].totalPages.value;\n");
        sb.append("if (parseInt(page) > parseInt(totalPages) || parseInt(page) <1) {page =1;}\n");
        sb.append("document.forms[0].submit();\n");
        sb.append("}\n");
        sb.append("function search_pagedForm(page) {\n");
        sb.append("document.pagedForm.page.value = page;\n");
        sb.append("document.pagedForm.target='_self';\n");
        sb.append("document.pagedForm.submit();\n");
        sb.append("}\n");
        sb.append("</script>\n");
        pagedView = sb.toString();
        
    }
    
    /**
     * 前台分页页面导航的显示
     * 
     * @return String
     */
    public void setPagedView1(String path)
    {
        
        StringBuffer sb = new StringBuffer();
        sb.append("&nbsp;");
        if (hasPreviousPage)
            sb.append("<a href=\"" + path
                + "page=1\"><img src=\"../images/houtui.gif\" border=0></a>&nbsp;&nbsp;<a href=\"" + path + "page="
                + (currentPage - 1) + "\"><img src=\"../images/houtui2.gif\" border=0></a>");
        else
            sb.append("<img src=\"../images/houtui.gif\" border=0>&nbsp;&nbsp;<img src=\"../images/houtui2.gif\" border=0>");
        sb.append("&nbsp;&nbsp;");
        if (hasNextPage)
            sb.append("<a href=\"" + path + "page=" + (currentPage + 1)
                + "\"><img src=\"../images/qianjin.gif\" border=0></a>&nbsp;&nbsp;<a href=\"" + path + "page="
                + totalPages + "\"><img src=\"../images/qianjin2.gif\" border=0></a>");
        else
            sb.append("<img src=\"../images/qianjin.gif\" border=0>&nbsp;&nbsp;<img src=\"../images/qianjin2.gif\" border=0>");
        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
        sb.append("当前页/总页数:");
        sb.append(currentPage);
        sb.append("/");
        sb.append(totalPages);
        sb.append("&nbsp;");
        
        sb.append("<script type=\"text/javascript\">\n");
        sb.append("function submit_pagedForm(){\n");
        sb.append(" var page = document.forms[0].page.value;\n");
        sb.append("var reg=eval('/^[0-9]+$/');\n");
        sb.append("var flag = reg.test(page);\n");
        sb.append("if(!flag){\n" + "alert('跳转页必须是数字');\n" + "return false;}\n");
        sb.append("var pageSize = document.forms[0].pageSize.value;\n");
        sb.append("var reg=eval('/^[0-9]+$/');\n");
        sb.append("var flag = reg.test(pageSize);\n");
        sb.append("if(!flag){alert('每页显示数必须是数字');\n" + "return false;}\n");
        sb.append("var totalPages = 0;\n");
        sb.append("totalPages = document.forms[0].totalPages.value;\n");
        sb.append("if (parseInt(page) > parseInt(totalPages) || parseInt(page) <1) {page =1;}\n");
        sb.append("document.forms[0].submit();\n");
        sb.append("}\n");
        sb.append("function search_pagedForm(page) {\n");
        sb.append("document.pagedForm.page.value = page;\n");
        sb.append("document.pagedForm.target='_self';\n");
        sb.append("document.pagedForm.submit();\n");
        sb.append("}\n");
        sb.append("</script>\n");
        pagedView = sb.toString();
    }
    
    // public void setPagedView(String path) {
    //
    // StringBuffer sb = new StringBuffer();
    //
    // sb.append("当前页/总页数:");
    // sb.append(currentPage);
    // sb.append("/");
    // sb.append(totalPages);
    // sb.append("&nbsp;");
    //
    // if (hasPreviousPage)
    // sb.append(
    // "<a href=\""
    // + path
    // + "page=1\">首页</a>&nbsp; <a href=\""
    // + path
    // + "page="
    // + (currentPage - 1)
    // + "\">上一页</a>");
    // else
    // sb.append("首页 上一页");
    // sb.append("/");
    //
    // if (hasNextPage)
    // sb.append(
    // "<a href=\""
    // + path
    // + "page="
    // + (currentPage + 1)
    // + "\">下一页</a> &nbsp;<a href=\""
    // + path
    // + "page="
    // + totalPages
    // + "\">尾页</a>");
    // else
    // sb.append("下一页 尾页");
    //
    // pagedView = sb.toString();
    // }
    
    public String getPagedView()
    {
        return pagedView;
    }
    
    /**
     * @return
     */
    public int getCurrentPage()
    {
        return currentPage;
    }
    
    /**
     * @return
     */
    public boolean isHasNextPage()
    {
        return hasNextPage;
    }
    
    /**
     * @return
     */
    public boolean isHasPreviousPage()
    {
        return hasPreviousPage;
    }
    
    /**
     * @return
     */
    public int getPageEndRow()
    {
        return pageEndRow;
    }
    
    /**
     * @return
     */
    public int getPageSize()
    {
        return pageSize;
    }
    
    /**
     * @return
     */
    public int getPageStartRow()
    {
        return pageStartRow;
    }
    
    /**
     * @return
     */
    public int getTotalPages()
    {
        return totalPages;
    }
    
    /**
     * @return
     */
    public int getTotalRows()
    {
        return totalRows;
    }
    
    /**
     * @param i
     */
    public void setTotalPages(int i)
    {
        totalPages = i;
    }
    
    /**
     * @param i
     */
    public void setCurrentPage(int i)
    {
        currentPage = i;
    }
    
    /**
     * @param b
     */
    public void setHasNextPage(boolean b)
    {
        hasNextPage = b;
    }
    
    /**
     * @param b
     */
    public void setHasPreviousPage(boolean b)
    {
        hasPreviousPage = b;
    }
    
    /**
     * @param i
     */
    public void setPageEndRow(int i)
    {
        pageEndRow = i;
    }
    
    /**
     * @param i
     */
    public void setPageSize(int i)
    {
        pageSize = i;
    }
    
    /**
     * @param i
     */
    public void setPageStartRow(int i)
    {
        pageStartRow = i;
    }
    
    /**
     * @param i
     */
    public void setTotalRows(int i)
    {
        totalRows = i;
    }
    
    public boolean isPagination()
    {
        return pagination;
    }
    
    public void setPagination(boolean pagination)
    {
        this.pagination = pagination;
    }
    
    public String getAction()
    {
        return action;
    }
    
    public void setAction(String action)
    {
        this.action = action;
    }
    
    public List<?> getResultSet()
    {
        return resultSet;
    }
    
    public void setResultSet(List<?> resultSet)
    {
        this.resultSet = resultSet;
    }
}
