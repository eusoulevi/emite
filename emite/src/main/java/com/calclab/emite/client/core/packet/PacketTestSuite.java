package com.calclab.emite.client.core.packet;

import java.util.List;

/**
 * A multi-environment class to test packets
 * 
 * @author dani
 * 
 */
public final class PacketTestSuite {

    public static interface Helper {
	public void assertEquals(Object expected, Object actual);

	public void assertTrue(String message, boolean condition);

	public IPacket createPacket(String nodeName);

	public void log(String message);
    }

    private static class HelperExtended implements Helper {
	private final Helper delegate;

	public HelperExtended(final Helper delegate) {
	    this.delegate = delegate;
	}

	public void assertEquals(final Object expected, final Object actual) {
	    delegate.assertEquals(expected, actual);
	}

	public void assertFalse(final boolean condition) {
	    assertTrue("", !condition);
	}

	public void assertNotNull(final Object o) {
	    assertTrue("should be NOT null: " + safeString(o), o != null);
	}

	public void assertNull(final Object value) {
	    assertTrue("should be null: " + safeString(value), value == null);
	}

	public void assertSame(final Object expected, final Object actual) {
	    assertTrue("should be same: " + safeString(expected) + " but was: " + safeString(actual),
		    expected == actual);
	}

	public void assertTrue(final boolean condition) {
	    assertTrue("", condition);
	}

	public void assertTrue(final String message, final boolean condition) {
	    delegate.assertTrue(message, condition);
	}

	public IPacket createPacket(final String nodeName) {
	    return delegate.createPacket(nodeName);
	}

	public void log(final String message) {
	    delegate.log(message);
	}

	public String safeString(final Object value) {
	    return value == null ? "[null]" : value.toString();
	}

    }

    public static void runPacketTests(final Helper utility) {
	final HelperExtended helper = new HelperExtended(utility);
	shouldNeverReturnNullWhenGetChildren(helper);
	shouldGetChildren(helper);
	shouldReturnNoPacketWhenGetFirstChild(helper);
	shouldSetAndClearTheAttributes(helper);
	shouldSetText(helper);
	shouldRemoveChildIfPresent(helper);
    }

    private static void shouldGetChildren(final HelperExtended helper) {
	final IPacket packet = helper.createPacket("presence");
	packet.addChild("x", "xmlns:x");
	packet.addChild("x", "xmlns:x");
	helper.assertEquals(2, packet.getChildren("x").size());
	helper.log("- test ends");
    }

    private static void shouldNeverReturnNullWhenGetChildren(final HelperExtended helper) {
	helper.log("- shouldNeverReturnNullWhenGetChildren");
	final IPacket packet = helper.createPacket("root");
	final List<? extends IPacket> children = packet.getChildren();
	helper.assertNotNull(children);
	helper.assertEquals(0, children.size());
	helper.log("- test ends");
    }

    private static void shouldRemoveChildIfPresent(final HelperExtended helper) {
	helper.log("- shouldRemoveChildIfPresent");
	final IPacket root = helper.createPacket("packet");
	helper.assertFalse(root.removeChild(helper.createPacket("otherPacket")));
	final IPacket child = root.addChild("child", null);
	helper.assertEquals(1, root.getChildrenCount());
	helper.assertTrue(root.removeChild(child));
	helper.assertEquals(0, root.getChildrenCount());
	helper.log("- test ends");
    }

    private static void shouldReturnNoPacketWhenGetFirstChild(final HelperExtended helper) {
	helper.log("- shouldReturnNoPacketWhenGetFirstChild");
	final IPacket packet = helper.createPacket("root");
	final IPacket child = packet.getFirstChild("child");
	helper.assertNotNull(child);
	helper.assertSame(NoPacket.INSTANCE, child);
	helper.log("- test ends");
    }

    private static void shouldSetAndClearTheAttributes(final HelperExtended helper) {
	helper.log("- shouldSetAndClearTheAttributes");
	final IPacket packet = helper.createPacket("packet");
	packet.setAttribute("name", "value");
	helper.assertEquals("value", packet.getAttribute("name"));
	packet.setAttribute("name", null);
	helper.assertNull(packet.getAttribute("name"));
	helper.assertFalse(packet.hasAttribute("name"));
	helper.log("- test ends");
    }

    private static void shouldSetText(final HelperExtended helper) {
	helper.log("- shouldSetAndClearTheAttributes");
	final IPacket packet = helper.createPacket("packet");
	packet.setText("text1");
	helper.assertEquals("text1", packet.getText());
	packet.setText("text2");
	helper.assertEquals("text2", packet.getText());
	helper.log("- test ends");
    }

}