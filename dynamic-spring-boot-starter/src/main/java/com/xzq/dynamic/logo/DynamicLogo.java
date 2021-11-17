package com.xzq.dynamic.logo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.logging.LoggingApplicationListener;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * the xzq logo.
 */
@Order(LoggingApplicationListener.DEFAULT_ORDER + 1)
@Slf4j
public class DynamicLogo implements ApplicationListener<ApplicationStartedEvent> {

   private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String VERSION = "1.0.2-SNAPSHOT";
    private static final String DYNAMIC_LOGO ="______                             _       ______      _        _____                          \n" +
            "|  _  \\                           (_)      |  _  \\    | |      /  ___|                         \n" +
            "| | | |_   _ _ __   __ _ _ __ ___  _  ___  | | | |__ _| |_ __ _\\ `--.  ___  _   _ _ __ ___ ___ \n" +
            "| | | | | | | '_ \\ / _` | '_ ` _ \\| |/ __| | | | / _` | __/ _` |`--. \\/ _ \\| | | | '__/ __/ _ \\\n" +
            "| |/ /| |_| | | | | (_| | | | | | | | (__  | |/ | (_| | || (_| /\\__/ | (_) | |_| | | | (_|  __/\n" +
            "|___/  \\__, |_| |_|\\__,_|_| |_| |_|_|\\___| |___/ \\__,_|\\__\\__,_\\____/ \\___/ \\__,_|_|  \\___\\___|\n" +
            "        __/ |                                                                                  \n" +
            "       |___/                                                                                   ";

    private final AtomicBoolean alreadyLog = new AtomicBoolean(false);

    private String buildBannerText() {
        return LINE_SEPARATOR
                + LINE_SEPARATOR
                + DYNAMIC_LOGO
                + LINE_SEPARATOR
                + " :: Xzq Dynamic DataSource API "+VERSION+" \n";
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        if (!alreadyLog.compareAndSet(false, true)) {
            return;
        }
        log.info(buildBannerText());
    }
}
