package cn.soft.common.util;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 以静态变量保存Spring ApplicationContext 可以在任何代码任何地方任何时候取出ApplicationContext
 */
@Slf4j
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContext接口的context注入函数  将其存入静态变量
     * @param applicationContext 上下文
     * @throws BeansException /
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 取得存储在静态变量中的applicationContext
     * @return /
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * 从静态变量ApplicationContext中取得bean 自动转换为所赋值对象的类型
     * @param name bean的名字
     * @param <T> 类型
     * @return 返回获取到的bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T)applicationContext.getBean(name);
    }

    /**
     * 从静态变量ApplicationContext中取得bean 自动转换为所赋值对象的类型
     * @param clazz 类型
     * @param <T> 类型
     * @return 返回获取到的bean
     */
    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return applicationContext.getBean(clazz);
    }

    /**
     * 获取bean
     * @param name 名字
     * @param tClass 类
     * @param <T> 类型
     * @return /
     */
    public static <T> T getHandler(String name, Class<T> tClass) {
        T t = null;
        if (ObjectUtil.isNotEmpty(name)) {
            checkApplicationContext();
            try {
                t = applicationContext.getBean(name, tClass);
            } catch (Exception e){
                log.error("############" + name + "未定义");
            }
        }
        return t;
    }

    /**
     * 清除applicationContext静态变量
     */
    public static void cleanApplicationContext() {
        applicationContext = null;
    }

    /**
     * 检测上下文
     */
    private static void checkApplicationContext() {
        if (applicationContext == null){
            throw new IllegalStateException("applicationContext未注入，请在applicationContext.xml中定义SpringContextHolder");
        }
    }
}
