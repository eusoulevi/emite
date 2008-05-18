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
package com.calclab.emite.client.core.packet;

import java.util.HashMap;
import java.util.List;

/**
 * TODO: no está nada claro lo del setText, getText, addText...
 * 
 * @author dani
 * 
 */
public interface IPacket {

    /**
     * 
     * 
     * @param the
     *                attribute name
     * @return the integer value
     * @throws an
     *                 exception
     */
    public int getAttributeAsInt(String name);

    public HashMap<String, String> getAttributes();

    public int getChildrenCount();

    public boolean hasAttribute(String name);

    public boolean hasAttribute(String name, String value);

    public IPacket With(final IPacket child);

    IPacket add(String nodeName, String xmlns);

    void addChild(IPacket child);

    String getAttribute(String name);

    List<? extends IPacket> getChildren();

    /**
     * Return a list of descendant childs after filter by the filter
     * 
     * @param filter
     * @return
     */
    List<? extends IPacket> getChildren(PacketFilter filter);

    /**
     * Return all the descendant childs with node name
     * 
     * @param name
     */
    List<? extends IPacket> getChildren(String name);

    IPacket getFirstChild(PacketFilter filter);

    /**
     * Return the first direct child with this name. NEVER returns null
     * 
     * @param childName
     * @return the Packet or NoPacket instance
     */
    IPacket getFirstChild(String childName);

    String getName();

    IPacket getParent();

    String getText();

    boolean hasChild(String name);

    void render(StringBuffer buffer);

    void setAttribute(String name, String value);

    void setText(String text);

    IPacket With(String name, long value);

    /**
     * Chain-able method to add a attribute
     * 
     * @param name
     * @param value
     * @return
     */
    IPacket With(String name, String value);

    IPacket WithText(String text);

}