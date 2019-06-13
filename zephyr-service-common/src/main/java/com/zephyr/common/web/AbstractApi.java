package com.zephyr.common.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.zephyr.common.exception.BOException;

/**
 * @author enpy cheung
 */
public abstract class AbstractApi implements BaseApi{

    protected final Logger logger = LoggerFactory.getLogger(AbstractApi.class);

    protected void assertCondition(boolean condition, ApiResultCode e) {
        if(!condition) {
            throw new BOException(e.getCode(), e.getMessage());
        }
    }

    protected List<Integer> convertArray2List(String arrayStr){
        List<Integer> list = null;
        if(StringUtils.isNotBlank(arrayStr)) {
            list = new ArrayList<>();
            for(String e : arrayStr.split(",")) {
                if(NumberUtils.isDigits(e)) {
                    list.add(Integer.valueOf(e));
                }
            }
        }
        return list;
    }

    /**
     * Copy List对象
     * @param list
     * @param clazz
     * @return
     */
    protected <T> List<T> copyList(@SuppressWarnings("rawtypes") List list, Class<T> clazz){
        List<T> respList = new ArrayList<>();
        for(Object o : list) {
            respList.add(copy(o, clazz));
        }

        return respList;
    }

    /**
     * Copy Object对象
     * @param ori
     * @param clazz
     * @return
     */
    protected <T> T copy(Object ori, Class<T> clazz){
        T t = null;
        try {
            t = clazz.newInstance();
            BeanUtils.copyProperties(ori, t);
        } catch (Exception e) {
            logger.error("异常",e);
        }

        return t;
    }

    protected <T> T copy(Object ori, T target){
        if(target == null) {
            throw new NullPointerException("Target copy object is null.");
        }

        try {
            BeanUtils.copyProperties(ori, target);
        } catch (Exception e) {
            logger.error("异常",e);
        }
        return target;
    }
}
