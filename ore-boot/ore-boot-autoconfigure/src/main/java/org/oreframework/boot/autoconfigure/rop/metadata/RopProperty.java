package org.oreframework.boot.autoconfigure.rop.metadata;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author huangzz 2017年3月18日
 */
@ConfigurationProperties("ore.rop")
public class RopProperty
{
    private RopAnnotationDriven annotationDriven = new RopAnnotationDriven();
    
    private RopSysParams sysparams = new RopSysParams();
    
    public RopAnnotationDriven getAnnotationDriven()
    {
        return annotationDriven;
    }
    public void setAnnotationDriven(RopAnnotationDriven annotationDriven)
    {
        this.annotationDriven = annotationDriven;
    }
    public RopSysParams getSysparams()
    {
        return sysparams;
    }
    public void setSysparams(RopSysParams sysparams)
    {
        this.sysparams = sysparams;
    }
   
    
}
