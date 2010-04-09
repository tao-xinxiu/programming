/*
 * ################################################################
 *
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2010 INRIA/University of 
 * 				Nice-Sophia Antipolis/ActiveEon
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 
 * or a different license than the GPL.
 *
 *  Initial developer(s):               The ActiveEon Team
 *                        http://www.activeeon.com/
 *  Contributor(s):
 *
 * ################################################################
 * $$ACTIVEEON_INITIAL_DEV$$
 */
package org.objectweb.proactive.api;

import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import org.objectweb.proactive.annotation.PublicAPI;
import org.objectweb.proactive.core.ProActiveException;
import org.objectweb.proactive.core.remoteobject.RemoteObjectAdapter;
import org.objectweb.proactive.core.remoteobject.RemoteObjectExposer;
import org.objectweb.proactive.core.remoteobject.RemoteObjectHelper;
import org.objectweb.proactive.core.remoteobject.RemoteRemoteObject;
import org.objectweb.proactive.core.remoteobject.adapter.Adapter;
import org.objectweb.proactive.core.runtime.ProActiveRuntimeImpl;
import org.objectweb.proactive.core.util.log.Loggers;
import org.objectweb.proactive.core.util.log.ProActiveLogger;


/**
 * This class provides methods to create, bind, lookup and unregister  a remote object.
 * A 'Remote Object' is a standard java object that is  remotely accessible through
 * all of the communication protocols supported by the ProActive's remote object framework.
 */
@PublicAPI
public class PARemoteObject {
    public final static Logger logger = ProActiveLogger.getLogger(Loggers.REMOTEOBJECT);

    /* Used to attribute an unique name to RO created by turnRemote */
    private final static String TURN_REMOTE_PREFIX;
    static {
        String vmName = ProActiveRuntimeImpl.getProActiveRuntime().getVMInformation().getName();
        TURN_REMOTE_PREFIX = "/" + vmName + "/TURN_REMOTE/";
    }

    /* Used to attribute an unique name to RO created by turnRemote */
    private final static AtomicLong counter = new AtomicLong();

    public static <T> RemoteObjectExposer<T> newRemoteObject(String className, T target) {
        return new RemoteObjectExposer<T>(className, target);
    }

    public static <T> RemoteObjectExposer<T> newRemoteObject(String className, T target,
            Class<? extends Adapter<T>> targetRemoteObjectAdapter) {
        return new RemoteObjectExposer<T>(className, target, targetRemoteObjectAdapter);
    }

    public static <T> T bind(RemoteObjectExposer<T> roe, URI uri) throws ProActiveException {
        RemoteRemoteObject irro = roe.createRemoteObject(uri);
        return (T) new RemoteObjectAdapter(irro).getObjectProxy();
    }

    /**
     * List all the remote objects contained within a registry
     * @param url url of the registry
     * @return return the list of objects (not only remote objects) registered in the registry identified by the param url
     * @throws ProActiveException
     */
    public static URI[] list(URI url) throws ProActiveException {
        return RemoteObjectHelper.getFactoryFromURL(url).list(RemoteObjectHelper.expandURI(url));
    }

    /**
     * unregister the object located at the endpoint identified by the url
     * @param url
     * @throws ProActiveException
     */
    public static void unregister(URI url) throws ProActiveException {
        RemoteObjectHelper.getFactoryFromURL(url).unregister(RemoteObjectHelper.expandURI(url));
    }

    /**
     * perform a lookup on the url in order to retrieve a stub on the object exposed through
     * a remote object
     * @param url the url to lookup
     * @return a stub on the object exposed by the remote object
     * @throws ProActiveException
     */
    public static Object lookup(URI url) throws ProActiveException {
        return RemoteObjectHelper.getFactoryFromURL(url).lookup(RemoteObjectHelper.expandURI(url))
                .getObjectProxy();
    }

    /**
     * Turn a POJO into a remote object.
     *
     * @param object the object to be exported as a remote object
     * @return A remote object that can be called from any JVM
     * @exception ProActiveException if the remote object cannot be created
     */
    @SuppressWarnings("unchecked")
    public static <T> T turnRemote(T object) throws ProActiveException {
        RemoteObjectExposer<T> roe = newRemoteObject(object.getClass().getName(), object);
        RemoteRemoteObject rro = roe
                .createRemoteObject(TURN_REMOTE_PREFIX + counter.incrementAndGet(), false);
        return (T) new RemoteObjectAdapter(rro).getObjectProxy();
    }
}
