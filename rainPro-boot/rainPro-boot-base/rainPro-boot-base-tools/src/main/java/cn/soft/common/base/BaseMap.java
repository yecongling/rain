package cn.soft.common.base;

import cn.hutool.core.util.ObjectUtil;

import java.util.HashMap;
import java.util.Optional;

/**
 * 自定义map
 */
public class BaseMap extends HashMap<String, Object> {
    private static final long serialVersionUID = -3301524732761575778L;

    public BaseMap() {
    }

    /**
     * 重载put方法
     * @param key 键
     * @param value 值
     * @return 返回当前对象
     */
    @Override
    public BaseMap put(String key, Object value) {
        super.put(key, Optional.ofNullable(value).orElse(""));
        return this;
    }

    /**
     * 添加元素的方法
     * @param key 键
     * @param value 值
     * @return 返回当前对象
     */
    public BaseMap add(String key, Object value) {
        super.put(key, Optional.ofNullable(value).orElse(""));
        return this;
    }

    /**
     * 获取元素
     * @param key 键
     * @param <T> 参数类型
     * @return 返回获取到的对象或null
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        Object obj = super.get(key);
        if (ObjectUtil.isNotEmpty(obj)) {
            return (T)obj;
        } else {
            return  null;
        }
    }

    /**
     * 获取Boolean类型的数据
     * @param key 键
     * @return 返回Boolean数据
     */
    public Boolean getBoolean(String key) {
        Object obj = super.get(key);
        if (ObjectUtil.isNotEmpty(obj)) {
            return Boolean.valueOf(obj.toString());
        } else {
            return false;
        }
    }
}
