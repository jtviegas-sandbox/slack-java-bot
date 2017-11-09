package com.ibm.cloud.wms.eleanor.slackbot;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ibm.cloud.wms.eleanor.slackbot.listeners.MessagePostListener;
import com.ibm.cloud.wms.eleanor.slackbot.listeners.MessageUpdateListener;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackConnected;
import com.ullink.slack.simpleslackapi.events.SlackDisconnected;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

import jersey.repackaged.com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 
 * Bot that intermediates messages between
 * Eleanor API and Slack.
 * It stands for a general bot that is added to an
 * assorted channel in a Slack team.
 * 
 * @author joaovieg
 *
 */
public class Bot {
	
	private static final String LITERAL_BOT_TOKEN= "BOT_TOKEN", LITERAL_BOT_CHANNEL="BOT_CHANNEL", LITERAL_BOT_ID="BOT_ID";
	private final SlackSession session;
	private final ExecutorService executionPool;
	//private SlackChannel channel;
	
	private Bot() {
		
		executionPool = Executors.newFixedThreadPool(
				Runtime.getRuntime().availableProcessors(), 
				new ThreadFactoryBuilder().setDaemon(true).build());
		Map<String, String> env = System.getenv();
		
		if( null == env.get(LITERAL_BOT_TOKEN) )
			throw new RuntimeException("must have a BOT_TOKEN env variable");

		if( null ==  env.get(LITERAL_BOT_CHANNEL) )
			throw new RuntimeException("must have a BOT_CHANNEL env variable");
		
		if( null ==  env.get(LITERAL_BOT_ID) )
			throw new RuntimeException("must have a BOT_ID env variable");
		
		session = SlackSessionFactory.createWebSocketSlackSession(env.get(LITERAL_BOT_TOKEN));

		final MessagePostListener postListener = new MessagePostListener(env.get(LITERAL_BOT_ID), executionPool);
		final MessageUpdateListener updateListener = new MessageUpdateListener(env.get(LITERAL_BOT_ID), executionPool);
		
		session.addSlackDisconnectedListener((SlackDisconnected e, SlackSession s) -> {
			s.removeMessagePostedListener(postListener);
			s.removeMessageUpdatedListener(updateListener);
		});
		
		session.addSlackConnectedListener((SlackConnected e, SlackSession s) -> {
			session.addMessagePostedListener(postListener);
			session.addMessageUpdatedListener(updateListener);
		});
		
		try {
			session.connect();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		//make sure bot is a member of the channel.
		//channel = session.findChannelByName(env.get(LITERAL_BOT_CHANNEL)); 
		
	
		/*session.addMessagePostedListener(postListener);
		session.addMessageUpdatedListener(updateListener);*/

		

		Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		    	try {
					session.disconnect();
					executionPool.shutdown();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
		});
	}
	
	public static void main(String[] args) {
        new Bot();
    }
}
