package com.calclab.hablar.client.chat;

import com.calclab.emite.im.client.chat.Chat;
import com.calclab.hablar.client.pages.PageWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class ChatWidget extends PageWidget {

    private static ChatWidgetUiBinder uiBinder = GWT.create(ChatWidgetUiBinder.class);

    interface ChatWidgetUiBinder extends UiBinder<Widget, ChatWidget> {
    }

    enum MessageType {
	incoming, sent
    }

    @UiField
    TextArea talkBox;

    @UiField
    FlowPanel list;

    private final ChatLogic logic;

    public ChatWidget(Chat chat) {
	super(true);
	initWidget(uiBinder.createAndBindUi(this));
	logic = new ChatLogic(chat, this);
    }

    @UiHandler("talkBox")
    public void handleKeys(KeyDownEvent event) {
	if (event.getNativeKeyCode() == 13) {
	    logic.onTalk(talkBox.getText());
	    event.stopPropagation();
	    event.preventDefault();
	}
    }

    public void clearAndFocus() {
	talkBox.setText("");
	talkBox.setFocus(true);
    }

    public void showMessage(String name, String body, MessageType type) {
	list.add(new ChatMessage(name, body, type));
    }


}
