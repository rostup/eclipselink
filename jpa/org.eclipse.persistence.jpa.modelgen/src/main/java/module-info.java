/*
 * Copyright (c) 2021 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */


module org.eclipse.persistence.jpa.modelgen {

    requires transitive java.compiler;

    requires jakarta.annotation;

    requires transitive org.eclipse.persistence.jpa;

    exports org.eclipse.persistence.internal.jpa.modelgen;
    exports org.eclipse.persistence.internal.jpa.modelgen.objects;

    provides javax.annotation.processing.Processor with
           org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor;
}
