package org.oreframework.boot.autoconfigure.dubbo.listener;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @author huangzz
 * 2017年3月18日
 */
public class DubboHolderListener implements ApplicationListener<ApplicationEvent>
{
    private static Thread holdThread;
    
    private static Boolean running = Boolean.FALSE;
    
    @Override
    public void onApplicationEvent(ApplicationEvent event)
    {
        if (event instanceof ApplicationPreparedEvent)
        {
            if (running == Boolean.FALSE)
                running = Boolean.TRUE;
            if (holdThread == null)
            {
                holdThread = new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        System.out.println(Thread.currentThread().getName());
                        while (running && !Thread.currentThread().isInterrupted())
                        {
                            try
                            {
                                Thread.sleep(2000);
                            }
                            catch (InterruptedException e)
                            {
                            }
                        }
                    }
                }, "Dubbo-Holder");
                holdThread.setDaemon(false);
                holdThread.start();
            }
        }
        if (event instanceof ContextClosedEvent)
        {
            running = Boolean.FALSE;
            if (null != holdThread)
            {
                holdThread.interrupt();
                holdThread = null;
            }
        }
    }
}
