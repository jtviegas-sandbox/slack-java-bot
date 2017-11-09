package com.ibm.cloud.wms.eleanor.slackbot.adapter.eleanor2slack;

import java.util.function.Supplier;

import com.ibm.cloud.wms.eleanor.Message2;
import com.ullink.slack.simpleslackapi.SlackPreparedMessage;

class TextDialogMsg implements Supplier<SlackPreparedMessage> {

	private final Message2 msg;

	TextDialogMsg(Message2 msg) {
		this.msg = msg;
	}

	@Override
	public SlackPreparedMessage get() {
		SlackPreparedMessage result = null;
		SlackPreparedMessage.Builder builder = new SlackPreparedMessage.Builder();
		builder.withMessage(msg.getAttribute("text").get(0));
		result = builder.build();
		return result;
	}

}
