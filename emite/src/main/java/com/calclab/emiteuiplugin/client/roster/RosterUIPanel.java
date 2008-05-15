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
package com.calclab.emiteuiplugin.client.roster;

import org.ourproject.kune.platf.client.services.I18nTranslationService;

import com.calclab.emite.client.xmpp.stanzas.Presence;
import com.calclab.emite.client.xmpp.stanzas.XmppURI;
import com.calclab.emiteuiplugin.client.users.ChatUserUI;
import com.calclab.emiteuiplugin.client.users.DragGridConfiguration;
import com.calclab.emiteuiplugin.client.users.DropGridConfiguration;
import com.calclab.emiteuiplugin.client.users.UserGridListener;
import com.calclab.emiteuiplugin.client.users.UserGridMenu;
import com.calclab.emiteuiplugin.client.users.UserGridMenuItemList;
import com.calclab.emiteuiplugin.client.users.UserGridPanel;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Panel;

public class RosterUIPanel extends UserGridPanel implements RosterUIView {

    private final RosterUIPresenter presenter;
    private final I18nTranslationService i18n;

    public RosterUIPanel(final I18nTranslationService i18n, final RosterUIPresenter presenter) {
        super(i18n.t("No buddies at this moment"), new DragGridConfiguration(USER_GROUP_DD, i18n
                .t("Drop in the chat area to start a chat.")
                + "<br/>" + i18n.t("Drop into a room to invite the user to join the chat room")),
                new UserGridListener() {
                    public void onDoubleClick(final XmppURI userJid) {
                        presenter.openChat(userJid);
                    }
                });
        this.i18n = i18n;
        this.presenter = presenter;
    }

    public void addRosterItem(final ChatUserUI user, final UserGridMenuItemList menuItemList) {
        final UserGridMenu menu = new UserGridMenu(presenter);
        menu.setMenuItemList(menuItemList);
        super.addUser(user, menu);
    }

    public void clearRoster() {
        super.removeAllUsers();
    }

    public void confDropInPanel(final Panel panel, final DropGridConfiguration dropGridConfiguration) {
        super.confDropInPanel(panel, dropGridConfiguration);
    }

    public void confirmSusbscriptionRequest(final Presence presence) {
        MessageBox.confirm(i18n.t("Confirm"), i18n.t("[%s] want to add you as a buddy. Do you want to permit?",
                presence.getFromURI().getJID().toString()), new MessageBox.ConfirmCallback() {
            public void execute(final String btnID) {
                if (btnID.equals("yes")) {
                    presenter.onPresenceAccepted(presence);
                } else {
                    presenter.onPresenceNotAccepted(presence);
                }
            }
        });
    }

    public void informRosterItemRemoved(final ChatUserUI user) {
        // not used currently
        MessageBox.alert(i18n.t("[%s] has removed you from his/her buddies list", user.getURI().toString()));
    }

    public void removeRosterItem(final ChatUserUI user) {
        super.removeUser(user);
    }

    public void showMessageAboutUnsuscription(final Presence presence) {
        MessageBox.alert(i18n.t("[%s] has decided to stop to show you his/her status", presence.getFrom()));
    }

    public void updateRosterItem(final ChatUserUI user, final UserGridMenuItemList menuItemList) {
        final UserGridMenu menu = new UserGridMenu(presenter);
        menu.setMenuItemList(menuItemList);
        super.updateRosterItem(user, menu);
    }
}
