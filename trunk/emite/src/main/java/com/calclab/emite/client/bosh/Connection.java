package com.calclab.emite.client.bosh;

import com.calclab.emite.client.packet.Event;
import com.calclab.emite.client.packet.Packet;

public interface Connection {

    public static class Events {
        public static final Event start = new Event("connection:start");
        public static final Event error = new Event("connection:error");
		public static final Event send = new Event("connection:send");
    }

    public void send(Packet toBeSend);
}

