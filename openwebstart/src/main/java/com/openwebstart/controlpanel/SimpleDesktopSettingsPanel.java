package com.openwebstart.controlpanel;

import net.adoptopenjdk.icedteaweb.client.controlpanel.ComboItem;
import net.adoptopenjdk.icedteaweb.i18n.Translator;
import net.adoptopenjdk.icedteaweb.jnlp.element.information.ShortcutDesc;
import net.sourceforge.jnlp.config.ConfigurationConstants;
import net.sourceforge.jnlp.config.DeploymentConfiguration;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;

public class SimpleDesktopSettingsPanel extends FormPanel {

    /**
     * Create a new instance of the desktop shortcut settings panel.
     *
     * @param config Loaded DeploymentConfiguration file.
     */
    public SimpleDesktopSettingsPanel(DeploymentConfiguration config) {

        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JLabel description = new JLabel(Translator.getInstance().translate("desktop.integration.userInteractionType.name"));
        JComboBox<ComboItem> shortcutComboOptions = new JComboBox<>();
        ComboItem[] items = {deploymentJavawsShortcutToComboItem(ShortcutDesc.CREATE_NEVER),
                deploymentJavawsShortcutToComboItem(ShortcutDesc.CREATE_ALWAYS),
                deploymentJavawsShortcutToComboItem(ShortcutDesc.CREATE_ASK_USER),
                deploymentJavawsShortcutToComboItem(ShortcutDesc.CREATE_ASK_USER_IF_HINTED),
                deploymentJavawsShortcutToComboItem(ShortcutDesc.CREATE_ALWAYS_IF_HINTED)};

        shortcutComboOptions.setActionCommand(ConfigurationConstants.KEY_CREATE_DESKTOP_SHORTCUT); // The configuration property this combobox affects.
        for (int j = 0; j < items.length; j++) {
            shortcutComboOptions.addItem(items[j]);
            if (config.getProperty(ConfigurationConstants.KEY_CREATE_DESKTOP_SHORTCUT).equals(items[j].getValue())) {
                shortcutComboOptions.setSelectedIndex(j);
            }
        }

        shortcutComboOptions.addItemListener(e -> {
            ComboItem c = (ComboItem) e.getItem();
            config.setProperty(shortcutComboOptions.getActionCommand(), c.getValue());
        });

        addRow(0, description, shortcutComboOptions);
        addFlexibleRow(1);
    }

    private static ComboItem deploymentJavawsShortcutToComboItem(String i) {
        return new ComboItem(ShortcutDesc.deploymentJavawsShortcutToString(i), i);
    }
}
