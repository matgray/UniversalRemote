/*******************************************************************************
 * Copyright (c) 2014. Mathew Gray
 *
 * This work is licensed under a Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 ******************************************************************************/

package me.mgray.universalremote.client;

import me.mgray.universalremote.client.model.ServerConnector;
import me.mgray.universalremote.client.model.event.DisconnectEvent;
import me.mgray.universalremote.client.model.event.SessionIdRecievedEvent;
import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;

public class ClientView extends JDialog {
    private JPanel contentPane;
    private JButton buttonConnect;
    private JPanel mainPanel;
    private JLabel imageLabel;
    private boolean connected = false;

    public ClientView() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonConnect);
        contentPane.setPreferredSize(new Dimension(300, 200));
        this.setResizable(false);

        AnnotationProcessor.process(this);

        buttonConnect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        if (!connected) {
            ServerConnector.createNew().connect();
        } else {
            EventBus.publish(new DisconnectEvent());
            imageLabel.setIcon(null);
            imageLabel.setText("Not Connected");
            buttonConnect.setText("Connect");
            connected = false;
        }
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    @SuppressWarnings("unused")
    @EventSubscriber(eventClass = SessionIdRecievedEvent.class)
    public void onSessionIdRecieved(SessionIdRecievedEvent event) {
        System.out.println(String.format("Session id received (%s)", event.getSessionId()));
        ByteArrayOutputStream out = net.glxn.qrgen.QRCode.from(event.getSessionId()).stream();
        ImageIcon imageIcon = new ImageIcon(out.toByteArray());
        imageLabel.setText("");
        imageLabel.setIcon(imageIcon);
        buttonConnect.setText("Disconnect");
        connected = true;
    }

    public static void main(String[] args) {
        ClientView dialog = new ClientView();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
