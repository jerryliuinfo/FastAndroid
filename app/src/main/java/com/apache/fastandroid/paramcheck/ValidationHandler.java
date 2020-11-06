package com.apache.fastandroid.paramcheck;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

/**
 * author: jerry
 * created on: 2020/9/16 12:00 PM
 * description:
 */
public class ValidationHandler {

    private static final String SEPERATOR = "_";
    /**
     * 校验参数不为空
     * @param obj 需要校验的对象
     */

    /**
     *
     * @param obj
     * @param hostPath
     * @param errorMsg
     */
    public static void validateParams(Object obj, String hostPath,StringBuffer errorMsg){
        if (obj == null){
            throw new UserDefineException("参数为空");
        }
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                Validation annotation = field.getAnnotation(Validation.class);
                //注解存在时，则说明当前字段需要校验
                if(annotation != null){
                    // 属性值不能为空
                    if(!notNull(value)){
                        //属性为空的记录下来
                        errorMsg.append(field.getName()).append(",");
                        continue;
                    }
                    checkTypeEnumValue(annotation,field,value,hostPath, errorMsg);
                }
            } catch (IllegalAccessException e) {
                //抛出自定义异常信息
                throw new UserDefineException(field.getName() + e.getMessage());
            } catch (Exception e) {
                //抛出自定义异常信息
                throw new UserDefineException(field.getName() + e.getMessage());
            }
        }

    }

    /**
     * 校验注解配置的属性值
     * @param annotation
     * @param field
     * @param value
     */
    private static void checkTypeEnumValue(Validation annotation, Field field, Object value,String hostPath,StringBuffer msg){
        // 暂值列举几种数据处理,剩余类型处理类似

        switch (annotation.value()){
            //如果是颜色，则校验颜色是否符合正则规范
            case TYPE_COLOR:
                if(!value.toString().startsWith("#")){
                    msg.append(field.getName()).append(SEPERATOR);
                }
                break;
                //如果是图片路径，则校验图片是否存在
            case TYPE_IMAGE:
                String path = value.toString();
                checkImagePath(field,path,hostPath,msg);
                break;
                //这个Array中的数据是图片路径还是color?
            case TYPE_LIST:
                List<String> list = (List<String>) value;
                if (list == null || list.size() == 0){
                    msg.append(field.getName());
                }else {
                    for (String s : list) {
                        checkImagePath(field,s,hostPath,msg);
                    }
                }
                break;

        }
    }


    private static boolean checkImagePath(Field field,String path, String host,StringBuffer msg){
        String fullPath = host + path;
        if(!new File(fullPath).exists()){
            msg.append(field.getName()).append("图片path:"+path).append("图片全路径:"+fullPath).append(SEPERATOR);
        }
        return true;
    }



    /**
     * 非空校验
     *
     * @param value
     * @return
     */
    public static boolean notNull(Object value){
        if(null == value){
            return false;
        }
        if(value instanceof String && isEmpty((String)value)){
            return false;
        }
        if(value instanceof List && isEmpty((List<?>)value)){
            return false;
        }
        return null!=value;
    }

    public static boolean isEmpty(String str){
        return null==str || str.isEmpty();
    }
    public static boolean isEmpty(List<?> list){
        return null==list || list.isEmpty();
    }


}
