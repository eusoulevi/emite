package com.calclab.emite.test.services;

import java.util.ArrayList;

import com.calclab.emite.core.client.packet.IPacket;
import com.calclab.emite.core.client.services.ConnectorCallback;
import com.calclab.emite.core.client.services.ConnectorException;
import com.calclab.emite.core.client.services.ScheduledAction;
import com.calclab.emite.core.client.services.Services;
import com.calclab.emite.j2se.services.TigaseXMLService;

public class MockServices implements Services {
    public static class Request {
	public final String httpBase;
	public final String request;
	public final ConnectorCallback listener;

	public Request(final String httpBase, final String request, final ConnectorCallback listener) {
	    this.httpBase = httpBase;
	    this.request = request;
	    this.listener = listener;
	}

    }

    private final TigaseXMLService xmler;
    private final ArrayList<Request> requests;

    public MockServices() {
	xmler = TigaseXMLService.getSingleton();
	this.requests = new ArrayList<Request>();
    }

    public long getCurrentTime() {
	return 0;
    }

    public IPacket getSentPacket(final int index) {
	final String request = requests.get(index).request;
	return TigaseXMLService.getSingleton().toXML(request);
    }

    public int requestSentCount() {
	return requests.size();
    }

    public void schedule(final int msecs, final ScheduledAction action) {
    }

    public void send(final String httpBase, final String request, final ConnectorCallback listener)
	    throws ConnectorException {
	requests.add(new Request(httpBase, request, listener));
    }

    public String toString(final IPacket packet) {
	return xmler.toString(packet);
    }

    public IPacket toXML(final String xml) {
	return xmler.toXML(xml);
    }

}