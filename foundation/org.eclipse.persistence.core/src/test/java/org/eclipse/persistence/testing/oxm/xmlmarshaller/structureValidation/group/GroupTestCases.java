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
package org.eclipse.persistence.testing.oxm.xmlmarshaller.structureValidation.group;

import java.util.Calendar;
import org.eclipse.persistence.oxm.*;
import org.eclipse.persistence.testing.oxm.OXTestCase;
import org.eclipse.persistence.testing.oxm.xmlmarshaller.structureValidation.Period;

public class GroupTestCases extends OXTestCase {
    private final static int START_YYYY = 1970;
    private final static int START_MM = 0;
    private final static int START_DD = 1;
    private final static int END_YYYY = 2004;
    private final static int END_MM = 0;
    private final static int END_DD = 1;
    private XMLContext xmlContext;
    private XMLValidator xmlValidator;
    private Calendar start;
    private Calendar end;
    private Employee employee;
    private Period period;

    public GroupTestCases(String name) throws Exception {
        super(name);
    }

    @Override
    public void setUp() throws Exception {
        xmlContext = new XMLContext(new EmployeeProject());
        xmlValidator = xmlContext.createValidator();

        start = Calendar.getInstance();
        start.clear();
        start.set(START_YYYY, START_MM, START_DD);

        end = Calendar.getInstance();
        end.clear();
        end.set(END_YYYY, END_MM, END_DD);
    }

    public void testValidEmployee() throws Exception {
        period = new Period();
        period.setStartDate(start);
        period.setEndDate(end);

        employee = new Employee();
        employee.setG1(period);

        assertTrue("Valid employee reported invalid", xmlValidator.validateRoot(employee));
    }

    /**
     * We are dealing with a sequence for the startDate and endDate, hence both
     * are required to be valid.
     */
    public void testInvalidEmployee() throws Exception {
        period = new Period();
        period.setStartDate(start);

        employee = new Employee();
        employee.setG1(period);

        assertFalse("Invalid employee found to be valid", xmlValidator.validateRoot(employee));
    }
}
