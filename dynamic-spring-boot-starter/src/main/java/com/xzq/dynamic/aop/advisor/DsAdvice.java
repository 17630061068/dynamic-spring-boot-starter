package com.xzq.dynamic.aop.advisor;

import com.xzq.dynamic.annotation.Ds;
import com.xzq.dynamic.aop.DsAspect;
import com.xzq.dynamic.core.DataSourceContextHolder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

public class DsAdvice implements MethodInterceptor {

    private Logger logger = LoggerFactory.getLogger(DsAspect.class);

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Ds ds = findDsKey(invocation);
        if (ds == null) {
            return invocation.proceed();
        }
        String value = ds.value();
        if (StringUtils.hasLength(value)) {
            logger.info("拦截目标方法{},设置数据库路由Key: {}", invocation.getMethod().getName(), value);
            DataSourceContextHolder.push(value);
        }
        try {
            return invocation.proceed();
        } finally {
            DataSourceContextHolder.poll();
        }
    }


    private Ds findDsKey(MethodInvocation mi) {
        //获取目标对象
        Class<?> target = mi.getThis().getClass();
        //获取目标对象所有接口
        Class<?>[] interfaces = target.getInterfaces();
        //获取方法对象
        Method method = mi.getMethod();
        //Method优先级 > 类优先级 > 接口优先级
        Ds ds = null;
        if ((ds = getDs(method)) == null &&
                (ds = getDs(new Class[]{target})) == null &&
                (ds = getDs(interfaces)) == null) {
            return null;
        }
        return ds;
    }
    private Ds getDs(Class<?>[] targets) {
        for (Class<?> target : targets) {
            Ds ds = target.getAnnotation(Ds.class);
            if (ds != null) {
                return ds;
            }
        }
        return null;
    }

    private Ds getDs(Method method) {
        return method.getAnnotation(Ds.class);
    }
}
