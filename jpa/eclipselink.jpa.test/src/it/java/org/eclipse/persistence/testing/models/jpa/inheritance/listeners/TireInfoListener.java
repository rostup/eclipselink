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
package org.eclipse.persistence.testing.models.jpa.inheritance.listeners;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PostPersist;

/**
 * A listener for all TireInfo entities.
 */
public class TireInfoListener extends ListenerSuperclass {
    public static int PRE_PERSIST_COUNT = 0;
    public static int POST_PERSIST_COUNT = 0;

    @Override
    @PrePersist
    public void prePersist(Object tireInfo) {
        PRE_PERSIST_COUNT++;
    }

    @PostPersist
    public void postPersist(Object tireInfo) {
        POST_PERSIST_COUNT++;
    }
}
