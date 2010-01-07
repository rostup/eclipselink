/*******************************************************************************
 * Copyright (c) 1998, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation from Oracle TopLink
 ******************************************************************************/  
package org.eclipse.persistence.testing.tests.queries;

import java.util.*;

/**
 * TopLink generated Project class.
 * <b>WARNING</b>: This code was generated by an automated tool.
 * Any changes will be lost when the code is re-generated
 */
public class NoIdentityMapProject extends org.eclipse.persistence.sessions.Project {

    /**
     * <b>WARNING</b>: This code was generated by an automated tool.
     * Any changes will be lost when the code is re-generated
     */
    public NoIdentityMapProject() {
        applyPROJECT();
        applyLOGIN();
        buildTestClass1Descriptor();
    }

    /**
     * TopLink generated method.
     * <b>WARNING</b>: This code was generated by an automated tool.
     * Any changes will be lost when the code is re-generated
     */
    protected void applyLOGIN() {
    }

    /**
     * TopLink generated method.
     * <b>WARNING</b>: This code was generated by an automated tool.
     * Any changes will be lost when the code is re-generated
     */
    protected void applyPROJECT() {
        setName("TestIdentity");
    }

    /**
     * TopLink generated method.
     * <b>WARNING</b>: This code was generated by an automated tool.
     * Any changes will be lost when the code is re-generated
     */
    protected void buildTestClass1Descriptor() {
        org.eclipse.persistence.descriptors.RelationalDescriptor descriptor = new org.eclipse.persistence.descriptors.RelationalDescriptor();

        // SECTION: DESCRIPTOR
        descriptor.setJavaClass(org.eclipse.persistence.testing.tests.queries.TestClass1.class);
        Vector vector = new Vector();
        vector.addElement("TESTTABLE1");
        descriptor.setTableNames(vector);
        descriptor.addPrimaryKeyFieldName("TESTTABLE1.PKEY");

        // SECTION: PROPERTIES
        descriptor.setIdentityMapClass(org.eclipse.persistence.internal.identitymaps.NoIdentityMap.class);
        descriptor.useNoIdentityMap();
        descriptor.setSequenceNumberName("IDENTITY_SEQ");
        descriptor.setSequenceNumberFieldName("PKEY");
        descriptor.setExistenceChecking("Assume existence");

        // SECTION: COPY POLICY
        descriptor.createCopyPolicy("constructor");

        // SECTION: INSTANTIATION POLICY
        descriptor.createInstantiationPolicy("constructor");

        // SECTION: DIRECTTOFIELDMAPPING
        org.eclipse.persistence.mappings.DirectToFieldMapping directtofieldmapping = new org.eclipse.persistence.mappings.DirectToFieldMapping();
        directtofieldmapping.setAttributeName("pkey");
        directtofieldmapping.setIsReadOnly(false);
        directtofieldmapping.setGetMethodName("getPkey");
        directtofieldmapping.setSetMethodName("setPkey");
        directtofieldmapping.setFieldName("TESTTABLE1.PKEY");
        descriptor.addMapping(directtofieldmapping);

        // SECTION: DIRECTTOFIELDMAPPING
        org.eclipse.persistence.mappings.DirectToFieldMapping directtofieldmapping1 = new org.eclipse.persistence.mappings.DirectToFieldMapping();
        directtofieldmapping1.setAttributeName("test1");
        directtofieldmapping1.setIsReadOnly(false);
        directtofieldmapping1.setGetMethodName("getTest1");
        directtofieldmapping1.setSetMethodName("setTest1");
        directtofieldmapping1.setFieldName("TESTTABLE1.FIRST_");
        descriptor.addMapping(directtofieldmapping1);

        // SECTION: DIRECTTOFIELDMAPPING
        org.eclipse.persistence.mappings.DirectToFieldMapping directtofieldmapping2 = new org.eclipse.persistence.mappings.DirectToFieldMapping();
        directtofieldmapping2.setAttributeName("test2");
        directtofieldmapping2.setIsReadOnly(false);
        directtofieldmapping2.setGetMethodName("getTest2");
        directtofieldmapping2.setSetMethodName("setTest2");
        directtofieldmapping2.setFieldName("TESTTABLE1.SECOND_");
        descriptor.addMapping(directtofieldmapping2);
        addDescriptor(descriptor);
    }
}
