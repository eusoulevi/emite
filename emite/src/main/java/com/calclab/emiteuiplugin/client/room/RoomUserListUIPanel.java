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
package com.calclab.emiteuiplugin.client.room;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.services.I18nTranslationService;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.emite.client.extra.muc.Occupant.Role;
import com.calclab.emite.client.xmpp.stanzas.XmppURI;
import com.calclab.emiteuiplugin.client.users.DragGridConfiguration;
import com.calclab.emiteuiplugin.client.users.DropGridConfiguration;
import com.calclab.emiteuiplugin.client.users.RoomUserUI;
import com.calclab.emiteuiplugin.client.users.UserGridDropListener;
import com.calclab.emiteuiplugin.client.users.UserGridMenu;
import com.calclab.emiteuiplugin.client.users.UserGridMenuItemList;
import com.calclab.emiteuiplugin.client.users.UserGridPanel;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public class RoomUserListUIPanel extends UserGridPanel implements View {

    private final String moderatorLabel;
    private final String visitorLabel;
    private final String participantLabel;
    private final RoomUIPresenter presenter;
    private final I18nTranslationService i18n;

    public RoomUserListUIPanel(final I18nTranslationService i18n, final RoomUIPresenter presenter) {
	super(i18n.t("Nobody in this room"),
		new DragGridConfiguration("none", "Room users drag & drop in development"), new DropGridConfiguration(
			INVITE_TO_GROUP_DD, new UserGridDropListener() {
			    public void onDrop(final XmppURI userURI) {
				Log.info("Sending invitation to" + userURI);
				// FIXME: Create a dialog
				presenter.onInviteUserRequested(userURI, "Join to our conversation");
			    }
			}));
	this.i18n = i18n;
	this.presenter = presenter;
	moderatorLabel = i18n.t("Moderator");
	participantLabel = i18n.t("Participant");
	visitorLabel = i18n.t("Visitor");
	createUserListBottomBar();
    }

    public void addUser(final RoomUserUI roomUser, final UserGridMenuItemList menuItemList) {
	final UserGridMenu menu = new UserGridMenu(presenter);
	menu.setMenuItemList(menuItemList);
	super.addUser(roomUser, menu, formatUserType(roomUser.getRole()));
    }

    public View getView() {
	return this;
    }

    public void removeAllUsers() {
	super.removeAllUsers();
    }

    public void removeUser(final RoomUserUI roomUser) {
	super.removeUser(roomUser);

    }

    public void updateUser(final RoomUserUI roomUser, final UserGridMenuItemList menuItemList) {
	final UserGridMenu menu = new UserGridMenu(presenter);
	menu.setMenuItemList(menuItemList);
	super.removeUser(roomUser);
    }

    private void createUserListBottomBar() {
	final ToolbarButton inviteUserToGroupChat = new ToolbarButton();
	inviteUserToGroupChat.setIcon("images/group_add.gif");
	inviteUserToGroupChat.setCls("x-btn-icon");
	inviteUserToGroupChat.setTooltip(i18n.t("Invite another user to this chat room"));
	inviteUserToGroupChat.addListener(new ButtonListenerAdapter() {
	    private InviteToRoomPanel inviteToRoomDialog;

	    public void onClick(final Button button, final EventObject e) {
		if (inviteToRoomDialog == null) {
		    inviteToRoomDialog = new InviteToRoomPanel(i18n, presenter);
		}
		inviteToRoomDialog.show();
	    }
	});
	final Toolbar bottomToolbar = new Toolbar();
	bottomToolbar.addButton(inviteUserToGroupChat);
	super.setBottomToolbar(bottomToolbar);
    }

    private String formatUserType(final Role role) {
	switch (role) {
	case moderator:
	    return moderatorLabel;
	case participant:
	    return participantLabel;
	case visitor:
	    return visitorLabel;
	default:
	    return "";
	}
    }

}