package com.calclab.emite.xep.muc.client;

import static com.calclab.emite.core.client.xmpp.stanzas.XmppURI.uri;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.Collection;

import org.junit.Test;

import com.calclab.emite.core.client.xmpp.stanzas.IQ;
import com.calclab.emite.core.client.xmpp.stanzas.Message;
import com.calclab.emite.core.client.xmpp.stanzas.IQ.Type;
import com.calclab.emite.im.client.chat.AbstractChatManagerTest;
import com.calclab.emite.im.client.chat.Chat;
import com.calclab.emite.im.client.chat.ChatManagerImpl;
import com.calclab.emite.xep.muc.client.Occupant.Affiliation;
import com.calclab.emite.xep.muc.client.Occupant.Role;
import com.calclab.suco.testing.listener.MockListener;

public class MUCRoomManagerTest extends AbstractChatManagerTest {

    @Test
    public void shouldAcceptRoomPresenceWithAvatar() {
	final Room room = (Room) manager.openChat(uri("room1@domain/nick"), null, null);
	session.receives("<presence to='user@domain/resource' from='room1@domain/otherUser2'>"
		+ "<priority>0</priority>" + "<x xmlns='http://jabber.org/protocol/muc#user'>"
		+ "<item jid='otheruserjid@domain/otherresoruce' affiliation='none' " + "role='participant'/></x>"
		+ "<x xmlns='vcard-temp:x:update'><photo>af70fe6519d6a27a910c427c3bc551dcd36073e7</photo></x>"
		+ "</presence>");
	assertEquals(1, room.getOccupantsCount());
	final Occupant occupant = room.findOccupant(uri("room1@domain/otherUser2"));
	assertNotNull(occupant);
	assertEquals(Affiliation.none, occupant.getAffiliation());
	assertEquals(Role.participant, occupant.getRole());
    }

    @Test
    public void shouldCreateInstantRoomIfNeeded() {
	manager.openChat(uri("newroomtest1@rooms.localhost/nick"), null, null);
	session.receives("<presence from='newroomtest1@rooms.localhost/nick' to='user@localhost/resource' >"
		+ "<priority>5</priority>" + "<x xmlns='http://jabber.org/protocol/muc#user'>"
		+ "<item affiliation='owner' role='moderator' jid='vjrj@localhost/Psi' />" + "<status code='201' />"
		+ "</x>" + "</presence>");
	session.verifyIQSent(new IQ(Type.set));
    }

    @Test
    public void shouldFireChatMessages() {
	final Chat chat = manager.openChat(uri("room@rooms.domain/user"), null, null);
	final MockListener<Message> listener = new MockListener<Message>();
	chat.onMessageReceived(listener);
	session.receives("<message from='room@rooms.domain/other' to='user@domain/resource' "
		+ "type='groupchat'><body>the message body</body></message>");

	MockListener.verifyCalled(listener, 1);
    }

    @Test
    public void shouldGiveSameRoomsWithSameURIS() {
	final Room room1 = (Room) manager.openChat(uri("room@domain/nick"), null, null);
	final Room room2 = (Room) manager.openChat(uri("room@domain/nick"), null, null);
	assertSame(room1, room2);
    }

    @Test
    public void shouldIgnoreLetterCaseInURIS() {
	final Room room = (Room) manager.openChat(uri("ROOM@domain/nick"), null, null);
	final MockListener<Collection<Occupant>> listener = new MockListener<Collection<Occupant>>();
	room.onOccupantsChanged(listener);
	session.receives("<presence to='user@domain/resource' xmlns='jabber:client' from='ROom@domain/otherUser'>"
		+ "<x xmlns='http://jabber.org/protocol/muc#user'>"
		+ "<item role='moderator' affiliation='owner' /></x></presence>");
	MockListener.verifyCalled(listener, 1);
    }

    @Test
    public void shouldUpdateRoomPresence() {
	final Room room = (Room) manager.openChat(uri("room1@domain/nick"), null, null);

	session.receives("<presence to='user@domain/resource' xmlns='jabber:client' from='room1@domain/otherUser'>"
		+ "<x xmlns='http://jabber.org/protocol/muc#user'>"
		+ "<item role='moderator' affiliation='owner' /></x></presence>");
	assertEquals(1, room.getOccupantsCount());
	Occupant user = room.findOccupant(uri("room1@domain/otherUser"));
	assertNotNull(user);
	assertEquals(Affiliation.owner, user.getAffiliation());
	assertEquals(Role.moderator, user.getRole());

	session.receives("<presence to='user@domain/resource' xmlns='jabber:client' from='room1@domain/otherUser'>"
		+ "<x xmlns='http://jabber.org/protocol/muc#user'>"
		+ "<item role='participant' affiliation='member' /></x></presence>");
	assertEquals(1, room.getOccupantsCount());
	user = room.findOccupant(uri("room1@domain/otherUser"));
	assertNotNull(user);
	assertEquals(Affiliation.member, user.getAffiliation());
	assertEquals(Role.participant, user.getRole());

	session.receives("<presence to='user@domain/res1' type='unavailable' "
		+ "xmlns='jabber:client' from='room1@domain/otherUser'>"
		+ "<status>custom message</status><x xmlns='http://jabber.org/protocol/muc#user'>"
		+ "<item role='none' affiliation='member' /></x></presence>");
	assertEquals(0, room.getOccupantsCount());

    }

    @Override
    protected ChatManagerImpl createChatManager() {
	final RoomManagerImpl roomManager = new RoomManagerImpl(session);
	return roomManager;
    }
}