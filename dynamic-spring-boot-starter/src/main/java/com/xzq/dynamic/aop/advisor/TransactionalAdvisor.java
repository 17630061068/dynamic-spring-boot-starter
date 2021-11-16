package com.xzq.dynamic.aop.advisor;

import com.xzq.dynamic.annotation.Ds;
import com.xzq.dynamic.annotation.Tx;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
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

/**
 * @Author xzq
 * @Description //TODO
 * @Date 2021/11/16 21:19
 * @Version 1.0.0
 **/
public class TransactionalAdvisor extends AbstractPointcutAdvisor {

//    @Override
//    public int getOrder() {
//        return Integer.MAX_VALUE - 2;
//    }

    private Pointcut pointcut;
    private Advice advice;

    public TransactionalAdvisor( Advice advice) {
        this.pointcut = builderPointCut();
        this.advice = advice;
    }

    private Pointcut builderPointCut() {
        //spring提供的注解切点实现，里面的方法匹配器对方法一路放行，不需要在经过切入点表达式进行匹配
        AnnotationMatchingPointcut cpc = new AnnotationMatchingPointcut(Tx.class, true);
        Pointcut mpc = new AnnotationMethodPoint(Tx.class);
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
            return new AnnotationMethodPoint.AnnotationMethodMatcher(annotationType);
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
