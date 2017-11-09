package com.ibm.cloud.wms.eleanor.slackbot.listeners;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import com.ibm.cloud.wms.eleanor.Message2;
import com.ibm.cloud.wms.eleanor.api.Api;
import com.ibm.cloud.wms.eleanor.slackbot.SlackMsgConsumer;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;

public class MessagePostListener implements SlackMessagePostedListener {
	
	private final String botId, botLabel;
	private final ExecutorService executionPool;
	private final Api api = new Api();
	private final Map<String, Message2> dialogState;
	
	public MessagePostListener(String botId, ExecutorService executionPool){
		this.botId = botId;
		this.botLabel = String.format("<@%s>", botId);
		this.executionPool = executionPool;
		dialogState = new ConcurrentHashMap<String, Message2>();
	}
	
	@Override
	public void onEvent(final SlackMessagePosted msg, final SlackSession session) {
		
		if(!isDirectMessage(msg))
			return;
		
		executionPool.submit( () ->  new SlackMsgConsumer(api, botLabel, session, dialogState).accept(msg));

	}
	
	private boolean isDirectMessage(final SlackMessagePosted msg){
		boolean result = false;
		
		if(!msg.getSender().getId().equals(this.botId) && msg.getMessageContent().startsWith(this.botLabel))
			result = true;
		
		return result;
	}

}
