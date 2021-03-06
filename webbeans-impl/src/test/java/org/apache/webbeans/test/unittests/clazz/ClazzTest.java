/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.webbeans.test.unittests.clazz;

import java.lang.reflect.Type;
import java.util.Set;

import junit.framework.Assert;

import org.apache.webbeans.util.GenericsUtil;
import org.junit.Test;

public class ClazzTest
{
    @Test
    public void testStudent()
    {
        Set<Type> set = GenericsUtil.getTypeClosure(Student.class, Student.class, Student.class);

        Assert.assertEquals(5, set.size());

    }

    @Test
    public void testStudent2()
    {
        Set<Type> set = GenericsUtil.getTypeClosure(Student2.class, Student2.class, Student2.class);

        Assert.assertEquals(4, set.size());

    }

}
