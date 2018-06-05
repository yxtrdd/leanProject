package cn.easyproject.easyee.sm.base.util;

import java.util.Map;
import java.util.Set;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**  
 * description: JMS工具类 <br/>  
 * date: 2017年10月30日 下午3:49:40 <br/>  
 * author: gaojx  <br/> 
 * copyright: 北京志诚泰和信息技术有限公司
 */
public class JMSUtil {
    
    /**
     * 发布订阅消息
     * @param propertyMap 消息属性
     * @param contentMap 消息内容
     */
    public static void sendTopicMessage(final Map<String, Object> propertyMap, final Map<String, Object> contentMap) {
        JmsTemplate jmsTemplate = (JmsTemplate) SpringContextUtil.getBean("jmsTemplate");
        Destination destination = (Destination) SpringContextUtil.getBean("jmsTopic");
        
        jmsTemplate.setPubSubDomain(true);
        
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                // 设置属性
                Set<String> propertyKeys = propertyMap.keySet();
                for (String key : propertyKeys) {
                    message.setObjectProperty(key, propertyMap.get(key));
                }
                // 设置内容
                Set<String> contentKeys = contentMap.keySet();
                for (String key : contentKeys) {
                    message.setObject(key, contentMap.get(key));
                }
                return message;
            }
        });
    }
    
    
}
  
