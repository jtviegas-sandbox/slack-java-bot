package com.ibm.cloud.wms.eleanor.slackbot.adapter.slack2eleanor;

import java.util.Map;
import java.util.function.BiFunction;

import com.ibm.cloud.wms.eleanor.Message2;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;

public class SlackMsg2EleanorMsg implements BiFunction<SlackMessagePosted, String, Message2>{
	
	private final Map<String, Message2> dialogState;
	
	public SlackMsg2EleanorMsg(Map<String, Message2> dialogState){
		this.dialogState = dialogState;
	}
	
	@Override
	public Message2 apply(SlackMessagePosted t, String botLabel) {
		Message2 result = null;
		String id = computeMsgId(t);
		// do we have an ongoing dialog?
		Message2 lastMessage = dialogState.get(id);
		if(null != lastMessage)
			result = Slack2EleanorMsgAdapterFactory.getAdapter(botLabel, lastMessage).apply(t);
		else
			result = Slack2EleanorMsgAdapterFactory.getDefaultAdapter(botLabel, id).apply(t);
		
		return result;
	}
	
	private String computeMsgId(SlackMessagePosted t){
		return String.format("%s-%s", t.getChannel().getId(), t.getSender().getId());
	}

}
