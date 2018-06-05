package cn.easyproject.easyee.sm.sys.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**  
 * description: TODO(功能描述) <br/>  
 * date: 2017年7月26日 下午5:41:06 <br/>  
 * author: gaojx  <br/> 
 * copyright: 北京志诚泰和信息技术有限公司
 */

public class EasyUITreeConvertor<T> {

    /**
     * getEasyUITree:(这里用一句话描述这个方法的作用). <br/>  
     * @author Administrator  
     * @param entityList
     * @param entityConfig
     * @return
     */
    public List<EasyUITreeEntity> getEasyUITree(Map<String, String> entityConfig, List<T> entityList, Class<T> entityClass) {
        List<EasyUITreeEntity> list = new ArrayList<EasyUITreeEntity>();
        // Collections.sort(entityList, comparator);
        try {
            Set<String> keySet = entityConfig.keySet();
            Class<EasyUITreeEntity> clazz = EasyUITreeEntity.class;
            for (T datum : entityList) {
                EasyUITreeEntity treeNode = new EasyUITreeEntity();
                for (String key : keySet) {
                    // 取值
                    Field field = entityClass.getField(entityConfig.get(key));
                    field.setAccessible(true);
                    Object value = field.get(datum);
                    field.setAccessible(false);
                    // 赋值
                    Field field2 = clazz.getField(key);
                    field2.setAccessible(true);
                    field2.set(treeNode, value);
                    field2.setAccessible(false);
                }
                treeNode.setState("OPEN");
                treeNode.setIconCls("");
                list.add(treeNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
  
