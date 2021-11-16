package com.xzq.dynamic.tx;

import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author xzq
 * @Description //TODO
 * @Date 2021/11/16 20:59
 * @Version 1.0.0
 **/
public class ConnectionFactory {

    private static final ThreadLocal<Map<String,ConnectionProxy>> CONNECTION_HOLDER = new ThreadLocal<Map<String,ConnectionProxy>>() {
        @Override
        protected Map<String,ConnectionProxy> initialValue() {
            return new ConcurrentHashMap<>();
        }
    };

    public static void putConnection(String ds,ConnectionProxy connection) {
        Map<String, ConnectionProxy> currentMap = CONNECTION_HOLDER.get();
        if (!currentMap.containsKey(ds)) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        currentMap.put(ds, connection);
    }

    public static ConnectionProxy getConnection(String ds) {
        return CONNECTION_HOLDER.get().get(ds);
    }

    public static void notify(Boolean state) {
        Map<String, ConnectionProxy> currentMap = CONNECTION_HOLDER.get();
        try {
            for (ConnectionProxy value : currentMap.values()) {
                value.notify(state);
            }
        }finally {
            CONNECTION_HOLDER.remove();
        }
    }
}
