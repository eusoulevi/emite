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
package com.calclab.emiteuiplugin.client.users;

public class UserGridMenuItem<T> {

    public interface UserGridMenuItemListener {
	public void onAction();
    }

    final String iconCls;
    final String title;
    String eventName;

    T param;
    private UserGridMenuItemListener listener;

    @Deprecated
    public UserGridMenuItem(final String iconCls, final String title, final String eventName, final T param) {
	this.iconCls = iconCls;
	this.title = title;
	this.eventName = eventName;
	this.param = param;
    }

    public UserGridMenuItem(final String iconCls, final String title, final UserGridMenuItemListener listener) {
	this.listener = listener;
	this.listener = listener;
	this.iconCls = iconCls;
	this.title = title;
    }

    public String getEventName() {
	return eventName;
    }

    public String getIconCls() {
	return iconCls;
    }

    public T getParam() {
	return param;
    }

    public String getTitle() {
	return title;
    }

}
