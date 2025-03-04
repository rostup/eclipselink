/*
 * Copyright (c) 2012, 2021 Oracle and/or its affiliates. All rights reserved.
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
//     tware - initial contribution for Bug 366748 - JPA 2.1 Injectable Entity Listeners
package org.eclipse.persistence.testing.models.jpa21.sessionbean;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;

import org.eclipse.persistence.jpa.JpaEntityManagerFactory;
import org.eclipse.persistence.testing.models.jpa21.entitylistener.EntityListener;
import org.eclipse.persistence.testing.models.jpa21.entitylistener.EntityListenerHolder;

@Stateless
public class EntityListenerTestBean implements EntityListenerTest {

    @PersistenceUnit(name="jpa21-sessionbean")
    private EntityManagerFactory emf;

    @Override
    public boolean triggerInjection(){
        EntityListener.INJECTED_RETURN_VALUE = false;
        EntityListener.POST_CONSTRUCT_CALLS = 0;
        EntityManager em = emf.createEntityManager();
        EntityListenerHolder holder = new EntityListenerHolder();
        em.persist(holder);
        return EntityListener.INJECTED_RETURN_VALUE && EntityListener.POST_CONSTRUCT_CALLS == 1;
    }

    @Override
    public boolean triggerPreDestroy(){
        EntityListener.PRE_DESTROY_CALLS = 0;
        emf.unwrap(JpaEntityManagerFactory.class).unwrap().close();
        return EntityListener.PRE_DESTROY_CALLS == 1;
    }
}
