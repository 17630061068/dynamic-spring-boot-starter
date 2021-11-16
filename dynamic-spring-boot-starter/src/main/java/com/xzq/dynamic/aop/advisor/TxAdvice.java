package com.xzq.dynamic.aop.advisor;

import com.xzq.dynamic.annotation.Ds;
import com.xzq.dynamic.annotation.Tx;
import com.xzq.dynamic.tx.ConnectionFactory;
import com.xzq.dynamic.tx.TransactionalContext;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @Author xzq
 * @Description //TODO
 * @Date 2021/11/16 21:18
 * @Version 1.0.0
 **/
public class TxAdvice implements MethodInterceptor {

    private Logger logger = LoggerFactory.getLogger(TxAdvice.class);
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (!StringUtils.isEmpty(TransactionalContext.getXID())) {
            return invocation.proceed();
        }
        logger.info("拦截目标方法，Begin 全局事务控制....");
        boolean state = true;
        String xid = UUID.randomUUID().toString();
        TransactionalContext.bind(xid);
        Object result = null;
        try {
            result=invocation.proceed();
        } catch (Exception e) {
            state = false;
            throw e;
        }finally {
            ConnectionFactory.notify(state);
            TransactionalContext.remove();
        }
        return result;
    }

}
