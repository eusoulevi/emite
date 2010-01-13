package com.calclab.emite.xep.search.client;

import java.util.HashMap;
import java.util.List;

import com.calclab.emite.core.client.xmpp.session.ResultListener;
import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;

/**
 * Search information repositories on the Jabber network.
 * 
 * Implements XEP-0055: Jabber Search
 * 
 * @see http://xmpp.org/extensions/xep-0055.html
 */
public interface SearchManager {

    /**
     * Set the host service where send the requests. Usually you send all the
     * request to same service, so its common to setup once (probably from a
     * meta tag)
     * 
     * @param host
     *            the jid of the search service
     */
    public void setHost(XmppURI host);

    /**
     * Request available search fields using simple search
     * 
     * @param onResult
     *            A SearchResult with a list of string with the available name
     *            fields
     * 
     * @see SearchResult
     */
    void requestSearchFields(ResultListener<SearchFields> listener);

    /**
     * Peform a simple search
     * 
     * @param query
     *            A HashMap with names and values
     * @param onResult
     *            A SearchResult with a list of returned items
     * 
     * @see SearchResult
     */
    void search(HashMap<String, String> query, ResultListener<List<SearchResultItem>> listener);

}