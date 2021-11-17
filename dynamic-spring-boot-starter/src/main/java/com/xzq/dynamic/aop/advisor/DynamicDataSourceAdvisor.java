package com.xzq.dynamic.aop.advisor;

import com.xzq.dynamic.annotation.Ds;
import org.aopalliance.aop.Advice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.StaticMethodMatcher;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicDataSourceAdvisor extends AbstractPointcutAdvisor {
    private Pointcut pointcut;
    private Advice advice;

    @Override
    public int getOrder() {
        return Integer.MAX_VALUE-1;
    }

    public DynamicDataSourceAdvisor(Advice advice) {
        this.advice = advice;
        this.pointcut = builderPointCut();
    }

    /**
     * 该注解可能作用在类也可能作用在方法中
     *  传统的默认AspectJExpressionPointCut只是针对方法的
     *      所以我们需要考虑两种情况
     *      1，类匹配上了，方法全部匹配
     *      2，类未匹配，方法匹配（但是这里需要注意,在判断的时候，是先匹配类在匹配方法，如果类都没有匹配上那么方法匹配都没有机会执行）
     *     所以需要配合一个联合切入点：
     *                  1，一个是当类匹配上时，方法直接匹配True
     *                  2,当类未匹配上，根据联合切点，执行or 操作，另一个切点将类匹配完全放开类匹配，只检查方法匹配
     * @return
     */
    private Pointcut builderPointCut() {
        //spring提供的注解切点实现，里面的方法匹配器对方法一路放行，不需要在经过切入点表达式进行匹配
        AnnotationMatchingPointcut cpc = new AnnotationMatchingPointcut(Ds.class, true);
        Pointcut mpc = new AnnotationMethodPoint(Ds.class);
        return new ComposablePointcut(cpc).union(mpc);
    }



    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }



    /**
     * In order to be compatible with the spring lower than 5.0
     */
    private static class AnnotationMethodPoint implements Pointcut {

        private final Class<? extends Annotation> annotationType;

        public AnnotationMethodPoint(Class<? extends Annotation> annotationType) {
            Assert.notNull(annotationType, "Annotation type must not be null");
            this.annotationType = annotationType;
        }

        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new AnnotationMethodMatcher(annotationType);
        }

        private static class AnnotationMethodMatcher extends StaticMethodMatcher {
            private final Class<? extends Annotation> annotationType;

            public AnnotationMethodMatcher(Class<? extends Annotation> annotationType) {
                this.annotationType = annotationType;
            }

            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                if (matchesMethod(method)) {
                    return true;
                }
                // Proxy classes never have annotations on their redeclared methods.
                if (Proxy.isProxyClass(targetClass)) {
                    return false;
                }
                // The method may be on an interface, so let's check on the target class as well.
                Method specificMethod = AopUtils.getMostSpecificMethod(method, targetClass);
                return (specificMethod != method && matchesMethod(specificMethod));
            }

            private boolean matchesMethod(Method method) {
                return AnnotatedElementUtils.hasAnnotation(method, this.annotationType);
            }
        }
    }
}
