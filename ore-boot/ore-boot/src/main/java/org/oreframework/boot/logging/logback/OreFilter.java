package org.oreframework.boot.logging.logback;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * @author huangzz
 * 2017年3月18日
 */
@SuppressWarnings("rawtypes")
public class OreFilter extends Filter
{
    
    @Override
    public FilterReply decide(Object eventObject)
    {
        LoggingEvent event = (LoggingEvent)eventObject;
        
        if (event.getMessage().contains("oreframework"))
        {
            return FilterReply.ACCEPT;
        }
        else
        {
            return FilterReply.NEUTRAL;
        }
    }
    
}
