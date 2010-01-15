package com.calclab.hablar.client.ui.page;

import com.calclab.suco.client.events.Event;
import com.calclab.suco.client.events.Listener;
import com.google.gwt.user.client.ui.Composite;

/**
 * A PageWidget is a widget with a header and some status. Is the base of the
 * hablar widgets panels.
 */
public abstract class PageWidget extends Composite implements Page {

    protected final HeaderWidget header;
    private final Event<PageWidget> statusAction;
    private final Event<PageWidget> closeEvent;
    private final Event<PageWidget> openEvent;
    private Visibility visibility;
    private String status;

    public PageWidget(final boolean closeable) {
	this.closeEvent = new Event<PageWidget>("page.close");
	this.header = new HeaderWidget(this, closeable);
	this.statusAction = new Event<PageWidget>("page.status");
	this.openEvent = new Event<PageWidget>("page.open");
	this.visibility = Visibility.hidden;
    }

    public void fireOpen() {
	openEvent.fire(this);
    }

    public PageHeader getHeader() {
	return this.header;
    }

    public String getStatus() {
	return status;
    }

    @Override
    public String getStatusMessage() {
	return status;
    }

    public Visibility getVisibility() {
	return visibility;
    }

    public void onClose(final Listener<PageWidget> listener) {
	closeEvent.add(listener);
    }

    public void onStatusMessageChanged(final Listener<PageWidget> listener) {
	statusAction.add(listener);
    }

    public void onVisibilityChanged(final Listener<PageWidget> listener) {
	openEvent.add(listener);
    }

    @Override
    public void setHeaderIconClass(final String iconClass) {
	header.setIconClass(iconClass);
    }

    @Override
    public void setHeaderTitle(final String title) {
	header.setHeaderTitle(title);
    }

    public void setId(final String id) {
	header.ensureDebugId(id);
    }

    @Override
    public void setStatusMessage(final String status) {
	this.status = status;
	statusAction.fire(this);
	if (visibility == Visibility.closed) {
	    header.requestFocus();
	}
    }

    public void setVisibility(final Visibility visibility) {
	this.visibility = visibility;
	header.setVisibility(visibility);
    }

    protected void fireClose() {
	closeEvent.fire(this);
    }

}