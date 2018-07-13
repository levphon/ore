
package org.oreframework.boot.seed.biz;

import java.util.Map;

import org.oreframework.boot.seed.framework.config.StaticProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Scope("prototype")
@Controller
class IndexController
{
    @Autowired
    private StaticProperty staticProperty;
    
    @RequestMapping("/")
    public String index(Map<String, Object> model)
    {
        model.put("cdnPath", staticProperty.getResource());
        return "cdn";
    }
}