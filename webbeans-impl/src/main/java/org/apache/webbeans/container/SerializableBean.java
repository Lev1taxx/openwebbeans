package org.apache.webbeans.container;
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

import org.apache.webbeans.exception.inject.DeploymentException;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.PassivationCapable;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;


/**
 * Wrapper to make all our Beans serializable.
 * This is basically a delegate to an underlying Bean&lt;T&gt;.
 *
 * We use the {@link PassivationCapable#getId()} and
 * {@link javax.enterprise.inject.spi.BeanManager#getPassivationCapableBean(String)}
 * for serialisation and deserialisation.
 *
 * @author <a href="mailto:struberg@yahoo.de">Mark Struberg</a>
 */
public final class SerializableBean<T> implements Bean<T>, PassivationCapable, Serializable {

    /** the delegated bean */
    private Bean<T> bean;

    /**
     * @return the deletaged internal Bean. 
     */
    public Bean<T> getBean()
    {
        return bean;
    }

    public SerializableBean(Bean<T> bean)
    {
        this.bean = bean;
    }

    @Override
    public Set<Type> getTypes()
    {
        return bean.getTypes();
    }

    @Override
    public Set<Annotation> getQualifiers()
    {
        return bean.getQualifiers();
    }

    @Override
    public Class<? extends Annotation> getScope()
    {
        return bean.getScope();
    }

    @Override
    public String getName()
    {
        return bean.getName();
    }

    @Override
    public boolean isNullable()
    {
        return bean.isNullable();
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints()
    {
        return bean.getInjectionPoints();
    }

    @Override
    public Class<?> getBeanClass()
    {
        return bean.getBeanClass();
    }

    @Override
    public Set<Class<? extends Annotation>> getStereotypes()
    {
        return bean.getStereotypes();
    }

    @Override
    public boolean isAlternative()
    {
        return bean.isAlternative();
    }

    @Override
    public T create(CreationalContext<T> tCreationalContext)
    {
        return bean.create(tCreationalContext);
    }

    @Override
    public void destroy(T instance, CreationalContext<T> tCreationalContext)
    {
        bean.destroy(instance, tCreationalContext);
    }

    @Override
    public String getId()
    {
        if (bean instanceof PassivationCapable)
        {
            return ((PassivationCapable)bean).getId();
        }
        return null;
    }

    private synchronized void writeObject(ObjectOutputStream s)
    throws IOException
    {
        String id = getId();
        if (id == null)
        {
            throw new NotSerializableException();
        }
        
        s.writeObject(id);
    }


    private synchronized void readObject(ObjectInputStream s)
    throws IOException, ClassNotFoundException
    {
        String id = (String) s.readObject();
        Bean<T> b = (Bean<T>) BeanManagerImpl.getManager().getPassivationCapableBean(id);
        if (b == null) {
            throw new DeploymentException("cannot deserialize Bean with PassivationCapable id=" + id);
        }
        bean = b;
    }


}