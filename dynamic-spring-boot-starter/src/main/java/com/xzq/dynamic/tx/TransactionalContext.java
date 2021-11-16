package com.xzq.dynamic.tx;

import org.springframework.util.StringUtils;

/**
 * @Author xzq
 * @Description //TODO
 * @Date 2021/11/16 21:06
 * @Version 1.0.0
 **/
public class TransactionalContext {
    private static final ThreadLocal<String> XID_HOLDER = new ThreadLocal<>();


    public static String getXID() {
        String xid = XID_HOLDER.get();
        if (!StringUtils.isEmpty(xid)) {
            return xid;
        }
        return null;
    }

    public static String bind(String xid) {
        XID_HOLDER.set(xid);
        return xid;
    }

    public static void remove() {
        XID_HOLDER.remove();
    }

}
