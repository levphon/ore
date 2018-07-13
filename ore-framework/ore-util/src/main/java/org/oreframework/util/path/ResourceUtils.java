/*
 * (C) 2007-2012 Alibaba Group Holding Limited.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 * Authors:
 *   leiwen <chrisredfield1985@126.com> , boyan <killme2008@gmail.com>
 */
package org.oreframework.util.path;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * @author huangzz
 * @version [1.0.0, 2015-9-25]
 */
public class ResourceUtils {
	/** logger */

	/** classpath prefix */
	public static final String CLASSPATH_URL_PREFIX = "classpath:";

	/**
	 * get a ClassLoader
	 * 
	 * @return
	 */
	public static ClassLoader getClassLoader() {
		ClassLoader cl = null;
		cl = Thread.currentThread().getContextClassLoader();
		if (cl == null) {
			// No thread context class loader, use class loader of this class.
			cl = ResourceUtils.class.getClassLoader();
		}
		return cl;
	}

	/**
	 * get config file name as URL
	 * 
	 * @param configFileName
	 * @return
	 * @throws Exception
	 */
	public static URL getAsUrl(String configFileName) {
		if (configFileName.startsWith(CLASSPATH_URL_PREFIX)) {
			configFileName = configFileName.substring(CLASSPATH_URL_PREFIX.length());
		}
		URL url = getClassLoader().getResource(configFileName);
		if (url == null) {
			System.out.println("config file: {} cannot be found!" + configFileName);
		}
		return url;
	}

	/**
	 * get config file name as File
	 * 
	 * @param file
	 * @return
	 */
	public static File getAsFile(String configFileName) {
		URL url = getAsUrl(configFileName);
		if (url != null) {
			URI uri = toURI(url);
			if (uri != null) {
				return new File(uri.getSchemeSpecificPart());
			}
		}
		return null;
	}

	public static URI toURI(URL url) {
		try {
			return new URI(url.toString().replaceAll(" ", "%20"));
		} catch (URISyntaxException e) {
			return null;
		}
	}

	public static InputStream getAsStream(String name) {
		return getClassLoader().getResourceAsStream(name);
	}

	/** */
	/**
	 * Returns the URL of the resource on the classpath
	 * 
	 * @param resource
	 *            The resource to find
	 * @throws IOException
	 *             If the resource cannot be found or read
	 * @return The resource
	 */
	public static URL getResourceURL(String resource) throws IOException {
		URL url = null;
		ClassLoader loader = ResourceUtils.class.getClassLoader();
		if (loader != null)
			url = loader.getResource(resource);
		if (url == null)
			url = ClassLoader.getSystemResource(resource);
		if (url == null)
			throw new IOException("Could not find resource " + resource);
		return url;
	}

	/** */
	/**
	 * Returns the URL of the resource on the classpath
	 * 
	 * @param loader
	 *            The classloader used to load the resource
	 * @param resource
	 *            The resource to find
	 * @throws IOException
	 *             If the resource cannot be found or read
	 * @return The resource
	 */
	public static URL getResourceURL(ClassLoader loader, String resource) throws IOException {
		URL url = null;
		if (loader != null)
			url = loader.getResource(resource);
		if (url == null)
			url = ClassLoader.getSystemResource(resource);
		if (url == null)
			throw new IOException("Could not find resource " + resource);
		return url;
	}

	/** */
	/**
	 * Returns a resource on the classpath as a Stream object
	 * 
	 * @param resource
	 *            The resource to find
	 * @throws IOException
	 *             If the resource cannot be found or read
	 * @return The resource
	 */
	public static InputStream getResourceAsStream(String resource) throws IOException {
		InputStream in = null;
		ClassLoader loader = ResourceUtils.class.getClassLoader();
		if (loader != null)
			in = loader.getResourceAsStream(resource);
		if (in == null)
			in = ClassLoader.getSystemResourceAsStream(resource);
		if (in == null)
			throw new IOException("Could not find resource " + resource);
		return in;
	}

	/** */
	/**
	 * Returns a resource on the classpath as a Stream object
	 * 
	 * @param loader
	 *            The classloader used to load the resource
	 * @param resource
	 *            The resource to find
	 * @throws IOException
	 *             If the resource cannot be found or read
	 * @return The resource
	 */
	public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
		InputStream in = null;
		if (loader != null)
			in = loader.getResourceAsStream(resource);
		if (in == null)
			in = ClassLoader.getSystemResourceAsStream(resource);
		if (in == null)
			throw new IOException("Could not find resource " + resource);
		return in;
	}

	/** */
	/**
	 * Returns a resource on the classpath as a Properties object
	 * 
	 * @param resource
	 *            The resource to find
	 * @throws IOException
	 *             If the resource cannot be found or read
	 * @return The resource
	 */
	public static Properties getResourceAsProperties(String resource) throws IOException {
		Properties props = new Properties();
		InputStream in = null;
		String propfile = resource;
		in = getResourceAsStream(propfile);
		props.load(in);
		in.close();
		return props;
	}

	/** */
	/**
	 * Returns a resource on the classpath as a Properties object
	 * 
	 * @param loader
	 *            The classloader used to load the resource
	 * @param resource
	 *            The resource to find
	 * @throws IOException
	 *             If the resource cannot be found or read
	 * @return The resource
	 */
	public static Properties getResourceAsProperties(ClassLoader loader, String resource) throws IOException {
		Properties props = new Properties();
		InputStream in = null;
		String propfile = resource;
		in = getResourceAsStream(loader, propfile);
		props.load(in);
		in.close();
		return props;
	}

	/** */
	/**
	 * Returns a resource on the classpath as a Reader object
	 * 
	 * @param resource
	 *            The resource to find
	 * @throws IOException
	 *             If the resource cannot be found or read
	 * @return The resource
	 */
	public static InputStreamReader getResourceAsReader(String resource) throws IOException {
		return new InputStreamReader(getResourceAsStream(resource));
	}

	/** */
	/**
	 * Returns a resource on the classpath as a Reader object
	 * 
	 * @param loader
	 *            The classloader used to load the resource
	 * @param resource
	 *            The resource to find
	 * @throws IOException
	 *             If the resource cannot be found or read
	 * @return The resource
	 */
	public static Reader getResourceAsReader(ClassLoader loader, String resource) throws IOException {
		return new InputStreamReader(getResourceAsStream(loader, resource));
	}

	/** */
	/**
	 * Returns a resource on the classpath as a File object
	 * 
	 * @param resource
	 *            The resource to find
	 * @throws IOException
	 *             If the resource cannot be found or read
	 * @return The resource
	 */
	public static File getResourceAsFile(String resource) throws IOException {
		return new File(getResourceURL(resource).getFile());
	}

	/** */
	/**
	 * Returns a resource on the classpath as a File object
	 * 
	 * @param loader
	 *            The classloader used to load the resource
	 * @param resource
	 *            The resource to find
	 * @throws IOException
	 *             If the resource cannot be found or read
	 * @return The resource
	 */
	public static File getResourceAsFile(ClassLoader loader, String resource) throws IOException {
		return new File(getResourceURL(loader, resource).getFile());
	}

}