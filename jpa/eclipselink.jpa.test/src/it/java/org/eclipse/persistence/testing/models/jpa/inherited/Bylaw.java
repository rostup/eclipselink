/*
 * Copyright (c) 2010, 2021 Oracle and/or its affiliates. All rights reserved.
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
//     06/18/2010-2.2 Guy Pelletier
//       - 300458: EclispeLink should throw a more specific exception than NPE
package org.eclipse.persistence.testing.models.jpa.inherited;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Bylaw implements CityNumberPair {
    public String city;
    public int number;

    @Override
    @Id
    public String getCity() {
        return city;
    }

    @Override
    @Id
    @Column(name="NUMB")
    @GeneratedValue
    public int getNumber() {
        return number;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public void setNumber(int number) {
        this.number = number;
    }

}
