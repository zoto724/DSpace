/**
 * $Id: DSpaceAbstractRequestTest.java 3563 2009-03-10 17:31:52Z mdiggory $
 * $URL: https://scm.dspace.org/svn/repo/dspace2/core/trunk/impl/src/test/java/org/dspace/test/DSpaceAbstractRequestTest.java $
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

package org.dspace.test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;


/**
 * This is an abstract class which makes it easier to test things that use the DSpace Kernel
 * and includes an automatic request wrapper around every test method which will start and
 * end a request, the default behavior is to end the request with a failure which causes
 * a rollback and reverts the storage to the previous values
 * 
 * @author Aaron Zeckoski (azeckoski @ gmail.com)
 */
public abstract class DSpaceAbstractRequestTest extends DSpaceAbstractKernelTest {

    /**
     * @return the current request ID for the current running request
     */
    public String getRequestId() {
        return requestId;
    }

    @BeforeClass
    public static void initRequestService() {
        _initializeRequestService();
    }

    @Before
    public void startRequest() {
        _startRequest();
    }

    @After
    public void endRequest() {
        _endRequest();
    }

    @AfterClass
    public static void cleanupRequestService() {
        _destroyRequestService();
    }

}
