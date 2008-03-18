package com.calclab.emite.client.packet;

import java.util.List;

public class DelegatedPacket implements Packet {
	private final Packet delegate;

	public DelegatedPacket(final Packet delegate) {
		this.delegate = delegate;
	}

	public final Packet add(final String nodeName, final String xmlns) {
		return delegate.add(nodeName, xmlns);
	}

	public final void addText(final String text) {
		delegate.addText(text);
	}

	public final String getAttribute(final String name) {
		return delegate.getAttribute(name);
	}

	public List<? extends Packet> getChildren() {
		return delegate.getChildren();
	}

	public List<Packet> getChildren(final String name) {
		return delegate.getChildren(name);
	}

	public final Packet getFirstChildren(final String childName) {
		return delegate.getFirstChildren(childName);
	}

	public final String getName() {
		return delegate.getName();
	}

	public Packet getParent() {
		return delegate.getParent();
	}

	public final String getText() {
		return delegate.getText();
	}

	public final void setAttribute(final String name, final String value) {
		delegate.setAttribute(name, value);
	}

	public void setText(final String text) {
		delegate.setText(text);
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

	public final Packet with(final String name, final String value) {
		return delegate.with(name, value);
	}

}
