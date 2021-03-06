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
package org.apache.webbeans.test.unittests.intercept.webbeans;

import javax.enterprise.inject.spi.Bean;

import junit.framework.Assert;

import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.test.TestContext;
import org.apache.webbeans.test.component.intercept.webbeans.ShoppingCard;
import org.apache.webbeans.test.component.intercept.webbeans.TransactionalInterceptor;
import org.junit.Before;
import org.junit.Test;

public class ShoppingCardInterceptorTest extends TestContext
{
    public ShoppingCardInterceptorTest()
    {
        super(ShoppingCardInterceptorTest.class.getName());
    }
    
    @Override
    @Before
    public void init()
    {
        initDefaultStereoTypes();
        initializeInterceptorType(TransactionalInterceptor.class);
        
        super.init();
        
    }

    @Test
    public void testTransactionalInterceptor()
    {
        WebBeansContext webBeansContext = WebBeansContext.getInstance();
        webBeansContext.getContextFactory().initSessionContext(null);

        // Interceptors must explicitly get enabled via XML. We fake this:
        webBeansContext.getInterceptorsManager().addEnabledInterceptorClass(TransactionalInterceptor.class);
        
        defineInterceptor(TransactionalInterceptor.class);
        
        Bean<ShoppingCard> bean = defineManagedBean(ShoppingCard.class);
        ShoppingCard card = getManager().getInstance(bean);
        
        card.placeOrder();
        
        Assert.assertTrue(ShoppingCard.getCALLED());
        
        card.placeOrder2();
        
        Assert.assertFalse(ShoppingCard.getCALLED());

        webBeansContext.getContextFactory().destroySessionContext(null);
    }
}
