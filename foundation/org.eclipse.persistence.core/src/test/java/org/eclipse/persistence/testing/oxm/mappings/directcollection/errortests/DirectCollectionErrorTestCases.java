/*
 * Copyright (c) 1998, 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */

// Contributors:
//     Oracle - initial API and implementation from Oracle TopLink
package org.eclipse.persistence.testing.oxm.mappings.directcollection.errortests;

import java.io.InputStream;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.eclipse.persistence.exceptions.DescriptorException;
import org.eclipse.persistence.exceptions.IntegrityException;
import org.eclipse.persistence.exceptions.XMLMarshalException;
import org.eclipse.persistence.platform.xml.XMLPlatformException;
import org.eclipse.persistence.oxm.XMLContext;
import org.eclipse.persistence.testing.oxm.OXTestCase;

public class DirectCollectionErrorTestCases extends OXTestCase {
    public DirectCollectionErrorTestCases(String name) throws Exception {
        super(name);
    }

    public void testXPathNotSetError() throws Exception {
        try {
            XMLContext xmlMarshaller = getXMLContext(new DirectCollectionErrorProject());
        } catch (IntegrityException e) {
            boolean foundException = false;
            Vector caughtExceptions = e.getIntegrityChecker().getCaughtExceptions();
            for (int i = 0; i < caughtExceptions.size(); i++) {
                Exception nextException = (Exception)caughtExceptions.elementAt(i);
                if (nextException instanceof DescriptorException) {
                    assertTrue("An incorrect Descriptor exception occurred.", ((DescriptorException)nextException).getErrorCode() == DescriptorException.FIELD_NAME_NOT_SET_IN_MAPPING);
                    foundException = true;
                }
            }
            assertTrue("The appropriate Descriptor exception was not encountered", foundException);
            return;
        } catch (XMLMarshalException marshalException) {
            assertTrue("An unexcepted XMLMarshalException occurred", !(metadata == Metadata.JAVA));
            return;
        } catch (Exception e) {
            fail("An unexcepted exception occurred");
            return;
        }
        fail("A Descriptor Exception should have been thrown but wasn't");
    }
}
