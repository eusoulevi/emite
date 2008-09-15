package com.calclab.emite.im.client.chat;

import org.junit.Test;

import com.calclab.emite.core.client.xmpp.stanzas.Message;
import com.calclab.emite.im.client.chat.AbstractChat;
import com.calclab.emite.im.client.chat.Chat.Status;
import com.calclab.emite.testing.MockedSession;
import com.calclab.suco.testing.listener.MockListener;

public abstract class AbstractChatTest {
    protected final MockedSession session;

    public AbstractChatTest() {
	session = new MockedSession();
    }

    public abstract AbstractChat getChat();

    @Test
    public void shouldInterceptIncomingMessages() {
	final AbstractChat chat = getChat();
	final MockListener<Message> interceptor = new MockListener<Message>();
	chat.onBeforeReceive(interceptor);
	final Message message = new Message("body");
	chat.receive(message);
	MockListener.verifyCalledWithSame(interceptor, message);
    }

    @Test
    public void shouldInterceptOutcomingMessages() {
	final AbstractChat chat = getChat();
	final MockListener<Message> interceptor = new MockListener<Message>();
	chat.onBeforeSend(interceptor);
	final Message message = new Message("body");
	chat.send(message);
	MockListener.verifyCalledWithSame(interceptor, message);
    }

    @Test
    public void shouldNotSendMessagesWhenStatusIsNotReady() {
	final AbstractChat chat = getChat();
	chat.setStatus(Status.locked);
	chat.send(new Message("a message"));
	session.verifyNotSent("<message />");
    }

    @Test
    public void shouldSetNullData() {
	getChat().setData(null, null);
    }
}