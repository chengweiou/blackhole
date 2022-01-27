package chengweiou.universe.blackhole.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LogUtil {
    private static final Logger logger = LogManager.getLogger();
    private static List<String> keepList = new ArrayList<>();

    /**
     * set up keep list, will only print package stack start from list.
     * please set up inorder to get a clean log
     * @param keepList
     */
    public static void init(String... keepList) {
        LogUtil.keepList = Arrays.asList(keepList);
    }
    /**
     * set up keep list, will only print package stack start from list.
     * please set up inorder to get a clean log
     * @param keepList
     */
    public static void init(List<String> keepList) {
        LogUtil.keepList = keepList;
    }

    public static void d(String name) {
        logger.debug(name);
    }
    public static void d(String name, String content) {
        logger.debug(name, content);
    }
    public static void d(String name, Object obj) {
        logger.debug(name, obj.toString());
    }
    public static void d(String name, Throwable throwable) {
        cleanStack(throwable);
        logger.debug(name, throwable);
    }

    public static void i(String name) {
        logger.info(name);
    }
    public static void i(String name, String content) {
        logger.info(name, content);
    }
    public static void i(String name, Object obj) {
        logger.info(name, obj.toString());
    }
    public static void i(String name, Throwable throwable) {
        cleanStack(throwable);
        logger.info(name, throwable);
    }

    public static void e(String name) {
        logger.error(name);
    }

    public static void e(String name, Throwable throwable) {
        cleanStack(throwable);
        logger.error(name, throwable);
    }


    private static void cleanStack(Throwable throwable) {
        // todo 还没处理多个包时跳包的省略号...
        StackTraceElement[] array = throwable.getStackTrace();
        if (keepList.isEmpty()) {
            return;
        }
        List<StackTraceElement> list = Arrays.asList(array).stream().filter(e -> keepList.stream().anyMatch(keep -> e.getClassName().startsWith(keep))).toList();
        throwable.setStackTrace(list.toArray(new StackTraceElement[list.size()]));
    }
}
