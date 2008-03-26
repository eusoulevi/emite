package com.calclab.emite.client.packet;

public class Event extends DelegatedPacket {

	private static final String TYPE = "type";

	private static Packet cloneEvent(final Event event) {
		return new BasicPacket("event", "emite:event").With(TYPE, event
				.getType());
	}

	public Event(final Event event) {
		super(cloneEvent(event));
	}

	public Event(final String type) {
		super(new BasicPacket("event", "emite:event"));
		setAttribute(TYPE, type);
	}

	public String getType() {
		return getAttribute(TYPE);
	}
}
