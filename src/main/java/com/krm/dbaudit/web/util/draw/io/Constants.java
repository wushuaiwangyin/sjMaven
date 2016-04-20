package com.krm.dbaudit.web.util.draw.io;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Constants
{
	/**
	 * Contains an empty image.
	 */
	public static BufferedImage EMPTY_IMAGE;

	/**
	 * Initializes the empty image.
	 */
	static
	{
		try
		{
			EMPTY_IMAGE = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		}
		catch (Exception e)
		{
			// ignore
		}
	}

	/**
	 * Maximum size (in bytes) for request payloads. Default is 10485760 (10MB).
	 */
	public static final int MAX_REQUEST_SIZE = 10485760;
	


	/**

	/**
	 * Image domains that map to our clipart
	 */
	public static final ArrayList<String> IMAGE_DOMAIN_MATCHES = new ArrayList<String>();
	

	/**
	 * Maximum width for exports. Default is 6000px.
	 */
	public static final int MAX_WIDTH = 6000;

	/**
	 * Maximum height for exports. Default is 6000px.
	 */
	public static final int MAX_HEIGHT = 6000;

	/**
	 * The domain where legacy images are stored.
	 */
	public static final String IMAGE_DOMAIN = "http://localhost/";
}
