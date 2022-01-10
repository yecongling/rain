package cn.soft.config.shiro.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

/**
 * 自定义ShiroFilterFactoryBean解决资源中文路径问题
 */
@Slf4j
public class CustomShiroFilterFactoryBean extends ShiroFilterFactoryBean {
}
