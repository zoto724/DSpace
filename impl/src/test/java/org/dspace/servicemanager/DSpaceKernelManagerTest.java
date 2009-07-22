/**
 * $Id: DSpaceKernelManagerTest.java 3409 2009-01-30 12:04:43Z azeckoski $
 * $URL: https://scm.dspace.org/svn/repo/dspace2/core/trunk/impl/src/test/java/org/dspace/servicemanager/DSpaceKernelManagerTest.java $
 * DSpaceKernelManagerTest.java - DSpace2 - Oct 6, 2008 7:23:54 PM - azeckoski
 **************************************************************************
 * Copyright (c) 2008 Aaron Zeckoski
 * Licensed under the Apache License, Version 2.0
 * 
 * A copy of the Apache License has been included in this 
 * distribution and is available at: http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * Aaron Zeckoski (azeckoski @ gmail.com) (aaronz @ vt.edu) (aaron @ caret.cam.ac.uk)
 */

package org.dspace.servicemanager;

import static org.junit.Assert.*;

import org.dspace.kernel.DSpaceKernel;
import org.dspace.kernel.DSpaceKernelManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * Testing the kernel manager
 * 
 * @author Aaron Zeckoski (azeckoski @ gmail.com)
 */
public class DSpaceKernelManagerTest {

    DSpaceKernelManager kernelManager;
    DSpaceKernelImpl kernelImpl;

    @Before
    public void init() {
        kernelImpl = DSpaceKernelInit.getKernel(null);
        kernelImpl.start(); // init the kernel
        kernelManager = new DSpaceKernelManager();
    }

    @After
    public void destroy() {
        if (kernelImpl != null) {
            // cleanup the kernel
            kernelImpl.stop();
            kernelImpl.destroy();
        }
        kernelImpl = null;
    }

    /**
     * Test method for {@link org.dspace.kernel.DSpaceKernelManager#getKernel()}.
     */
    @Test
    public void testGetKernel() {
        DSpaceKernel kernel = kernelManager.getKernel();
        assertNotNull(kernel);
        DSpaceKernel k2 = kernelManager.getKernel();
        assertNotNull(k2);
        assertEquals(kernel, k2);
    }

}
