package com.ibm.cloud.wms.eleanor.slackbot.adapter.eleanor2slack;

import java.util.function.Function;

import com.ibm.cloud.wms.eleanor.Message2;
import com.ullink.slack.simpleslackapi.SlackPreparedMessage;

public class EleanorMsg2SlackMsg implements Function<Message2, SlackPreparedMessage>{
	
	@Override
	public SlackPreparedMessage apply(Message2 t) {
		SlackPreparedMessage result = null;
		
		result = Eleanor2SlackMsgAdapterFactory.getAdapter(t).get();
		
		return result;
	}

}
