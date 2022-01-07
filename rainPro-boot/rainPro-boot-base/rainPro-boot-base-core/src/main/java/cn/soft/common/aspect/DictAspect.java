package cn.soft.common.aspect;

import cn.soft.common.api.CommonAPI;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 字典aop类
 */
@Aspect
@Component
@Slf4j
public class DictAspect {

    private CommonAPI commonAPI;
    @Autowired
    public void setCommonAPI(CommonAPI commonAPI) {
        this.commonAPI = commonAPI;
    }


}
