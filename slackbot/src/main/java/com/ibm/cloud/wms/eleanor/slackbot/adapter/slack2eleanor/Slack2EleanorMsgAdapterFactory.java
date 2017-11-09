package com.ibm.cloud.wms.eleanor.slackbot.adapter.slack2eleanor;

import java.util.function.Function;

import com.ibm.cloud.wms.eleanor.Message2;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;

class Slack2EleanorMsgAdapterFactory {

	public static Function<SlackMessagePosted, Message2> getAdapter(final String botLabel, final Message2 lastMessage){
		Function<SlackMessagePosted, Message2> result = null;
		switch(lastMessage.getType()){
			case SELECT:
				result = new SelectDialogMsg(botLabel, lastMessage);
				break;
			default:
				result = new TextDialogMsg(botLabel, lastMessage);
		}
		return result;
	}
	
	public static Function<SlackMessagePosted, Message2> getDefaultAdapter(final String botLabel, final String id){
		return new DefaultDialogMsg(botLabel, id);
	}

}
