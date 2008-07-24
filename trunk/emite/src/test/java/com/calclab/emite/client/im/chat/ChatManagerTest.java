package com.calclab.emite.client.im.chat;

import static com.calclab.emite.client.xmpp.stanzas.XmppURI.uri;
import static com.calclab.emite.testing.MockSlot.verifyCalled;
import static com.calclab.emite.testing.MockSlot.verifyCalledWith;
import static com.calclab.emite.testing.MockSlot.verifyCalledWithSame;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.calclab.emite.client.im.chat.Chat.Status;
import com.calclab.emite.client.xmpp.stanzas.Message;
import com.calclab.emite.testing.MockSlot;

public class ChatManagerTest extends AbstractChatManagerTest {

    @Test
    public void managerShouldCreateOneChatForSameResource() {
	final MockSlot<Chat> listener = addOnChatCreatedSlot();
	session.receives(new Message(uri("source@domain/resource1"), MYSELF, "message 1"));
	session.receives(new Message(uri("source@domain/resource1"), MYSELF, "message 2"));
	verifyCalled(listener, 1);
    }

    @Test
    public void oneToOneChatsAreAlwaysReadyWhenCreated() {
	final Chat chat = manager.openChat(uri("other@domain/resource"), null, null);
	assertSame(Chat.Status.ready, chat.getState());
    }

    @Test
    public void shouldBlockChatWhenClosingIt() {
	final Chat chat = manager.openChat(uri("other@domain/resource"), null, null);
	manager.close(chat);
	assertSame(Chat.Status.locked, chat.getState());
    }

    @Test
    public void shouldCloseChatWhenLoggedOut() {
	final Chat chat = manager.openChat(uri("name@domain/resouce"), null, null);
	final MockSlot<Status> listener = new MockSlot<Status>();
	chat.onStateChanged(listener);
	session.logout();
	verifyCalledWith(listener, Status.locked);
    }

    @Test
    public void shouldReuseChatIfNotResouceSpecified() {
	final MockSlot<Chat> listener = addOnChatCreatedSlot();
	session.receives(new Message(uri("source@domain"), MYSELF, "message 1"));
	session.receives(new Message(uri("source@domain/resource1"), MYSELF, "message 2"));
	verifyCalled(listener, 1);
    }

    @Test
    public void shouldSignalIncommingMessages() {
	final Chat chat = manager.openChat(uri("someone@domain"), null, null);
	final MockSlot<Message> slot = new MockSlot<Message>();
	chat.onMessageReceived(slot);
	session.receives("<message type='chat' id='purplee8b92642' to='user@domain' "
		+ "from='someone@domain'><x xmlns='jabber:x:event'/><active"
		+ "xmlns='http://jabber.org/protocol/chatstates'/></message>");
	verifyCalled(slot);
    }

    @Test
    public void shouldUseSameRoomWhenAnswering() {
	final MockSlot<Chat> listener = addOnChatCreatedSlot();
	final Chat chat = manager.openChat(uri("someone@domain"), null, null);
	verifyCalledWithSame(listener, chat);
	session.receives(new Message(uri("someone@domain/resource"), MYSELF, "answer").Thread(chat.getThread()));
	verifyCalled(listener, 1);
    }

    @Override
    protected ChatManagerDefault createChatManager() {
	final ChatManagerDefault chatManagerDefault = new ChatManagerDefault(session);
	return chatManagerDefault;
    }

    private MockSlot<Chat> addOnChatCreatedSlot() {
	final MockSlot<Chat> listener = new MockSlot<Chat>();
	manager.onChatCreated(listener);
	return listener;
    }
}
