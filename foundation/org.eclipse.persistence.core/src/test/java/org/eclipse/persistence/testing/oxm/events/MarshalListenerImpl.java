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
package org.eclipse.persistence.testing.oxm.events;

import java.util.ArrayList;

import org.eclipse.persistence.oxm.*;

/**
 *  @version $Header: MarshalListenerImpl.java 17-may-2006.14:47:42 mmacivor Exp $
 *  @author  mmacivor
 *  @since   release specific (what release of product did this appear in)
 */

public class MarshalListenerImpl implements XMLMarshalListener {
    static Integer EMPLOYEE_BEFORE_MARSHAL = 0;
    static Integer ADDRESS_BEFORE_MARSHAL = 1;
    static Integer PHONE_BEFORE_MARSHAL = 2;
    static Integer EMPLOYEE_AFTER_MARSHAL = 3;
    static Integer ADDRESS_AFTER_MARSHAL = 4;
    static Integer PHONE_AFTER_MARSHAL = 5;

    public ArrayList events = null;

    public MarshalListenerImpl() {
        events = new ArrayList();
    }
    @Override
    public void beforeMarshal(Object obj) {
        if(obj instanceof Employee) {
            events.add(EMPLOYEE_BEFORE_MARSHAL);
        } else if(obj instanceof Address) {
            events.add(ADDRESS_BEFORE_MARSHAL);
        } else if(obj instanceof PhoneNumber) {
            events.add(PHONE_BEFORE_MARSHAL);
        }
    }
    @Override
    public void afterMarshal(Object obj) {
        if(obj instanceof Employee) {
            events.add(EMPLOYEE_AFTER_MARSHAL);
        } else if(obj instanceof Address) {
            events.add(ADDRESS_AFTER_MARSHAL);
        } else if(obj instanceof PhoneNumber) {
            events.add(PHONE_AFTER_MARSHAL);
        }
    }
}
