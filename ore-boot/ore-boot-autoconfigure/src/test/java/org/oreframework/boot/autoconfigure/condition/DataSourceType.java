package org.oreframework.boot.autoconfigure.condition;

/**
 * @author huangzz
 * 2017年3月18日
 */
public enum DataSourceType
{
    SINGLE("single"), MULTI("multi"), PRIMARY("primary"), SECONDARY("secondary");
    
    private final String text;
    
    private DataSourceType(final String text)
    {
        this.text = text;
    }
    
    @Override
    public String toString()
    {
        return text;
    }
    
}
