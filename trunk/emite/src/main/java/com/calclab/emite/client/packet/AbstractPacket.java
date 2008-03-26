package com.calclab.emite.client.packet;

public abstract class AbstractPacket implements Packet {

	public boolean hasAttribute(final String name) {
		return getAttribute(name) != null;
	}

	public boolean hasAttribute(final String name, final String value) {
		return value.equals(getAttribute(name));
	}

	public Packet With(final Packet child) {
		addChild(child);
		return this;
	}

	public Packet With(final String name, final long value) {
		return With(name, String.valueOf(value));
	}

	public Packet With(final String name, final String value) {
		setAttribute(name, value);
		return this;
	}
}
