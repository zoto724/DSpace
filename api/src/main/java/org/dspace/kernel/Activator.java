/**
 * $Id: Activator.java 3492 2009-02-24 16:03:56Z azeckoski $
 * $URL: https://scm.dspace.org/svn/repo/dspace2/core/trunk/api/src/main/java/org/dspace/kernel/Activator.java $
 * Activator.java - DS2 - Feb 24, 2009 11:44:14 AM - azeckoski
 **************************************************************************
 * Copyright (c) 2008 Aaron Zeckoski
 * Licensed under the Apache License, Version 2.0
 * 
 * A copy of the Apache License has been included in this 
 * distribution and is available at: http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * Aaron Zeckoski (azeckoski @ gmail.com) (aaronz @ vt.edu) (aaron @ caret.cam.ac.uk)
 */

package org.dspace.kernel;


/**
 * An activator is a special type which allows a provider to be plugged into the system by dropping a jar file
 * in with the kernel and adding in a hook in the configuration file. Activators are started after the
 * initial classes and the service manager have already been started. All classes which implement this
 * must have: <br/>
 * 1) A public empty constructor (takes no parameters) (e.g. public MyClass() {} ) <br/>
 * <br/>
 * If you want the system to execute your class then you must list it in the config file with the fully qualified classpath
 * (NOTE that the xxx can be anything as long as it is unique): <br/>
 * <b>activator.xxx = org.dspace.MyClass</b> <br/>
 * <br/>
 * {@link #start(ServiceManager)} will be called after the class is created during kernel startup. 
 * Developers should create their providers/plugins/etc. in this method and
 * use the registration methods in the {@link ServiceManager} to register them. 
 * {@link #stop(ServiceManager)} will be called when the kernel shuts down. Perform any cleanup/shutdown actions
 * you like during this phase (unregistering your services here is a good idea). <br/>
 * <br/>
 * This is modeled after the OSGi BundleActivator <br/>
 * <br/>
 * There is another type of activator used in DSpace but it is configured via the configuration service only.
 * The class activator is configured by creating a config property like this 
 * (NOTE that the xxx can be anything as long as it is unique): <br/>
 * <b>activator.class.xxx = org.dspace.MyClass;org.dspace.MyServiceName;constructor</b> <br/>
 * Unlike the normal activators, these are started up when the kernel core services start and thus can actually
 * be accessed from the service manager and referenced in providers and plugins.
 * 
 * @author Aaron Zeckoski (azeckoski @ gmail.com)
 */
public interface Activator {

    /**
     * This is called when the service manager is starting this activator, it is only called once. 
     * It will be called after the core services are started. The ClassLoader used will be the one
     * that this class is associated with to ensure all dependencies are available. <br/>
     * This method should be used to startup and register services in most cases but it can be used
     * to simply perform some system startup actions if desired. <br/>
     * Exceptions thrown out of this method will not cause the system startup to fail. <br/>
     * 
     * @param serviceManager the current system service manager
     */
    public void start(ServiceManager serviceManager);

    /**
     * This is called when the service manager is shutting down this activator, it is only called once. 
     * It will be called before the core services are stopped. The ClassLoader used will be the one
     * that this class is associated with to ensure all dependencies are available. <br/>
     * This method should be used to shutdown and unregister services in most cases but it can be used
     * to simply perform some system shutdown actions if desired. <br/>
     * Exceptions thrown out of this method will not cause the system shutdown to fail. <br/>
     * WARNING: this can hang the shutdown by performing operations that take a long long time or are deadlocked,
     * the developer is expected to ensure this does not happen
     * 
     * @param serviceManager the current system service manager
     */
    public void stop(ServiceManager serviceManager);

}
