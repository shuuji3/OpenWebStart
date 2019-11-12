package com.openwebstart.os;

import com.openwebstart.controlpanel.ButtonPanelFactory;
import com.openwebstart.controlpanel.FormPanel;
import com.openwebstart.jvm.ui.dialogs.ModalDialog;
import net.adoptopenjdk.icedteaweb.i18n.Translator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class AskForEntriesDialog extends ModalDialog {

    private final JCheckBox menuCheckBox;

    private final JCheckBox desktopCheckBox;

    public AskForEntriesDialog(final String appName, final boolean askForMenu, final boolean askForDesktop) {

        menuCheckBox = new JCheckBox(Translator.getInstance().translate("shortcuts.dialog.menuEntry.text"));
        desktopCheckBox = new JCheckBox(Translator.getInstance().translate("shortcuts.dialog.desktopEntry.text"));

        final FormPanel formPanel = new FormPanel();
        formPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        final JLabel textLabel = new JLabel(Translator.getInstance().translate("shortcuts.dialog.text"));

        int row = 0;

        formPanel.addEditorRow(row++, textLabel);
        if(askForMenu) {
            formPanel.addEditorRow(row++, menuCheckBox);
        }
        if(askForDesktop) {
            formPanel.addEditorRow(row++, desktopCheckBox);
        }
        formPanel.addFlexibleRow(row++);

        final JButton okButton = new JButton(Translator.getInstance().translate("shortcuts.dialog.ok.text"));
        okButton.addActionListener(e -> {
            this.setVisible(false);
            this.dispose();
        });
        final JPanel buttonPanel = ButtonPanelFactory.createButtonPanel(okButton);

        final JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(formPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(contentPane);
    }

    public AskForEntriesDialogResult showAndWaitForResult() {
        showAndWait();
        return new AskForEntriesDialogResult(desktopCheckBox.isSelected(), menuCheckBox.isSelected());
    }
}