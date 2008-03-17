package com.calclab.emite.client.packet;

public class Event extends DelegatedPacket {
	public Event(final Packet packet) {
		super(packet);
	}

	public Event(final String name) {
		this(new BasicPacket("event", "transa:event"));
		setAttribute("name", name);
	}
}
