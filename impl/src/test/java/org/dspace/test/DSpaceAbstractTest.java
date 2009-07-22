/**
 * $Id: DSpaceAbstractTest.java 3563 2009-03-10 17:31:52Z mdiggory $
 * $URL: https://scm.dspace.org/svn/repo/dspace2/core/trunk/impl/src/test/java/org/dspace/test/DSpaceAbstractTest.java $
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

import org.dspace.kernel.DSpaceKernel;
import org.dspace.kernel.ServiceManager;
import org.dspace.servicemanager.DSpaceKernelImpl;
import org.dspace.servicemanager.DSpaceKernelInit;
import org.dspace.services.RequestService;

/**
 * This is an abstract class which makes it easier to test things that use the DSpace Kernel
 * 
 * @author Aaron Zeckoski (azeckoski @ gmail.com)
 */
public abstract class DSpaceAbstractTest {

    protected static DSpaceKernelImpl kernelImpl;
    /**
     * The current kernel for testing
     */
    public static DSpaceKernel kernel;
    /**
     * @return the current kernel running for this test
     */
    public static DSpaceKernel getKernel() {
        return kernel;
    }
    /**
     * @return the current service manager for this kernel
     */
    public static ServiceManager getServiceManager() {
        return kernel.getServiceManager();
    }

    /**
     * This starts up the kernel,
     * run this before running all your tests (before the test suite),
     * do not run this after each individual test
     */
    public static void _initializeKernel() {
        kernelImpl = DSpaceKernelInit.getKernel(null);
        kernelImpl.start(); // init the kernel
        kernel = kernelImpl.getManagedBean();
    }

    /**
     * This shuts the kernel down,
     * run this at the end of all your tests (after the test suite),
     * do not run this after each individual test
     */
    public static void _destroyKernel() {
        if (kernelImpl != null) {
            // cleanup the kernel
            try {
                kernelImpl.stop();
            } catch (Exception e) {
                // keep going
            }
            kernelImpl.destroy();
        }
        // must null things out or JUnit will not clean them up
        kernelImpl = null;
        kernel = null;
    }

    /**
     * Gets a service for testing, can be cast to the impl if desired,
     * only works if this is the only service of the given type
     * 
     * @param <T>
     * @param type the type of the service desired
     * @return the service of the type requested
     */
    public static <T> T getService(Class<T> type) {
        T service = kernel.getServiceManager().getServiceByName(null, type);
        if (service == null) {
            throw new IllegalStateException("Could not find service ("+type+") in service manager for testing");
        }
        return service;
    }

    /**
     * The request service
     */
    public static RequestService requestService;
    public static RequestService getRequestService() {
        return requestService;
    }

    /**
     * Holds the current request Id
     */
    protected String requestId = null;

    /**
     * Gets the request service,
     * run this before all tests (before the test suite)
     */
    public static void _initializeRequestService() {
        requestService = getService(RequestService.class);
    }

    /**
     * Cleans up the request service,
     * run this after all tests are complete (after the test suite)
     */
    public static void _destroyRequestService() {
        // must null things out or JUnit will not clean them up
        requestService = null;
    }

    /**
     * Starts a request,
     * this should be run before each individual test
     */
    public void _startRequest() {
        requestId = requestService.startRequest();
    }

    /**
     * Ends a request,
     * this should be run after each individual test
     */
    public void _endRequest() {
        requestService.endRequest(new RuntimeException("End of test request ("+requestId+"), standard handling, this is not a failure"));
    }

}
