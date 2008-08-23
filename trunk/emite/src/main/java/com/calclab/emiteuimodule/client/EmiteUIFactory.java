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
package com.calclab.emiteuimodule.client;

import org.ourproject.kune.platf.client.services.I18nTranslationService;

import com.calclab.emite.client.Xmpp;
import com.calclab.emite.client.xep.chatstate.ChatStateManager;
import com.calclab.emite.client.xmpp.stanzas.XmppURI;
import com.calclab.emiteuimodule.client.chat.ChatStatePresenter;
import com.calclab.emiteuimodule.client.chat.ChatUI;
import com.calclab.emiteuimodule.client.chat.ChatUIPanel;
import com.calclab.emiteuimodule.client.chat.ChatUIPresenter;
import com.calclab.emiteuimodule.client.dialog.MultiChatPanel;
import com.calclab.emiteuimodule.client.dialog.MultiChatPresenter;
import com.calclab.emiteuimodule.client.params.AvatarProvider;
import com.calclab.emiteuimodule.client.params.MultiChatCreationParam;
import com.calclab.emiteuimodule.client.room.RoomUI;
import com.calclab.emiteuimodule.client.room.RoomUIPanel;
import com.calclab.emiteuimodule.client.room.RoomUIPresenter;
import com.calclab.emiteuimodule.client.room.RoomUserListUIPanel;
import com.calclab.emiteuimodule.client.roster.RosterUIPanel;
import com.calclab.emiteuimodule.client.roster.RosterUIPresenter;
import com.calclab.emiteuimodule.client.sound.SoundManager;
import com.calclab.emiteuimodule.client.status.StatusUI;
import com.calclab.suco.client.provider.Provider;

public class EmiteUIFactory {
    private final I18nTranslationService i18n;
    private final Xmpp xmpp;
    private final StatusUI statusUI;
    private final Provider<SoundManager> soundManagerProvider;

    public EmiteUIFactory(final Xmpp xmpp, final I18nTranslationService i18n, final StatusUI statusUI,
	    final Provider<SoundManager> soundManagerProvider) {
	this.xmpp = xmpp;
	this.i18n = i18n;
	this.statusUI = statusUI;
	this.soundManagerProvider = soundManagerProvider;
    }

    public ChatUI createChatUI(final XmppURI otherURI, final String currentUserAlias, final String currentUserColor,
	    final ChatStateManager chatStateManager) {
	final ChatUIPresenter presenter = new ChatUIPresenter(otherURI, currentUserAlias, currentUserColor);
	new ChatStatePresenter(i18n, chatStateManager, presenter);
	final ChatUIPanel panel = new ChatUIPanel(presenter);
	presenter.init(panel);
	return presenter;
    }

    public MultiChatPresenter createMultiChat(final MultiChatCreationParam param) {
	final RosterUIPresenter roster = createRosterUI(param.getAvatarProvider());
	final MultiChatPresenter presenter = new MultiChatPresenter(xmpp, i18n, this, param, roster, statusUI,
		soundManagerProvider);
	final MultiChatPanel panel = new MultiChatPanel(param.getChatDialogTitle(), (RosterUIPanel) roster.getView(),
		statusUI, i18n, presenter);
	presenter.init(panel);
	return presenter;
    }

    public RoomUI createRoomUI(final XmppURI otherURI, final String currentUserAlias, final String currentUserColor,
	    final I18nTranslationService i18n) {
	final RoomUIPresenter presenter = new RoomUIPresenter(i18n, otherURI, currentUserAlias, currentUserColor);
	// FIXME: create list presenter
	final RoomUserListUIPanel roomUserListUIPanel = new RoomUserListUIPanel(i18n, presenter);
	final RoomUIPanel panel = new RoomUIPanel(i18n, roomUserListUIPanel, presenter);
	presenter.init(panel, roomUserListUIPanel);
	return presenter;
    }

    public RosterUIPresenter createRosterUI(final AvatarProvider provider) {
	final RosterUIPresenter roster = new RosterUIPresenter(xmpp, i18n, provider);
	final RosterUIPanel rosterUIPanel = new RosterUIPanel(i18n, roster);
	roster.init(rosterUIPanel);
	return roster;
    }

}
