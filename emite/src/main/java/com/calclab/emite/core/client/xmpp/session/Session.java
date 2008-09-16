package com.calclab.emite.core.client.xmpp.session;

import com.calclab.emite.core.client.bosh.StreamSettings;
import com.calclab.emite.core.client.packet.IPacket;
import com.calclab.emite.core.client.xmpp.sasl.SASLManager;
import com.calclab.emite.core.client.xmpp.stanzas.IQ;
import com.calclab.emite.core.client.xmpp.stanzas.Message;
import com.calclab.emite.core.client.xmpp.stanzas.Presence;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.suco.client.listener.Listener;

/**
 * The most important object in Xmpp emite module. You can login, send and
 * receive stanzas. It also allows you to pause and resume the session.
 * 
 */
public interface Session {

    /**
     * Different session states
     */
    public static enum State {
	authorized, loggedIn, connecting, disconnected, error, notAuthorized, ready
    }

    public static XmppURI ANONYMOUS = SASLManager.ANONYMOUS;

    /**
     * Returns the current user xmpp uri
     * 
     * @return the current user xmpp uri
     */
    public abstract XmppURI getCurrentUser();

    /**
     * Returns the current state
     * 
     * @return a session.state enum type
     */
    public abstract State getState();

    /**
     * Answer if is logged in or not
     * 
     * @return true if a user is logged in
     */
    public abstract boolean isLoggedIn();

    /**
     * <p>
     * Start a login process with the current xmpp uri and password. Use
     * onLoggedIn method to know when you are really logged in. If the uri
     * doesn't provide a resource, the session will generate one.
     * </p>
     * <p>
     * You can use Session.ANONYMOUS and null as password to do an anonymous
     * login.
     * </p>
     * 
     * @param uri
     *            the user's uri to loggin
     * @param password
     *            the user's password
     */
    public abstract void login(final XmppURI uri, final String password);

    /**
     * Start a logout process in the current session. Use obnLoggedOut to know
     * when you are really logged out.
     */
    public abstract void logout();

    /**
     * The given listener is called when a IQ <b>of type 'get' or 'set'</b> is
     * received
     * 
     * @param listener
     */
    public abstract void onIQ(Listener<IQ> listener);

    /**
     * The given listener is called when the user has logged in into the
     * session.
     * 
     * @param listener
     *            receives the user's logged in URI
     */
    public abstract void onLoggedIn(final Listener<XmppURI> listener);

    /**
     * The given listener is called when the user has logged out into the
     * session (and just before close the connection)
     * 
     * @param listener
     *            receives the user's logged out URI
     */
    public abstract void onLoggedOut(final Listener<XmppURI> listener);

    /**
     * The given listener is called when a message stanza has arrived
     * 
     * @param listener
     * 
     */
    public abstract void onMessage(final Listener<Message> listener);

    /**
     * The given listener is called when a presence stanza has arrived
     * 
     * @param listener
     */
    public abstract void onPresence(final Listener<Presence> listener);

    /**
     * The given listener is called when the session changed it's state
     * 
     * @param listener
     */
    public abstract void onStateChanged(final Listener<State> listener);

    /**
     * Call this method to pause the session. You can use the given object
     * object (or other with the same data) to resume the session later.
     * 
     * @see http://www.xmpp.org/extensions/xep-0124.html#inactive
     * @see Session.resume
     * @return
     */
    public abstract StreamSettings pause();

    /**
     * Call this method to resume a session.
     * 
     * @see http://www.xmpp.org/extensions/xep-0124.html#inactive
     * @see Session.pause
     * @param userURI
     *            the previous session user's uri
     * @param settings
     *            the stream settings given by the pause method
     */
    public abstract void resume(XmppURI userURI, StreamSettings settings);

    /**
     * Send a stanza to the server. This method overrides the "from" uri
     * attribute.
     * 
     * <b>All the stanzas sent using this method BEFORE the LoggedIn state are
     * queued and sent AFTER Ready state.</b>
     * 
     * @see sendIQ
     * @param stanza
     *            the stanza to be sent
     */
    public abstract void send(final IPacket stanza);

    /**
     * A helper method that allows to send a IQ stanza and attach a listener to
     * the response. This method overrides (if present) the given IQ id using
     * the category provided and a internal sequential number. This method also
     * overrides (if present) the given 'from' attribute
     * 
     * <b>All the stanzas sent using this method BEFORE the LoggedIn state are
     * queued and sent AFTER Ready state.</b>
     * 
     * @param category
     *            a uniqe-per-component string that allows the session to
     *            generate a sequential and uniqe id for the IQ
     * @param iq
     *            the IQ stanza to be sent
     * @param listener
     *            the listener called when a IQ of type "result" arrives to the
     *            server. After the invocation, the listener is discarded
     * 
     */
    public abstract void sendIQ(final String category, final IQ iq, final Listener<IPacket> listener);

}