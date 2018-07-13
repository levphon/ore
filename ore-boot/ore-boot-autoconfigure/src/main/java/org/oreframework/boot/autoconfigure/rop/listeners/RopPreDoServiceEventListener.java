package org.oreframework.boot.autoconfigure.rop.listeners;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rop.event.PreDoServiceEvent;
import com.rop.event.RopEventListener;

public class RopPreDoServiceEventListener implements RopEventListener<PreDoServiceEvent> {

	private final static Logger logger = LoggerFactory.getLogger(RopPreDoServiceEventListener.class);

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public void onRopEvent(PreDoServiceEvent ropEvent) {
		try {
				String requestId = String.valueOf(UUID.randomUUID());
				ropEvent.getRopRequestContext().setAttribute("requestId", requestId);
				String str = ropEvent.getRopRequestContext().getAllParams().toString();
				logger.info("Rop Request params:" + str);
		} catch (Exception e) {
			logger.error("onRopEvent", e);
		}
		
	}

}
