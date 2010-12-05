package com.calclab.hablar.roster.client;

import com.calclab.emite.core.client.xmpp.session.Session;
import com.calclab.emite.core.client.xmpp.session.Session.State;
import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.emite.im.client.roster.RosterItem;
import com.calclab.emite.im.client.roster.SubscriptionHandler;
import com.calclab.emite.im.client.roster.SubscriptionHandler.Behaviour;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.page.PagePresenter.Visibility;
import com.calclab.hablar.core.client.ui.menu.SimpleAction;
import com.calclab.hablar.roster.client.groups.RosterGroupPresenter;
import com.calclab.hablar.roster.client.page.RosterPage;
import com.calclab.hablar.roster.client.page.RosterPresenter;
import com.calclab.hablar.roster.client.page.RosterWidget;
import com.calclab.hablar.roster.client.selection.DoubleListRosterItemSelector;
import com.calclab.suco.client.Suco;
import com.calclab.suco.client.events.Listener;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class HablarRoster implements EntryPoint {

	private static RosterMessages rosterMessages;

	public static void addActions(final RosterPage roster) {
		roster.addLowPriorityActions();
	}

	public static RosterMessages i18n() {
		return rosterMessages;
	}

	public static RosterPage installModule(final Hablar hablar,
			RosterConfig rosterConfig) {
		final SubscriptionHandler subscriptionHandler = Suco
				.get(SubscriptionHandler.class);
		subscriptionHandler.setBehaviour(Behaviour.acceptAll);

		if (rosterConfig.rosterItemClickAction == null
				&& rosterConfig.oneClickChat) {
			rosterConfig.rosterItemClickAction = new SimpleAction<RosterItem>(
					i18n().clickToOpenChat(), "rosterItemClickAction") {
				@Override
				public void execute(RosterItem item) {
					final ChatManager manager = Suco.get(ChatManager.class);
					manager.open(item.getJID());
				}
			};
		}

		final RosterPage roster = new RosterPresenter(hablar.getEventBus(),
				new RosterWidget(), rosterConfig);
		roster.setVisibility(Visibility.notFocused);
		hablar.addPage(roster);

		final Session session = Suco.get(Session.class);
		session.onStateChanged(new Listener<Session>() {

			@Override
			public void onEvent(final Session session) {
				setState(roster, session.getState());
			}
		});
		setState(roster, session.getState());
		roster.addHighPriorityActions();
		return roster;
	}

	public static void setMessages(final RosterMessages messages) {
		rosterMessages = messages;
	}

	private static void setState(final RosterPage roster, final State state) {
		if (state == State.ready) {
			roster.requestVisibility(Visibility.focused);
		}
	}

	@Override
	public void onModuleLoad() {
		RosterMessages messages = (RosterMessages) GWT
				.create(RosterMessages.class);
		setMessages(messages);
		RosterBasicActions.setMessages(messages);
		RosterGroupPresenter.setMessages(messages);
		DoubleListRosterItemSelector.setMessages(messages);
	}

}