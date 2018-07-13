package org.oreframework.web.ui;

public class ResultEntity<T>
{
    private String returnCode = ResultCode.SUCCESS;
    
    private String message;
    
    private T data;
    
    public String getReturnCode()
    {
        return returnCode;
    }
    
    public void setReturnCode(String returnCode)
    {
        this.returnCode = returnCode;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    
    public T getData()
    {
        return data;
    }
    
    public void setData(T data)
    {
        this.data = data;
    }
    
}
