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
 package org.eclipse.persistence.testing.tests.jpa.performance.reading;

import java.util.*;
import jakarta.persistence.*;

/**
 * This test compares the performance of read all Project.
 */
public class JPAReadAllLargeProjectPerformanceComparisonTest extends JPAReadPerformanceComparisonTest {

    public JPAReadAllLargeProjectPerformanceComparisonTest(boolean isReadOnly) {
        super(isReadOnly);
        setName("JPAReadAllLargeProjectPerformanceComparisonTest-readonly:" + isReadOnly);
        setDescription("This test compares the performance of read all Project.");
    }

    /**
     * Read all larger project.
     */
    @Override
    public void test() throws Exception {
        EntityManager manager = createEntityManager();
        Query query = manager.createQuery("Select p from LargeProject p");
        List result = list(query, manager);
        result.size();
        manager.close();
    }
}
