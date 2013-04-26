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
package org.apache.webbeans.component.third;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.PassivationCapable;
import javax.enterprise.inject.spi.Producer;

import org.apache.webbeans.component.AbstractOwbBean;
import org.apache.webbeans.component.BeanAttributesImpl;
import org.apache.webbeans.component.WebBeansType;
import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.inject.AlternativesManager;

public class ThirdpartyBeanImpl<T> extends AbstractOwbBean<T> implements Bean<T>
{
    private Bean<T> bean = null;
    
    public ThirdpartyBeanImpl(WebBeansContext webBeansContext, Bean<T> bean)
    {
        super(webBeansContext,
              WebBeansType.THIRDPARTY,
              new BeanAttributesImpl<T>(bean),
              bean.getBeanClass(),
              bean.isNullable());
        
        this.bean = bean;
        
    }

    @Override
    public Producer<T> getProducer()
    {
        return new Producer<T>()
        {
            @Override
            public T produce(CreationalContext<T> creationalContext)
            {
                return bean.create(creationalContext);
            }

            @Override
            public void dispose(T instance)
            {
                bean.destroy(instance, null);
            }

            @Override
            public Set<InjectionPoint> getInjectionPoints()
            {
                return bean.getInjectionPoints();
            }
        };
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints()
    {
        return bean.getInjectionPoints();
    }

    public T create(CreationalContext<T> context)
    {
        
        return bean.create(context);
    }

    public void destroy(T instance, CreationalContext<T> context)
    {
        bean.destroy(instance,context);
        
    }

    /* (non-Javadoc)
     * @see org.apache.webbeans.component.AbstractBean#getId()
     */
    @Override
    public String getId()
    {
        if(bean instanceof PassivationCapable)
        {
            PassivationCapable pc = (PassivationCapable) bean;
            return pc.getId();
        }
        
        return null;
    }
    
    

    @Override
    public boolean isPassivationCapable()
    {
        return bean instanceof PassivationCapable;
    }

    @Override
    public Class<?> getBeanClass()
    {
        return bean.getBeanClass();
    }

    @Override
    public boolean isAlternative()
    {
        boolean alternative = super.isAlternative();
        if(alternative)
        {
            AlternativesManager manager = getWebBeansContext().getAlternativesManager();
            //Class alternative
            if(manager.isClassAlternative(getBeanClass()))
            {
                return true;
            }
            
            Set<Class<? extends Annotation>> stereoTypes = bean.getStereotypes();
            if(stereoTypes != null)
            {
                for(Class<? extends Annotation> stereo : stereoTypes)
                {
                    if(manager.isStereoAlternative(stereo))
                    {
                        return true;
                    }
                }
            }
        }
        
        return false;
    }

    /**
     * We need to override the hash code from the AbstractOwbBean
     * and delegate to the shaded instance.
     *
     * @return the hash mixed with the shadowed bean.
     */
    @Override
    public int hashCode()
    {
        return 29 * bean.hashCode();
    }

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof ThirdpartyBeanImpl)
        {
            return ((ThirdpartyBeanImpl) other).bean.equals(bean);
        }

        return bean.equals(other);
    }
}
