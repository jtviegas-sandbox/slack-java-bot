package com.ibm.cloud.wms.eleanor.slackbot.adapter.slack2eleanor;

import java.util.function.Function;

import com.ibm.cloud.wms.eleanor.Message2;
import com.ibm.cloud.wms.eleanor.Message2.TYPE;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;

class DefaultDialogMsg implements Function<SlackMessagePosted, Message2> {
	
	private final String botLabel;
	private final String id;
	
	DefaultDialogMsg(final String botLabel, final String id) {
		this.botLabel = botLabel;
		this.id = id;
	}

	@Override
	public Message2 apply(final SlackMessagePosted t) {
		Message2 result = null;
		
		String text  = t.getMessageContent().replaceFirst(botLabel, "").trim();
		result = Message2.create(TYPE.TEXT);
		result.addAttribute("text", text);
		result.addAttribute("channelId", t.getChannel().getId());
		result.addAttribute("userId", t.getSender().getId());
		result.addAttribute("conversation", "AutomatedTester2");
		result.addAttribute("threshold", "0.4");
		result.setInteractionId(id);
		return result;
	}

}
