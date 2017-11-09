package com.ibm.cloud.wms.eleanor.slackbot.adapter.eleanor2slack;

import java.util.function.Supplier;

import com.ibm.cloud.wms.eleanor.Message2;
import com.ullink.slack.simpleslackapi.SlackPreparedMessage;

class Eleanor2SlackMsgAdapterFactory {

	public static Supplier<SlackPreparedMessage> getAdapter(Message2 eleanorMsg) {
		Supplier<SlackPreparedMessage> result = null;

		switch (eleanorMsg.getType()) {
			case SELECT:
				result = new SelectDialogMsg(eleanorMsg);
				break;
			default:
				result = new TextDialogMsg(eleanorMsg);
		}

		return result;
	}

}
