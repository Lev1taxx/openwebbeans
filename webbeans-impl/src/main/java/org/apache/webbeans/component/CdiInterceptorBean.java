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
package org.apache.webbeans.component;

import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.Interceptor;
import java.lang.annotation.Annotation;
import java.util.Set;

import org.apache.webbeans.config.WebBeansContext;

/**
 * <p>{@link Interceptor} Bean implementation for CDI-style Beans.
 * This is Interceptors which got defined using
 * &#064;{@link javax.interceptor.InterceptorBinding}.</p>
 */
public class CdiInterceptorBean<T> extends InterceptorBean<T> implements Interceptor<T>
{
    /**
     *
     * @param returnType the return Type of the Bean
     * @param annotatedType AnnotatedType will be returned by some methods in the SPI
     * @param webBeansContext
     * @param interceptorBindings the &#064;{@link javax.interceptor.InterceptorBinding} annotations handled by this Interceptor
     * @param intercepts the InterceptionTypes this Bean handles on the intercepted target
     */
    public CdiInterceptorBean(Class<T> returnType, AnnotatedType<T> annotatedType,
                              WebBeansContext webBeansContext,
                              Set<Annotation> interceptorBindings,
                              Set<InterceptionType> intercepts)
    {
        super(returnType, annotatedType, webBeansContext, intercepts);
        this.interceptorBindings = interceptorBindings;
    }


    private Set<Annotation> interceptorBindings;


    @Override
    public Set<Annotation> getInterceptorBindings()
    {
        return interceptorBindings;
    }


}
