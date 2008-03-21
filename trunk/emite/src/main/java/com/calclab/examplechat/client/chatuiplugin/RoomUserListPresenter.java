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

package com.calclab.examplechat.client.chatuiplugin;

import java.util.ArrayList;

import com.calclab.examplechat.client.chatuiplugin.ui.RoomUserListPanel;

public class RoomUserListPresenter implements RoomUserList {
    private RoomUserListPanel view;
    private final ArrayList<RoomUser> users;

    public RoomUserListPresenter() {
        users = new ArrayList<RoomUser>();
    }

    public void init(final RoomUserListPanel view) {
        this.view = view;
    }

    public void add(final RoomUser user) {
        users.add(user);
        view.addUser(user);
    }

    public void remove(final RoomUser user) {
        view.delUser(users.indexOf(user));
    }

    public RoomUserListView getView() {
        return view;
    }

}
