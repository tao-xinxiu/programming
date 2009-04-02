/*
 * ################################################################
 *
 * ProActive: The Java(TM) library for Parallel, Distributed,
 *            Concurrent computing with Security and Mobility
 *
 * Copyright (C) 1997-2009 INRIA/University of Nice-Sophia Antipolis
 * Contact: proactive@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version
 * 2 of the License, or any later version.
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
 *  Initial developer(s):               The ActiveEon Team
 *                        http://www.activeeon.com/
 *  Contributor(s):
 *
 *
 * ################################################################
 * $$ACTIVEEON_INITIAL_DEV$$
 */
package functionalTests.messagerouting.router.blackbox;

import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.objectweb.proactive.core.util.ProActiveRandom;
import org.objectweb.proactive.extra.messagerouting.protocol.AgentID;
import org.objectweb.proactive.extra.messagerouting.protocol.message.ErrorMessage;
import org.objectweb.proactive.extra.messagerouting.protocol.message.Message;
import org.objectweb.proactive.extra.messagerouting.protocol.message.RegistrationRequestMessage;
import org.objectweb.proactive.extra.messagerouting.protocol.message.ErrorMessage.ErrorType;

import functionalTests.messagerouting.BlackBox;


public class TestInvalidReconnection extends BlackBox {

    /*
     * Send an invalid Registration Request with an AgentID.
     * Since this AgentID is not know by the router, an error message
     * if expected in response
     *
     * An error message is expected is expected
     */
    @Test
    public void test() throws IOException {
        AgentID agentID = new AgentID(0xcafe);
        long messageID = ProActiveRandom.nextPosLong();
        Message message = new RegistrationRequestMessage(agentID, messageID);
        tunnel.write(message.toByteArray());

        byte[] resp = tunnel.readMessage();
        ErrorMessage error = (ErrorMessage) Message.constructMessage(resp, 0);
        Assert.assertEquals(ErrorType.ERR_INVALID_AGENT_ID, error.getErrorType());
        Assert.assertEquals(agentID, error.getRecipient());
        Assert.assertEquals(agentID, error.getSender());
        Assert.assertEquals(messageID, error.getMessageID());

    }
}