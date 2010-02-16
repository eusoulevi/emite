package com.calclab.hablar.roster.client.addtogroup;

import com.calclab.hablar.core.client.mvp.Display;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasText;

public interface AddToGroupDisplay extends Display {
    HasClickHandlers getApply();

    HasClickHandlers getCancel();

    HasText getNewGroupName();
}