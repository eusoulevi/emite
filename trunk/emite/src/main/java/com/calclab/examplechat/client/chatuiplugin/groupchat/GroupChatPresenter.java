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

import java.util.HashMap;
import java.util.Map;

import org.ourproject.kune.platf.client.View;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.examplechat.client.chatuiplugin.groupchat.GroupChatUser.UserType;

public class GroupChatPresenter implements GroupChat {

    private final static String[] USERCOLORS = { "green", "navy", "black", "grey", "olive", "teal", "blue", "lime",
            "purple", "fuchsia", "maroon", "red" };

    private int currentColor;
    private GroupChatView view;
    private String input;
    private String subject;
    private String userAlias;
    // FIXME: this in RoomUserList?
    private final Map<String, GroupChatUser> users;
    private String roomName;
    private GroupChatUserList userList;
    // private XmppRoom handler;
    private final GroupChatListener listener;
    private boolean closeConfirmed;
    private UserType userType;

    public GroupChatPresenter(final GroupChatListener listener) {
        this.listener = listener;
        this.input = "";
        this.currentColor = 0;
        this.subject = "Subject: " + roomName;
        users = new HashMap<String, GroupChatUser>();
    }

    public void setRoomName(final String roomName) {
        this.roomName = roomName;
        view.showRoomName(roomName);
    }

    public void setUserAlias(final String userAlias) {
        this.userAlias = userAlias;
    }

    public void setUserType(final UserType userType) {
        this.userType = userType;
    }

    public void setUserList(final GroupChatUserList userList) {
        this.userList = userList;
    }

    public void init(final GroupChatView view) {
        this.view = view;
        closeConfirmed = false;
    }

    public View getView() {
        return view;
    }

    public void addMessage(final String userAlias, final String message) {
        String userColor;

        GroupChatUser user = users.get(userAlias);
        if (user != null) {
            userColor = user.getColor();
        } else {
            Log.debug("User " + userAlias + " not in our users list");
            userColor = "black";
        }
        view.showMessage(userAlias, userColor, message);
        listener.onMessageReceived(this);
    }

    public void addInfoMessage(final String message) {
        view.showInfoMessage(message);
    }

    public void addUser(final String alias, final UserType type) {
        GroupChatUser user = new GroupChatUser(alias, getNextColor(), type);
        getUsersList().add(user);
        users.put(alias, user);
    }

    public void removeUser(final String alias) {
        getUsersList().remove(users.get(alias));
    }

    public void addDelimiter(final String datetime) {
        view.showDelimiter(datetime);
    }

    public void clearSavedInput() {
        saveInput(null);
    }

    public String getSessionAlias() {
        return userAlias;
    }

    public void saveInput(final String inputText) {
        input = inputText;
    }

    public String getSavedInput() {
        return input;
    }

    public void doClose() {
        // handler.logout();
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getName() {
        return roomName;
    }

    private String getNextColor() {
        String color = USERCOLORS[currentColor++];
        if (currentColor >= USERCOLORS.length) {
            currentColor = 0;
        }
        return color;
    }

    public GroupChatUserList getUsersList() {
        return userList;
    }

    public GroupChatUserListView getUsersListView() {
        return userList.getView();
    }

    // public void setHandler(final XmppRoom handler) {
    // this.handler = handler;
    // listener.onRoomReady(this);
    // }

    public boolean isReady() {
        return true;
        // return handler != null;
    }

    // public XmppRoom getHandler() {
    // // return handler;
    // }

    public void onCloseConfirmed() {
        closeConfirmed = true;
    }

    public void onCloseNotConfirmed() {
        closeConfirmed = false;
    }

    public boolean isCloseConfirmed() {
        return closeConfirmed;
    }

    public void activate() {
        view.scrollDown();
    }

    public UserType getUserType() {
        return userType;
    }

}
