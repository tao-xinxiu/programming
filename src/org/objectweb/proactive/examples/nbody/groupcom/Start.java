/* 
* ################################################################
* 
* ProActive: The Java(TM) library for Parallel, Distributed, 
*            Concurrent computing with Security and Mobility
* 
* Copyright (C) 1997-2002 INRIA/University of Nice-Sophia Antipolis
* Contact: proactive-support@inria.fr
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
package org.objectweb.proactive.examples.nbody.groupcom;

import org.objectweb.proactive.ActiveObjectCreationException;
import org.objectweb.proactive.ProActive;
import org.objectweb.proactive.core.group.ProActiveGroup;
import org.objectweb.proactive.core.mop.ClassNotReifiableException;
import org.objectweb.proactive.core.node.Node;
import org.objectweb.proactive.core.node.NodeException;
import org.objectweb.proactive.examples.nbody.common.Displayer;
import org.objectweb.proactive.examples.nbody.common.Planet;
import org.objectweb.proactive.examples.nbody.common.Rectangle;


/**
 * Starts the simulation running the groupcom example.
 */
public class Start {
    
    public static void main(String[] args) {  
        org.objectweb.proactive.examples.nbody.common.Start.main(args) ; 
    }
    
    /**
     * Called by common.Start if this version is selected.
     */
    public static void main(int totalNbBodies, int maxIter, Displayer displayer, Node[] nodes,
            org.objectweb.proactive.examples.nbody.common.Start killsupport) {
        
        System.out.println("RUNNING groupcom VERSION");
        
        Rectangle universe = new Rectangle (-100,-100,100,100);
        Object [][] constructorParams = new Object [totalNbBodies][3] ;
        for (int  i = 0 ; i < totalNbBodies ; i++) {
            constructorParams[i][0] = new Integer(i);		      
            // coordinates between -100,-100 and 100,100
            constructorParams[i][1] = new Planet(universe);
            constructorParams[i][2] = killsupport ; 
        }
        Domain  domainGroup = null;
        try {
            // Create all the Domains as part of a Group
            domainGroup = (Domain) ProActiveGroup.newGroup ( Domain.class.getName(), constructorParams, nodes);
        } 
        catch (ClassNotReifiableException e) { killsupport.abort(e); }
        catch (ClassNotFoundException e) { killsupport.abort(e); }
        catch (ActiveObjectCreationException e) { killsupport.abort(e); } 
        catch (NodeException e) { killsupport.abort(e); }
        
        System.out.println("[NBODY] " + totalNbBodies + " Domains are deployed");
        
        Maestro maestro = null;
        try {
            // Supervizes the synchronisations
            maestro = (Maestro) ProActive.newActive(
                    Maestro.class.getName(), new Object[] {domainGroup, new Integer(maxIter), killsupport}, nodes[0]);
        } 
        catch (ActiveObjectCreationException e) {  killsupport.abort(e); }
        catch(NodeException e){  killsupport.abort(e); }
        
        
        // init workers
        domainGroup.init(domainGroup, displayer, maestro);
        
        // launch computation
        domainGroup.sendValueToNeighbours();
    }
    
}