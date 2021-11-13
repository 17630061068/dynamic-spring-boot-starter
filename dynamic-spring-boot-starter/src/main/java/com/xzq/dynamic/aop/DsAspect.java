package com.xzq.dynamic.aop;

import com.xzq.dynamic.annotation.Ds;
import com.xzq.dynamic.core.DataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;

@Aspect
@Order(0)
public class DsAspect {

    private Logger logger = LoggerFactory.getLogger(DsAspect.class);

    @Pointcut("@annotation(com.xzq.dynamic.annotation.Ds)")
    public void aopPoint() {
    }

    @Around("aopPoint()")
    public Object dbRouter(ProceedingJoinPoint pjp) throws Throwable {
        Ds ds = findDsKey(pjp);
        if (ds == null) {
            return pjp.proceed();
        }
        String value = ds.value();
        if (StringUtils.hasLength(value)) {
            logger.info("拦截目标方法{},设置数据库路由Key: {}", ((MethodSignature) pjp.getSignature()).getMethod().getName(), value);
            DataSourceContextHolder.push(value);
        }
        try {
            return pjp.proceed();
        } finally {
            DataSourceContextHolder.poll();
        }
    }

    private Ds findDsKey(ProceedingJoinPoint pjp) {
        //获取目标对象
        Class<?> target = pjp.getTarget().getClass();
        //获取目标对象所有接口
        Class<?>[] interfaces = target.getInterfaces();
        //获取方法对象
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
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
