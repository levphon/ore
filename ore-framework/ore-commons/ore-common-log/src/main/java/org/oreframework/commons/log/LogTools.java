package org.oreframework.commons.log;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huangzz
 * @version [1.0.0, 2015-3-23]
 */
public class LogTools
{
    private Logger logger = LoggerFactory.getLogger(LogTools.class);
    
    public LogTools(Class<?> clssName)
    {
        logger = LoggerFactory.getLogger(clssName);
    }
    
    public void startLogRecord()
    {
        if (logger.isInfoEnabled())
        {
            this.redcord("start");
        }
    }
    
    public void endLogRecord()
    {
        if (logger.isInfoEnabled())
        {
            this.redcord("end");
        }
    }
    
    public void redcordParams(Map<String, String> params)
    {
        String concatStr = "";
        Set<String> set = params.keySet();
        for (String key : set)
        {
            concatStr = concatStr + key + "=\"" + params.get(key) + "\"\n";
        }
        logger.info("input params value:");
        logger.info(concatStr);
    }
    
    public void redcord(String content)
    {
        String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
        String className = Thread.currentThread().getStackTrace()[3].getClassName();
        getCaller(content, className, methodName);
    }
    
    public void getCaller(String content, String className, String methodName)
    {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        for (StackTraceElement ste : stack)
        {
            if (ste.getClassName() == className && ste.getMethodName() == methodName)
            {
                logger.info(ste.getClassName() + "." + ste.getMethodName() + " " + content);
            }
        }
    }
    
    public Logger getLogger()
    {
        return logger;
    }
    
}
