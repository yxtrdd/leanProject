package cn.easyproject.easyee.sm.base.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON工具
 * @author gaojx
 */
public final class JSONUtil {
    private final static Log LOG = LogFactory.getLog(JSONUtil.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    
    static {
        // 如果Json字符串中是单值的数组转成Java集合，则会报异常，设置为true屏蔽报异常
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        // 如果Json字符串中有而Java对象没有的属性，则会报异常，设置为false屏蔽报异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 字符串key不加双引号
//      objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 通过该方法对objectMapper对象进行设置，所有序列化的对象都将按改规则进行系列化
        // Include.Include.ALWAYS 默认
        // Include.NON_DEFAULT 属性为默认值不序列化
        // Include.NON_EMPTY 属性为空（""） 或者为 NULL 都不序列化
        // Include.NON_NULL 属性为NULL 不序列化
        objectMapper.setSerializationInclusion(Include.NON_EMPTY);
    }
    
    private JSONUtil() {
        super();
    }

    /**
     * 将Java对象转换成Json字符串
     * @param bean Java对象
     * @return Json字符串
     */
    public static String beanToJson(Object bean) {
        String json = "";
        try {
            json = objectMapper.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(String.format("Error convert bean to string: message=%s", e.getMessage()));
            }
        }
        return json;
    }
    
    /**
     * 将Json字符串转换成Java对象
     * @param jsonStr Json字符串
     * @param beanClass Java对象对应的class对象，T一定要有默认构造方法
     * @return Java对象
     */
    public static <T> T jsonToBean(String jsonStr, Class<T> beanClass) {
        T bean = null;
        try {
            ObjectMapper objMapper = new ObjectMapper();
            objMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            objMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            bean = objMapper.readValue(jsonStr, beanClass);
        } catch (IOException e) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(String.format("Error convert string to bean: message=%s", e.getMessage()));
            }
        }
        return bean;
    }
    
    /**
     * 将Json字符串转换成Java对象
     * @param jsonStr Json字符串
     * @param javaType TypeReference对象，T一定要有默认构造方法
     * @return Java对象
     */
    public static <T> T jsonToBean(String jsonStr, TypeReference<T> javaType) {
        T bean = null;
        try {
            bean = objectMapper.readValue(jsonStr, javaType);
        } catch (IOException e) {
            if (LOG.isWarnEnabled()) {
                LOG.warn(String.format("Error convert string to bean: message=%s", e.getMessage()));
            }
        }
        return bean;
    }
    
}
