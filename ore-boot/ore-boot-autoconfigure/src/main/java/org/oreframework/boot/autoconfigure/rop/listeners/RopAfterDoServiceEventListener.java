package org.oreframework.boot.autoconfigure.rop.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rop.RopRequest;
import com.rop.RopRequestContext;
import com.rop.event.AfterDoServiceEvent;
import com.rop.event.RopEventListener;
import com.rop.marshaller.MessageMarshallerUtils;

public class RopAfterDoServiceEventListener implements RopEventListener<AfterDoServiceEvent> {
	private final static Logger logger = LoggerFactory.getLogger(RopAfterDoServiceEventListener.class);

	@Override
	public void onRopEvent(AfterDoServiceEvent ropEvent) {
		try {
			RopRequestContext gopRequestContext = ropEvent.getRopRequestContext();
			if (gopRequestContext != null && gopRequestContext.getRopRequest() != null) {
				RopRequest gopRequest = gopRequestContext.getRopRequest();
				String message = MessageMarshallerUtils.asUrlString(gopRequest);
				logger.info("message(" + ropEvent.getServiceEndTime() + ")" + message);
			}
		} catch (Exception e) {
			logger.error("onRopEvent", e);
		}
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
