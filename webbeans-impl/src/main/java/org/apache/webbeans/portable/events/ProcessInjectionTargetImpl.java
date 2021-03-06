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
package org.apache.webbeans.portable.events;

import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.portable.InjectionTargetImpl;

import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessInjectionTarget;

/**
 * Implementation of the {@link ProcessInjectionTarget}.
 * 
 * @version $Rev$ $Date$
 *
 * @param <X> bean class info
 */
public class ProcessInjectionTargetImpl<X> implements ProcessInjectionTarget<X>
{
    /**Annotated type instance that is used by container to read meta-data*/
    private final AnnotatedType<X> annotatedType;
    
    /**Injection target that is used by container to inject dependencies*/
    private InjectionTargetImpl<X> injectionTarget = null;

    /**Injection target is set or not*/
    private boolean set = false;
    
    /**
     * Creates a new instance.
     * 
     * @param injectionTarget injection target
     */
    public ProcessInjectionTargetImpl(InjectionTargetImpl<X> injectionTarget, AnnotatedType<X> annotatedType)
    {
        this.injectionTarget = injectionTarget;
        this.annotatedType = annotatedType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addDefinitionError(Throwable t)
    {
        WebBeansContext.getInstance().getBeanManagerImpl().getErrorStack().pushError(t);
    }

    @Override
    public AnnotatedType<X> getAnnotatedType()
    {
        return annotatedType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InjectionTarget<X> getInjectionTarget()
    {
        return injectionTarget.simpleInstance();
    }

    public InjectionTarget<X> getCompleteInjectionTarget()
    {
        return injectionTarget;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInjectionTarget(InjectionTarget<X> injectionTarget)
    {
        this.injectionTarget.setDelegate(injectionTarget); // wrap it to keep interceptors info
        set = true;
    }

    /**
     * Returns whether or not injection target is set or not.
     * 
     * @return whether or not injection target is set
     */
    public boolean isSet()
    {
        return set;
    }
}
