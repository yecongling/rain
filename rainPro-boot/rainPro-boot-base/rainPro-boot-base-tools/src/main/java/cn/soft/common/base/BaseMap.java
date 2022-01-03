package cn.soft.common.base;

import cn.hutool.core.util.ObjectUtil;
import org.apache.commons.beanutils.ConvertUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /**
     * 获取Long类型的数据
     * @param key 键
     * @return 返回Long类型的数据
     */
    public Long getLong(String key) {
        Object v = get(key);
        if (ObjectUtil.isNotEmpty(v)) {
            return new Long(clone().toString());
        } else  {
            return  null;
        }
    }

    /**
     * 获取Long类型额数组数据
     * @param key 键
     * @return 返回Lone类型的数组数据
     */
    public Long[] getLongs(String key) {
        Object v = get(key);
        if (ObjectUtil.isNotEmpty(v)) {
            return (Long[]) v;
        } else {
            return null;
        }
    }

    /**
     * 获取long类型的集合
     * @param key 键
     * @return 返回long类型的集合
     */
    public List<Long> getListLong(String key) {
        List<String> list = get(key);
        if (ObjectUtil.isNotEmpty(list)) {
            return list.stream().map(Long::new).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    /**
     * 获取long类型的数组数据
     * @param key 键
     * @return 返回数据
     */
    public Long[] getLongIds(String key) {
        Object ids = get(key);
        if (ObjectUtil.isNotEmpty(ids)) {
            return (Long[]) ConvertUtils.convert(ids.toString().split(","), Long.class);
        } else {
            return null;
        }
    }

    /**
     * 获取Integer数据
     * @param key 键
     * @param def 默认值
     * @return 如果取到值则返回  取不到返回默认值
     */
    public Integer getInt(String key, Integer def) {
        Object o = get(key);
        if (ObjectUtil.isNotEmpty(o)){
            return Integer.parseInt(o.toString());
        } else {
            return def;
        }
    }

    /**
     * 获取Integer数据
     * @param key 键
     * @return 取到值返回值  取不到默认返回0
     */
    public Integer getInt(String key) {
        Object v = get(key);
        if (ObjectUtil.isNotEmpty(v)) {
            return Integer.parseInt(v.toString());
        } else {
            return 0;
        }
    }

    /**
     * 获取精度数据
     * @param key 键
     * @return 取到则返回  取不到0
     */
    public BigDecimal getBigDecimal(String key) {
        Object v = get(key);
        if (ObjectUtil.isNotEmpty(v)) {
            return new BigDecimal(v.toString());
        }
        return new BigDecimal("0");
    }


    /**
     * 获取数据
     * @param key 键
     * @param def 默认值
     * @param <T> 类型
     * @return 返回取到的数据或默认值
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, T def) {
        Object obj = super.get(key);
        if (ObjectUtil.isEmpty(obj)) {
            return def;
        }
        return (T) obj;
    }

    /**
     * 转换为BaseMap
     * @param obj 原始对象
     * @return 转换后的对象
     */
    public static BaseMap toBaseMap(Map<String, Object> obj) {
        BaseMap map = new BaseMap();
        map.putAll(obj);
        return map;
    }
}
