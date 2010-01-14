package com.calclab.hablar.client.ui.page;

import com.calclab.suco.client.events.Listener;

public interface Page {

    public static enum Visibility {
	open, closed, hidden
    }

    PageHeader getHeader();

    String getStatusMessage();

    /**
     * Add a listener to onClose events. This listener is called when the close
     * button in the header is clicked
     * 
     * @param closeListener
     */
    void onClose(Listener<PageWidget> closeListener);

    /**
     * Add a listener to know when the status message of this page has changed
     * 
     * @param statusListener
     */
    void onStatusMessageChanged(Listener<PageWidget> statusListener);

    /**
     * Add a listener to know when this page is open
     * 
     * @param openListener
     */
    void onVisibilityChanged(Listener<PageWidget> openListener);

    /**
     * Change the status message of this page
     * 
     * @param status
     */
    void setStatusMessage(String status);

    /**
     * Inform the page about it's current visibility state
     * 
     * @param visibility
     */
    void setVisibility(Visibility visibility);

}
