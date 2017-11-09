package com.ibm.cloud.wms.eleanor.slackbot.adapter.slack2eleanor;

import java.util.function.Function;

import com.ibm.cloud.wms.eleanor.Message2;
import com.ibm.cloud.wms.eleanor.Message2.TYPE;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;

class SelectDialogMsg implements Function<SlackMessagePosted, Message2> {
	
	private final String botLabel;
	private final Message2 lastMessage;
	
	SelectDialogMsg(final String botLabel, final Message2 lastMessage) {
		this.botLabel = botLabel;
		this.lastMessage = lastMessage;
	}

	@Override
	public Message2 apply(final SlackMessagePosted t) {
		Message2 result = null;
		
		String text  = t.getMessageContent().replaceFirst(botLabel, "").trim();
		
		if(lastMessage.getAttribute("select").contains(text)){
			result = Message2.create(TYPE.SELECT);
			result.addAttribute("select", text);
		}
		else {
			result = Message2.create(TYPE.TEXT);
			result.addAttribute("text", text);
		}
		
		result.addAttribute("channelId", t.getChannel().getId());
		result.addAttribute("userId", t.getSender().getId());
		result.addAttribute("conversation", "AutomatedTester2");
		result.addAttribute("threshold", "0.4");
		result.setInteractionId(lastMessage.getInteractionId());
		return result;
	}

}
