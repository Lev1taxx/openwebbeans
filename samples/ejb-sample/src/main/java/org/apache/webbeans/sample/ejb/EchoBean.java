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
package org.apache.webbeans.sample.ejb;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;

@Stateless
@Dependent
public class EchoBean implements Echo
{
    @Override
    @LogInterceptorBinding
    public String echo(String name)
    {
        return "Hello " + name; 
    }    

    @PostConstruct
    public void postConstruct()
    {
        System.out.println("Post construct call in " + EchoBean.class.getName() + " via EJB interceptor");
    }
    
    @PreDestroy
    public void preDestroy()
    {
        System.out.println("PreDestroy construct call in " + EchoBean.class.getName() + " via EJB interceptor");
    }
}
