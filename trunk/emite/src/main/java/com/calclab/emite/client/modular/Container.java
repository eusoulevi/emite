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
package com.calclab.emite.client.modular;

/**
 * A component container
 * 
 * @author dani
 */
public interface Container {

    /**
     * Obtain a component registered with the given key from the container
     * 
     * @param <T>
     * @param componentType
     * @return The component, throw RuntimeException if the given component
     *         doesnt exist
     */
    <T> T getInstance(Class<T> componentKey);

    /**
     * Obtain a provider of the component type from the container
     * 
     * @param <T>
     * @param componentType
     * @return
     */
    <T> Provider<T> getProvider(Class<T> componentKey);

    /**
     * Register a component into the container
     * 
     * @param <T>
     * @param componentType
     *                the key under this component is registered
     * @param component
     *                the component itself (any POJO)
     * @return the registered component
     */
    <T> T register(Class<T> componentType, T component);

    <T> Provider<T> registerProvider(Class<T> componentKey, Provider<T> provider);

}