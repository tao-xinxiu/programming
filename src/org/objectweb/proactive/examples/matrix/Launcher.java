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
package org.objectweb.proactive.examples.matrix;

import org.apache.log4j.Logger;
import org.objectweb.proactive.core.node.Node;
import org.objectweb.proactive.core.node.NodeException;
import org.objectweb.proactive.core.node.NodeFactory;
import org.objectweb.proactive.core.util.log.Loggers;
import org.objectweb.proactive.core.util.log.ProActiveLogger;


public class Launcher implements java.io.Serializable {
    static Logger logger = ProActiveLogger.getLogger(Loggers.EXAMPLES);
    Node[] nodesList;

    public Launcher() {
    }

    public Launcher(String[] nodesNameList) throws NodeException {
        nodesList = new Node[nodesNameList.length];
        for (int i = 0; i < nodesNameList.length; i++) {
            nodesList[i] = NodeFactory.getNode(nodesNameList[i]);
        }
    }

    // MAIN !!!
    public void start(Matrix m1, Matrix m2, int i) {
        // DISTRIBUTED MULTIPLICATION      
        int matrixSize = m1.getWidth();

        long startTime;
        long endTime;

        startTime = System.currentTimeMillis();

        //System.out.println("Multiplication!!!!! ");
        Matrix groupResult = multiply(m1, m2 /*group*/);

        //endTime = System.currentTimeMillis() - startTime;
        //System.out.println("     Distributed Multiplication : " + endTime + " millisecondes\n");
        //startTime = System.currentTimeMillis();
        // RECONSTRUCTION
        try {
            Matrix result = reconstruction(groupResult, matrixSize);
        } catch (Exception e) {
        }

        endTime = System.currentTimeMillis() - startTime;
        logger.info("\n       Result (" + i + ") : Total time spent = " +
            endTime + " millisecondes");

        //System.out.println(result);
    }

    public Matrix createMatrix(int size) {
        Matrix m = new Matrix(size, size);
        m.initializeWithRandomValues();
        return m;
    }

    public Matrix distribute(Matrix m) {
        Matrix verticalSubMatrixGroup = null;
        verticalSubMatrixGroup = m.transformIntoActiveVerticalSubMatrixGroup(nodesList);

        return verticalSubMatrixGroup;
    }

    public Matrix multiply(Matrix m, Matrix group) {
        Matrix ma = group.localMultiplyForGroup(m);
        return ma;
    }

    public Matrix reconstruction(Matrix group, int size) {
        Matrix result = null;

        result = new Matrix(group, size);

        return result;
    }

    public String getString(Matrix m) {
        return m.toString();
    }
}
