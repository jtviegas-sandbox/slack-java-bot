package com.ibm.cloud.wms.eleanor.slackbot;

import java.util.Map;
import java.util.function.Consumer;

import com.ibm.cloud.wms.eleanor.Message2;
import com.ibm.cloud.wms.eleanor.api.Api;
import com.ibm.cloud.wms.eleanor.slackbot.adapter.eleanor2slack.EleanorMsg2SlackMsg;
import com.ibm.cloud.wms.eleanor.slackbot.adapter.slack2eleanor.SlackMsg2EleanorMsg;
import com.ullink.slack.simpleslackapi.SlackPreparedMessage;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;

/**
 * Should receive the slack messages, hand it over to Eleanor
 * and then receive then back and hand it to Slack back again.
 * In the mean time should translate the message formats accordingly.
 * 
 * @author joaovieg
 *
 */
public class SlackMsgConsumer implements Consumer<SlackMessagePosted> {
	
	private final Api api;
	private final String botLabel;
	private final SlackSession session;
	private final Map<String, Message2> dialogState;
	
	public SlackMsgConsumer(Api api, String botLabel, SlackSession session, Map<String, Message2> dialogState) {
		this.api = api;
		this.botLabel = botLabel;
		this.session = session;
		this.dialogState = dialogState;
	}

	@Override
	public void accept(SlackMessagePosted slackInputMsg) {
		
		Message2 eleanorInputMsg = new SlackMsg2EleanorMsg(dialogState).apply(slackInputMsg, botLabel);
		Message2 eleanorResponseMsg = api.onMessagePost(eleanorInputMsg);
		dialogState.put(eleanorResponseMsg.getInteractionId(), eleanorResponseMsg);
		SlackPreparedMessage slackResponseMsg = new EleanorMsg2SlackMsg().apply(eleanorResponseMsg);
		
		session.sendMessage(slackInputMsg.getChannel(), slackResponseMsg);
	}

}
