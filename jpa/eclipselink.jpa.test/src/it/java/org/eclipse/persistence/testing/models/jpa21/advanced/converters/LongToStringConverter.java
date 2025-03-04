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
//     10/09/2012-2.5 Guy Pelletier
//       - 374688: JPA 2.1 Converter support
package org.eclipse.persistence.testing.models.jpa21.advanced.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply=true)
public class LongToStringConverter implements AttributeConverter<Long, String> {

    @Override
    public String convertToDatabaseColumn(Long attribute) {
        return (attribute == null) ? null : attribute.toString();
    }

    @Override
    public Long convertToEntityAttribute(String dbData) {
        return (dbData == null) ? null : Long.valueOf(dbData);
    }
}
