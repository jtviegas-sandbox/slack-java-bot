package com.ibm.cloud.wms.eleanor.slackbot.listeners;

import java.util.concurrent.ExecutorService;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessageUpdated;
import com.ullink.slack.simpleslackapi.listeners.SlackMessageUpdatedListener;

public class MessageUpdateListener implements SlackMessageUpdatedListener {

	private String botId;
	
	public MessageUpdateListener(String botId, ExecutorService executionPool){
		this.botId = botId;
	}
	
	@Override
	public void onEvent(SlackMessageUpdated msg, SlackSession session) {
		System.out.println(msg.getNewMessage());
	}

}
