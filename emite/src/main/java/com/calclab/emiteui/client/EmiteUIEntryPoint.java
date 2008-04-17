/*
 *
 * ((e)) emite: A pure gwt (Google Web Toolkit) xmpp (jabber) library
 *
 * (c) 2008 The emite development team (see CREDITS for details)
 * This file is part of emite.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.calclab.emiteui.client;

import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.extend.PluginManager;
import org.ourproject.kune.platf.client.extend.UIExtensionPointManager;
import org.ourproject.kune.platf.client.services.I18nTranslationServiceMocked;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.emite.client.core.bosh.BoshOptions;
import com.calclab.emite.client.im.roster.Roster;
import com.calclab.emiteui.client.emiteuiplugin.EmiteUiPlugin;
import com.calclab.emiteui.client.emiteuiplugin.UserChatOptions;
import com.calclab.emiteui.client.emiteuiplugin.params.MultiChatCreationParam;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;

public class EmiteUIEntryPoint implements EntryPoint {
    private DefaultDispatcher dispatcher;
    private TextField passwd;
    private TextField jid;

    public void onModuleLoad() {
        /*
         * Install an UncaughtExceptionHandler which will produce <code>FATAL</code>
         * log messages
         */

        Log.setUncaughtExceptionHandler();

        // At the moment, in runtime:
        Log.setCurrentLogLevel(Log.LOG_LEVEL_DEBUG);

        // Log.getDivLogger().moveTo(10, 60);
        // Log.getDivLogger().setSize("1200", "400");

        /*
         * Use a deferred command so that the UncaughtExceptionHandler catches
         * any exceptions in onModuleLoadCont()
         */
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                onModuleLoadCont();
            }
        });
    }

    public void onModuleLoadCont() {
        createFormPanel();
        createExtUI();
    }

    private void createExtUI() {
        dispatcher = DefaultDispatcher.getInstance();
        final PluginManager kunePluginManager = new PluginManager(dispatcher, new UIExtensionPointManager(),
                new I18nTranslationServiceMocked());
        kunePluginManager.install(new EmiteUiPlugin());

        dispatcher.fire(EmiteUiPlugin.OPEN_CHAT_DIALOG, new MultiChatCreationParam(new BoshOptions("http-bind"),
                generateUserChatOptions()));
    }

    private void createFormPanel() {
        final Panel panel = new Panel();
        panel.setBorder(false);
        panel.setPaddings(15);

        final FormPanel formPanel = new FormPanel();
        formPanel.setFrame(true);
        formPanel.setTitle("Some external Login Form");

        formPanel.setWidth(300);
        formPanel.setLabelWidth(75);
        formPanel.setUrl("save-form.php");

        final Label label = new Label();
        label.setHtml("<p>Currently we are only supporting PLAIN authentication, "
                + "them for your security, only use jabber test accounts.</p><br/><br/>");
        label.setCls("simple-form-label");
        label.setWidth(350);
        label.setHeight(20);

        jid = new TextField("Jabber id", "jid", 180);
        jid.setAllowBlank(false);
        jid.setValue("admin@localhost");
        formPanel.add(jid);

        passwd = new TextField("Password", "last", 180);
        passwd.setAllowBlank(false);
        passwd.setValue("easyeasy");
        passwd.setPassword(true);
        formPanel.add(passwd);

        jid.addListener(new FieldListenerAdapter() {
            public void onChange(final Field field, final Object newVal, final Object oldVal) {

            }
        });

        passwd.addListener(new FieldListenerAdapter() {
            public void onChange(final Field field, final Object newVal, final Object oldVal) {
                dispatcher.fire(EmiteUiPlugin.REFLESH_USER_OPTIONS, generateUserChatOptions());
            }
        });

        panel.add(label);

        panel.add(formPanel);

        RootPanel.get().add(panel);
    }

    private UserChatOptions generateUserChatOptions() {
        return new UserChatOptions(jid.getRawValue(), passwd.getRawValue(), "blue", Roster.DEF_SUBSCRIPTION_MODE);
    }

}
