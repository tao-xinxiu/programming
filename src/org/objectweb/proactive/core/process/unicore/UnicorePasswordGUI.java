/* 
 * ################################################################
 * 
 * ProActive: The Java(TM) library for Parallel, Distributed, 
 *            Concurrent computing with Security and Mobility
 * 
 * Copyright (C) 1997-2007 INRIA/University of Nice-Sophia Antipolis
 * Contact: proactive@objectweb.org
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *  
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *  
 *  Initial developer(s):               The ProActive Team
 *                        http://www.inria.fr/oasis/ProActive/contacts.html
 *  Contributor(s): 
 * 
 * ################################################################
 */ 
package org.objectweb.proactive.core.process.unicore;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


/**
 * @author mleyton
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class UnicorePasswordGUI extends JFrame implements ActionListener {
    String keypassword = "";
    JTextField password;

    public synchronized String getKeyPassword() {
        while (keypassword.length() <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        notifyAll();

        return keypassword;
    }

    public synchronized void setKeyPassword(String keypassword) {
        this.keypassword = keypassword;
        notifyAll();
    }

    public UnicorePasswordGUI() {
        super("ProActive Unicore Client");

        JButton ok = new JButton("OK");
        ok.addActionListener(this);

        password = new JPasswordField(12);
        //password.setEchoChar('*');
        Container panel = this.getContentPane();
        panel.setLayout(new BorderLayout());

        panel.add(new JLabel("Input Unicore Keystore Password"),
            BorderLayout.NORTH);
        panel.add(password, BorderLayout.CENTER);
        panel.add(ok, BorderLayout.SOUTH);

        this.pack();
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        setKeyPassword(password.getText());
        this.dispose();
    }

    public static void main(String[] args) {
        UnicorePasswordGUI upGUI = new UnicorePasswordGUI();
        System.out.println("password:" + upGUI.getKeyPassword());
    }
}
