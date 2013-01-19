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
package org.apache.webbeans.ee.beans;

import javax.inject.Provider;
import javax.transaction.UserTransaction;

import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.TransactionService;

public class UserTransactionProvider implements Provider<UserTransaction>
{

    private WebBeansContext webBeansContext;
    
    public UserTransactionProvider(WebBeansContext webBeansContext)
    {
        this.webBeansContext = webBeansContext;
    }

    @Override
    public UserTransaction get()
    {
        TransactionService transactionService = webBeansContext.getService(TransactionService.class);
        if(transactionService != null)
        {
            return transactionService.getUserTransaction();
        }
        return null;
    }
}