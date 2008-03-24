/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.calclab.examplechat.client.chatuiplugin.groupchat;

import org.ourproject.kune.platf.client.ui.IconLabel;

import com.calclab.examplechat.client.chatuiplugin.utils.Emoticons;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class GroupChatUserListPanel extends VerticalPanel implements GroupChatUserListView {
    public GroupChatUserListPanel() {
    }

    public int addUser(final GroupChatUser user) {
        HorizontalPanel userPanel = new HorizontalPanel();
        AbstractImagePrototype icon;
        if (user.getUserType() == GroupChatUser.MODERADOR) {
            icon = Emoticons.App.getInstance().bulletStar();
        } else {
            icon = Emoticons.App.getInstance().bulletBlack();
        }
        IconLabel userLabel = new IconLabel(icon, user.getAlias());
        userLabel.setColor(user.getColor());
        userPanel.add(userLabel);
        super.add(userPanel);
        return this.getWidgetCount();
    }

    public void delUser(final int index) {
        this.remove(index);
    }
}
