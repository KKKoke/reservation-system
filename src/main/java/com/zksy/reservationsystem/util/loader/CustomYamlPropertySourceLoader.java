package com.zksy.reservationsystem.util.loader;

import com.zksy.reservationsystem.util.encode.EncoderUtil;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 对yml文件进行解密器
 */
public class CustomYamlPropertySourceLoader extends YamlPropertySourceLoader {
    private final String ENCRYPT_PREFIX = "ihbut{";
    private final String END_PREFIX = "}";
    private static final String SOURCE = "source";

    @Override
    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
        List<PropertySource<?>> propertySources = super.load(name, resource);
        propertySources.forEach(propertySource -> {
            OriginTrackedMapPropertySource trackedMapPropertySource = (OriginTrackedMapPropertySource) propertySource;
            //反射获取source然后修改其值
            Map<String, Object> source = trackedMapPropertySource.getSource();
            //深拷贝
            HashMap<Object, Object> hashMap = new HashMap<>();
            hashMap.putAll(source);
            //进行解密操作
            decode(hashMap);
            //反射替换原有成员变量source
            replaceFinalSourceFiled(trackedMapPropertySource, hashMap);
        });
        return propertySources;
    }

    /**
     * 进行解密操作
     */
    private void decode(HashMap<Object, Object> hashMap) {
        hashMap.forEach((k, v) -> {
            if (v instanceof OriginTrackedValue) {
                OriginTrackedValue v1 = (OriginTrackedValue) v;
                Object value = v1.getValue();
                //解密
                if (value instanceof String && ((String) value).startsWith(ENCRYPT_PREFIX) && ((String) value).endsWith(END_PREFIX)) {
                    String extractPwd = ((String) value).substring(ENCRYPT_PREFIX.length(), ((String) value).length() - END_PREFIX.length());
                    value = EncoderUtil.decode(extractPwd);
                    OriginTrackedValue trackedValue = OriginTrackedValue.of(value, v1.getOrigin());
                    hashMap.put(k, trackedValue);
                }
            }
        });
    }

    /**
     * 反射替换原有成员变量source
     */
    private void replaceFinalSourceFiled(OriginTrackedMapPropertySource trackedMapPropertySource, HashMap<Object, Object> hashMap) {
        Class<? extends OriginTrackedMapPropertySource> sourceClass = trackedMapPropertySource.getClass();
        try {
            Field[] allFields = getListFields(sourceClass);
            for (Field field : allFields) {
                if (field.getName().equals(SOURCE)) {
                    field.setAccessible(true);
                    field.set(trackedMapPropertySource, hashMap);
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取某个类所有属性,包括继承至父类的
     */
    public static Field[] getListFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        return fieldList.toArray(fields);
    }
}
