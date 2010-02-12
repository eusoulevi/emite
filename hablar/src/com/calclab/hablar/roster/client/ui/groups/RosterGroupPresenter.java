package com.calclab.hablar.roster.client.ui.groups;

import java.util.Collection;
import java.util.HashMap;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.roster.Roster;
import com.calclab.emite.im.client.roster.RosterItem;
import com.calclab.hablar.core.client.mvp.Presenter;
import com.calclab.hablar.core.client.ui.menu.Menu;
import com.calclab.suco.client.Suco;

public class RosterGroupPresenter implements Presenter<RosterGroupDisplay> {
    private final RosterGroupDisplay display;
    private final String groupLabel;
    private final HashMap<XmppURI, RosterItemPresenter> items;
    private final Menu<RosterItemPresenter> itemMenu;
    private final String groupName;

    public RosterGroupPresenter(final String groupName, final Menu<RosterItemPresenter> itemMenu,
	    final RosterGroupDisplay display) {
	this.groupName = groupName;
	this.itemMenu = itemMenu;
	this.display = display;
	groupLabel = isAllContacts() ? "All contacts" : "Group: " + groupName;
	items = new HashMap<XmppURI, RosterItemPresenter>();

	final Roster roster = Suco.get(Roster.class);
	final Collection<RosterItem> items = isAllContacts() ? roster.getItems() : roster.getItemsByGroup(groupName);
	for (final RosterItem item : items) {
	    getPresenter(item);
	}
    }

    private RosterItemPresenter createRosterItem(final RosterItem item) {
	final RosterItemDisplay itemDisplay = display.newRosterItemDisplay();
	final RosterItemPresenter presenter = new RosterItemPresenter(groupName, itemMenu, itemDisplay);
	display.add(itemDisplay);
	items.put(item.getJID(), presenter);
	return presenter;
    }

    @Override
    public RosterGroupDisplay getDisplay() {
	return display;
    }

    public String getGroupLabel() {
	return groupLabel;
    }

    public String getGroupName() {
	return groupName;
    }

    private RosterItemPresenter getPresenter(final RosterItem item) {
	RosterItemPresenter presenter = items.get(item.getJID());
	if (presenter == null) {
	    presenter = createRosterItem(item);
	}
	presenter.setItem(item);
	return presenter;
    }

    public boolean isAllContacts() {
	return groupName.length() == 0;
    }

    public void toggleVisibility() {
	display.setVisible(!display.isVisible());
    }

}
