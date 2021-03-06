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
package org.apache.webbeans.container;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.InterceptionType;
import javax.enterprise.inject.spi.Interceptor;
import javax.enterprise.inject.spi.ObserverMethod;

import org.apache.webbeans.config.WebBeansContext;

/**
 * <p>This implementation of the {@link BeanManager} will get used
 * for whenever a BeanManager gets injected into a bean:
 * <pre>
 *   private @Inject BeanManager beanManager;
 * </pre>
 * </p>
 * This class is Serializable and always resolves the current
 * instance of the central BeanManager automatically.
 */
public class InjectableBeanManager extends AbstractBeanManager implements BeanManager, Serializable, Externalizable 
{

    private static final long serialVersionUID = 1L;
    
    private transient BeanManagerImpl bm;

    /**
     * Used by serialization.
     */
    public InjectableBeanManager()
    {
        this(WebBeansContext.getInstance().getBeanManagerImpl());
    }

    public InjectableBeanManager(BeanManagerImpl beanManager)
    {
        bm = beanManager;
    }

    @Override
    public WebBeansContext getWebBeansContext()
    {
        return bm.getWebBeansContext();
    }

    @Override
    public <T> AnnotatedType<T> createAnnotatedType(Class<T> type)
    {
        return bm.createAnnotatedType(type);
    }

    @Override
    public <T> CreationalContext<T> createCreationalContext(Contextual<T> contextual)
    {
        return bm.createCreationalContext(contextual);
    }

    @Override
    public <T> InjectionTarget<T> createInjectionTarget(AnnotatedType<T> type)
    {
        return bm.createInjectionTarget(type);
    }

    @Override
    public void fireEvent(Object event, Annotation... qualifiers)
    {
        bm.fireEvent(event, qualifiers);
    }

    @Override
    public Set<Bean<?>> getBeans(String name)
    {
        return bm.getBeans(name);
    }

    @Override
    public Set<Bean<?>> getBeans(Type beanType, Annotation... qualifiers)
    {
        return bm.getBeans(beanType, qualifiers);
    }

    @Override
    public Context getContext(Class<? extends Annotation> scope)
    {
        return bm.getContext(scope);
    }

    @Override
    public ELResolver getELResolver()
    {
        return bm.getELResolver();
    }

    @Override
    public Object getInjectableReference(InjectionPoint injectionPoint, CreationalContext<?> ctx)
    {
        return bm.getInjectableReference(injectionPoint, ctx);
    }

    @Override
    public Set<Annotation> getInterceptorBindingDefinition(Class<? extends Annotation> qualifier)
    {
        return bm.getInterceptorBindingDefinition(qualifier);
    }

    @Override
    public Bean<?> getPassivationCapableBean(String id)
    {
        return bm.getPassivationCapableBean(id);
    }

    @Override
    public Object getReference(Bean<?> bean, Type beanType, CreationalContext<?> ctx)
    {
        return bm.getReference(bean, beanType, ctx);
    }

    @Override
    public Set<Annotation> getStereotypeDefinition(Class<? extends Annotation> stereotype)
    {
        return bm.getStereotypeDefinition(stereotype);
    }

    @Override
    public boolean isInterceptorBinding(Class<? extends Annotation> annotationType)
    {
        return bm.isInterceptorBinding(annotationType);
    }

    @Override
    public boolean isNormalScope(Class<? extends Annotation> annotationType)
    {
        return bm.isNormalScope(annotationType);
    }

    @Override
    public boolean isPassivatingScope(Class<? extends Annotation> annotationType)
    {
        return bm.isPassivatingScope(annotationType);
    }

    @Override
    public boolean isQualifier(Class<? extends Annotation> annotationType)
    {
        return bm.isQualifier(annotationType);
    }

    @Override
    public boolean isScope(Class<? extends Annotation> annotationType)
    {
        return bm.isScope(annotationType);
    }

    @Override
    public boolean isStereotype(Class<? extends Annotation> annotationType)
    {
        return bm.isStereotype(annotationType);
    }

    @Override
    public <X> Bean<? extends X> resolve(Set<Bean<? extends X>> beans)
    {
        return bm.resolve(beans);
    }

    @Override
    public List<Decorator<?>> resolveDecorators(Set<Type> types, Annotation... qualifiers)
    {
        return bm.resolveDecorators(types, qualifiers);
    }

    @Override
    public List<Interceptor<?>> resolveInterceptors(InterceptionType type, Annotation... interceptorBindings)
    {
        return bm.resolveInterceptors(type, interceptorBindings);
    }

    @Override
    public <T> Set<ObserverMethod<? super T>> resolveObserverMethods(T event, Annotation... qualifiers)
    {
        return bm.resolveObserverMethods(event, qualifiers);
    }

    @Override
    public void validate(InjectionPoint injectionPoint)
    {
        bm.validate(injectionPoint);
    }

    @Override
    public ExpressionFactory wrapExpressionFactory(ExpressionFactory expressionFactory)
    {
        return bm.wrapExpressionFactory(expressionFactory);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {    
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException 
    {
        //static lookup required for bean manager
        bm = WebBeansContext.currentInstance().getBeanManagerImpl();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bm == null) ? 0 : System.identityHashCode(bm));
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;   
        }
        if (obj == null)
        {
            return false;   
        }
        if (getClass() != obj.getClass())
        {
            return false;   
        }
        
        InjectableBeanManager other = (InjectableBeanManager) obj;
        if (bm == null)
        {
            if (other.bm != null)
            {
                return false;   
            }
        }
        else if (System.identityHashCode(bm) != (System.identityHashCode(other.bm)))
        {
            return false;   
        }
        
        return true;
    }
    
    

}
