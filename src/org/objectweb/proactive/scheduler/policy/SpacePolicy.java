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
package org.objectweb.proactive.scheduler.policy;

import org.objectweb.proactive.scheduler.*;


/**
 * Space policy is a policy where the jobs that need the minimum of ressources
 * are served first. If more than one job need the same amount of ressources,
 * the oldest job in the queue is served first.
 *
 * @author cjarjouh
 *
 */
public class SpacePolicy extends AbstractPolicy {
    public SpacePolicy() {
        super();

        // TODO Auto-generated constructor stub
    }

    public SpacePolicy(RessourceManager ressourceManager) {
        super(ressourceManager);
    }

    /**
     * Returns true if job1 is to be served before job2 according to the policy.
     * @param job1
     * @param job2
     * @return true if job1 is to be served before job2.
     */
    public boolean isToBeServed(GenericJob job1, GenericJob job2) {
        // TODO Auto-generated method stub
        int ressourceNb1 = job1.getRessourceNb();
        int ressourceNb2 = job2.getRessourceNb();

        if ((ressourceNb1 < ressourceNb2) ||
                ((ressourceNb1 == ressourceNb2) &&
                (job1.getSubmitDate().before(job2.getSubmitDate())))) {
            return true;
        } else {
            return false;
        }
    }
}
