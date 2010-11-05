package com.calclab.hablar.search.client;

import static com.calclab.hablar.search.client.HablarSearch.i18n;

import com.calclab.emite.im.client.chat.ChatManager;
import com.calclab.emite.im.client.roster.Roster;
import com.calclab.emite.xep.search.client.SearchResultItem;
import com.calclab.hablar.core.client.ui.menu.Menu;
import com.calclab.hablar.core.client.ui.menu.SimpleAction;
import com.calclab.suco.client.Suco;

public class SearchBasicActions {
	public static final String REMOVE_ROSTER_MENU_DEB_ID = "SearchLogic-remove-menu";
	public static final String CHAT_DEB_ID = "SearchLogic-chat";
	public static final String ADD_ROSTERITEM_DEB_ID = "SearchLogic-add-item";
	public static final String REMOVE_ROSTERITEM_DEB_ID = "SearchLogic-remove-item";

	private final Roster roster;
	private final ChatManager chatManager;

	public SearchBasicActions() {
		chatManager = Suco.get(ChatManager.class);
		roster = Suco.get(Roster.class);
	}

	public void addMenuOptions(final Menu<SearchResultItem> menu) {
		addAddToRosterAction(menu);
		addStartChatAction(menu);
		addRemoveFromRosterAction(menu);
	}
	
	protected void addAddToRosterAction(final Menu<SearchResultItem> menu) {
		menu.addAction(new SimpleAction<SearchResultItem>(i18n()
				.addToContacts(), ADD_ROSTERITEM_DEB_ID) {
			@Override
			public void execute(final SearchResultItem item) {
				roster.requestAddItem(item.getJid(), item.getNick());
			}

			@Override
			public boolean isApplicable(final SearchResultItem item) {
				return roster.getItemByJID(item.getJid()) == null;
			}
		});
	}
	
	protected void addStartChatAction(final Menu<SearchResultItem> menu) {
		menu.addAction(new SimpleAction<SearchResultItem>(i18n().chat(),
				CHAT_DEB_ID) {
			@Override
			public void execute(final SearchResultItem item) {
				chatManager.open(item.getJid());
			}
		});
	}
	
	protected void addRemoveFromRosterAction(final Menu<SearchResultItem> menu) {
		menu.addAction(new SimpleAction<SearchResultItem>(i18n()
				.removeFromContacts(), REMOVE_ROSTERITEM_DEB_ID) {
			@Override
			public void execute(final SearchResultItem item) {
				roster.removeItem(item.getJid());
			}

			@Override
			public boolean isApplicable(final SearchResultItem item) {
				return roster.getItemByJID(item.getJid()) != null;
			}
		});

	}

}
