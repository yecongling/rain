package cn.soft.common.aspect;

import cn.soft.common.api.CommonAPI;
import cn.soft.common.api.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    private RedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 定义切入点 pointcut
     */
    @Pointcut("execution(public * cn.soft.modules..*.*Controller.*(..))")
    public void executeService(){}

    /**
     * 环绕执行方法
     * @param point 切入点
     * @return /
     * @throws Throwable /
     */
    @Around("executeService()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        long time1 = System.currentTimeMillis();
        Object result = point.proceed();
        long time2 = System.currentTimeMillis();
        log.debug("获取JSON数据  耗时：" + (time2 - time1) + "ms");
        long start = System.currentTimeMillis();
        this.parseDictText(result);
        long end = System.currentTimeMillis();
        log.debug("注入字典到JSON数据   耗时" + (end - start) + "ms");
        return result;
    }

    /**
     * 本方法针对返回对象为Result 的IPage的分页列表数据进行动态字典注入
     * 字典注入实现 通过对实体类添加注解@dict 来标识需要的字典内容,字典分为单字典code即可 ，table字典 code table text配合使用与原来jeecg的用法相同
     * 示例为SysUser   字段为sex 添加了注解@Dict(dicCode = "sex") 会在字典服务立马查出来对应的text 然后在请求list的时候将这个字典text，已字段名称加_dictText形式返回到前端
     * 例输入当前返回值的就会多出一个sex_dictText字段
     * {
     *      sex:1,
     *      sex_dictText:"男"
     * }
     * 前端直接取值sext_dictText在table里面无需再进行前端的字典转换了
     *  customRender:function (text) {
     *               if(text==1){
     *                 return "男";
     *               }else if(text==2){
     *                 return "女";
     *               }else{
     *                 return text;
     *               }
     *             }
     *             目前vue是这么进行字典渲染到table上的多了就很麻烦了 这个直接在服务端渲染完成前端可以直接用
     * @param result 参数
     */
    private void parseDictText(Object result) {
        if (result instanceof Result) {

        }

    }
}
