/**
 * Copyright (c) 2018-2020. Andrew D. King. All Rights Reserved.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.labbenchstudios.iot.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.net.ssl.SSLSocketFactory;

import org.junit.Before;
import org.junit.Test;

import com.labbenchstudios.iot.common.CertManagementUtil;

/**
 * Simple test class for
 * {@link com.labbenchstudios.iot.common.CertManagementUtil}.
 * 
 * @author aking
 */
public class CertManagementUtilTest
{
	// static
	
	/**
	 * NOTE: All file names listed are examples only and can be used as-is, or changed based
	 * on your testing needs. They are simply place holders.
	 */
	public static final String DIR_PREFIX = "./certs/";
	
	public static final String TEST_VALID_CERT_FILE    = DIR_PREFIX + "test_cert_validA.pem";
	public static final String TEST_INVALID_CERT_FILEA = DIR_PREFIX + "test_cert_emptyA.pem";
	public static final String TEST_INVALID_CERT_FILEB = DIR_PREFIX + "test_cert_emptyB.pem";
	public static final String TEST_INVALID_CERT_FILEC = DIR_PREFIX + "test_cert_emptyC.pem";
	
	// setup methods
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		// make sure test files exist - uncomment this section if file existence validation is required.
		/*
		File validTestFileA   = new File(TEST_VALID_CERT_FILE);
		File invalidTestFileA = new File(TEST_INVALID_CERT_FILEA);
		File invalidTestFileB = new File(TEST_INVALID_CERT_FILEB);
		File invalidTestFileC = new File(TEST_INVALID_CERT_FILEC);
		
		assertTrue(validTestFileA.exists());
		assertTrue(invalidTestFileA.exists());
		assertTrue(invalidTestFileB.exists());
		assertTrue(invalidTestFileC.exists());
		*/
	}
	
	// test methods
	
	/**
	 * Tests {@link CertManagementUtil} with a certificate file
	 * containing one or more certificates.
	 * <p>
	 * NOTE: To include in JUnit tests, uncomment the @Test annotation.
	 * 
	 */
//	@Test
	public void testImportOfCertFromValidFile()
	{
		CertManagementUtil certMgr = CertManagementUtil.getInstance();
		SSLSocketFactory   factory = certMgr.loadCertificate(TEST_VALID_CERT_FILE);
		
		org.junit.Assert.assertNotNull(factory);
	}
	
	/**
	 * Tests {@link CertManagementUtil} with a null certificate file.
	 * 
	 */
	@Test
	public void testImportOfCertFromNullFile()
	{
		CertManagementUtil certMgr = CertManagementUtil.getInstance();
		SSLSocketFactory   factory = certMgr.loadCertificate(null);
		
		org.junit.Assert.assertNull(factory);
	}
	
	/**
	 * Tests {@link CertManagementUtil} with an invalid certificate file
	 * containing only the BEGIN / END entries.
	 * 
	 */
	@Test
	public void testImportOfCertFromEmptyFileA()
	{
		CertManagementUtil certMgr = CertManagementUtil.getInstance();
		SSLSocketFactory   factory = certMgr.loadCertificate(TEST_INVALID_CERT_FILEA);
		
		org.junit.Assert.assertNull(factory);
	}
	
	/**
	 * Tests {@link CertManagementUtil} with an invalid certificate file
	 * containing invalid text.
	 * 
	 */
	@Test
	public void testImportOfCertFromEmptyFileB()
	{
		CertManagementUtil certMgr = CertManagementUtil.getInstance();
		SSLSocketFactory   factory = certMgr.loadCertificate(TEST_INVALID_CERT_FILEB);
		
		org.junit.Assert.assertNull(factory);
	}
	
	/**
	 * Tests {@link CertManagementUtil} with an empty certificate file.
	 * 
	 */
	@Test
	public void testImportOfCertFromEmptyFileC()
	{
		CertManagementUtil certMgr = CertManagementUtil.getInstance();
		SSLSocketFactory   factory = certMgr.loadCertificate(TEST_INVALID_CERT_FILEC);
		
		org.junit.Assert.assertNull(factory);
	}
	
}
