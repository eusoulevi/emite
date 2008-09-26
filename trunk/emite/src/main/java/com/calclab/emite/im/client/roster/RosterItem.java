/*
 *
 * ((e)) emite: A pure gwt (Google Web Toolkit) xmpp (jabber) library
 *
 * (c) 2008 The emite development team (see CREDITS for details)
 * This file is part of emite.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.calclab.emite.im.client.roster;

import static com.calclab.emite.core.client.xmpp.stanzas.XmppURI.uri;

import java.util.ArrayList;
import java.util.List;

import com.calclab.emite.core.client.packet.IPacket;
import com.calclab.emite.core.client.packet.MatcherFactory;
import com.calclab.emite.core.client.packet.PacketMatcher;
import com.calclab.emite.core.client.xmpp.stanzas.Presence;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.core.client.xmpp.stanzas.Presence.Show;
import com.calclab.emite.core.client.xmpp.stanzas.Presence.Type;

/**
 * Represents a item in the Roster
 */
public class RosterItem {

    private static final PacketMatcher GROUP_FILTER = MatcherFactory.byName("group");

    static RosterItem parse(final IPacket packet) {
	final RosterItem item = new RosterItem(uri(packet.getAttribute("jid")), parseSubscriptionState(packet
		.getAttribute("subscription")), packet.getAttribute("name"), parseAsk(packet.getAttribute("ask")));
	final List<? extends IPacket> groups = packet.getChildren(GROUP_FILTER);

	String groupName;
	for (final IPacket group : groups) {
	    groupName = group.getText();
	    item.addGroup(groupName);
	}
	return item;
    }

    private static Type parseAsk(final String ask) {
	Type type;
	try {
	    type = Presence.Type.valueOf(ask);
	} catch (final Exception e) {
	    type = null;
	}
	return type;
    }

    private static SubscriptionState parseSubscriptionState(final String state) {
	SubscriptionState subscriptionState;
	try {
	    subscriptionState = SubscriptionState.valueOf(state);
	} catch (final Exception e) {
	    subscriptionState = null;
	}
	return subscriptionState;
    }

    private final ArrayList<String> groups;
    private final XmppURI jid;
    private final String name;
    private Presence presence;

    private SubscriptionState subscriptionState;
    private final Type ask;

    public RosterItem(final XmppURI jid, final SubscriptionState subscriptionState, final String name, final Type ask) {
	this.ask = ask;
	this.jid = jid.getJID();
	this.subscriptionState = subscriptionState;
	this.name = name;
	this.groups = new ArrayList<String>();
	setPresence(null);
    }

    public Type getAsk() {
	return ask;
    }

    public List<String> getGroups() {
	return groups;
    }

    public XmppURI getJID() {
	return jid;
    }

    public String getName() {
	return name;
    }

    public Presence getPresence() {
	return presence;
    }

    public SubscriptionState getSubscriptionState() {
	return subscriptionState;
    }

    public void setPresence(final Presence presence) {
	if (presence == null) {
	    this.presence = new Presence(Type.unavailable, null, null).With(Show.away);
	} else {
	    this.presence = presence;
	}
    }

    public void setSubscriptionState(final SubscriptionState state) {
	this.subscriptionState = state;
    }

    void addGroup(final String group) {
	groups.add(group);
    }

    /**
     * Add the item as child of the given stanza
     * 
     * @param parent
     * @return the child stanza created
     */
    IPacket addStanzaTo(final IPacket parent) {
	final IPacket packet = parent.addChild("item", null);
	packet.With("jid", jid.toString()).With("name", name);
	for (final String group : groups) {
	    packet.addChild("group", null).setText(group);
	}
	return packet;
    }

    void setGroups(final String... groups) {
	this.groups.clear();
	for (final String group : groups) {
	    addGroup(group);
	}
    }

}