/*
 * ################################################################
 *
 * ProActive: The Java(TM) library for Parallel, Distributed,
 *            Concurrent computing with Security and Mobility
 *
 * Copyright (C) 1997-2005 INRIA/University of Nice-Sophia Antipolis
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
package org.objectweb.proactive.ext.locationserver;

import java.io.IOException;

import org.objectweb.proactive.Body;
import org.objectweb.proactive.core.body.request.Request;
import org.objectweb.proactive.core.body.request.RequestReceiver;
import org.objectweb.proactive.core.event.MessageEventListener;


public class BouncingRequestReceiver implements RequestReceiver {
    protected static java.io.IOException REUSABLE_EXCEPTION = new java.io.IOException(
            "Object has migrated");

    public int receiveRequest(Request r, Body bodyReceiver)
        throws java.io.IOException {
        //System.out.println("BouncingRequestReceiver: receiveRequest()");
        throw REUSABLE_EXCEPTION;
    }

    public void addMessageEventListener(MessageEventListener listener) {
    }

    public void removeMessageEventListener(MessageEventListener listener) {
    }

    public void setImmediateService(String methodName) {
    }

    public void removeImmediateService(String methodName,
        Class[] parametersTypes) throws IOException {
    }

    public void setImmediateService(String methodName, Class[] parametersTypes)
        throws IOException {
    }
}
