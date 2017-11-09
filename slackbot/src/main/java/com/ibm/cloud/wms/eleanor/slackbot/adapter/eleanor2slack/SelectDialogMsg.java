package com.ibm.cloud.wms.eleanor.slackbot.adapter.eleanor2slack;

import java.util.function.Supplier;

import com.ibm.cloud.wms.eleanor.Message2;
import com.ullink.slack.simpleslackapi.SlackPreparedMessage;

class SelectDialogMsg implements Supplier<SlackPreparedMessage> {
	
	private final Message2 msg;

	SelectDialogMsg(Message2 msg) {
		this.msg = msg;
	}

	@Override
	public SlackPreparedMessage get() {
		SlackPreparedMessage result = null;
		
		SlackPreparedMessage.Builder builder = new SlackPreparedMessage.Builder();
		/* ### one day we'll do this ###
		builder.withMessage(msg.getAttribute("text").get(0));
		SlackAttachment att = new SlackAttachment();
		att.setTitle("please choose");
		att.setColor("#3AA3E3");
		for (String s: msg.getAttributes().get("select"))
			att.addAction(new SlackAction(s, s, "button", s));
		builder.addAttachment(att);
		*/
		StringBuffer buff = new StringBuffer(String.format("Please select: %s", System.getProperty("line.separator")));
		for(String s: msg.getAttribute("select"))
			buff.append(String.format("- %s%s", s, System.getProperty("line.separator")));
		builder.withMessage(buff.toString());
		
		result = builder.build();
		return result;
	}

}
