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
package org.objectweb.proactive.ext.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;


/**
 * @author acontes
 *
 */
public class SerializableKeyStore implements Serializable {
    protected transient KeyStore keyStore;
    protected byte[] encodedKeyStore;

    public SerializableKeyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    private void writeObject(java.io.ObjectOutputStream out)
        throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();

        try {
            keyStore.store(bout, "ha".toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        encodedKeyStore = bout.toByteArray();
        bout.close();

        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in)
        throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        try {
            keyStore = KeyStore.getInstance("PKCS12", "BC");
            keyStore.load(new ByteArrayInputStream(encodedKeyStore),
                "ha".toCharArray());
        } catch (KeyStoreException e) {
            // TODOSECURITYSECURITY Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            // TODOSECURITYSECURITY Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODOSECURITYSECURITY Auto-generated catch block
            e.printStackTrace();
        } catch (CertificateException e) {
            // TODOSECURITYSECURITY Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODOSECURITYSECURITY Auto-generated catch block
            e.printStackTrace();
        }
    }

    public KeyStore getKeyStore() {
        return keyStore;
    }
}
