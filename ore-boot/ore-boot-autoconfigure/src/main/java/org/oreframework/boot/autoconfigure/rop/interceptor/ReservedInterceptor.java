package org.oreframework.boot.autoconfigure.rop.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rop.AbstractInterceptor;
import com.rop.RopRequest;
import com.rop.RopRequestContext;
import com.rop.marshaller.MessageMarshallerUtils;


public class ReservedInterceptor extends AbstractInterceptor {
	private final static Logger logger = LoggerFactory.getLogger(ReservedInterceptor.class);

	@Override
	public void beforeService(RopRequestContext ropRequestContext) {
		super.beforeService(ropRequestContext);
		if (ropRequestContext != null && ropRequestContext.getRopRequest() != null) {
			RopRequest gopRequest = ropRequestContext.getRopRequest();
			String message = MessageMarshallerUtils.asUrlString(gopRequest);
			logger.info("beforeService (" + ropRequestContext.getRequestId() + ")" + message);
		}
	}

	@Override
	public void beforeResponse(RopRequestContext ropRequestContext) {
		super.beforeResponse(ropRequestContext);
		if (ropRequestContext != null && ropRequestContext.getRopResponse() != null) {
			Object response = ropRequestContext.getRopResponse();
			//String message = MessageMarshallerUtils.getMessage(response, MessageFormat.json);
			logger.info("beforeResponse (" + ropRequestContext.getRequestId() + ")" + response);
		}
	}
	
	
}
