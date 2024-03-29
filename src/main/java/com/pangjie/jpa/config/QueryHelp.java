/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.pangjie.jpa.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.pangjie.jpa.config.annotation.DataPermission;
import com.pangjie.jpa.config.annotation.Query;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SuppressWarnings({"unchecked", "all"})
public class QueryHelp {

    public static <R, Q> Predicate getPredicate(Root<R> root, Q query, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<>();
        if (query == null) {
            return cb.and(list.toArray(new Predicate[0]));
        }

        DataPermission permission = query.getClass().getAnnotation(DataPermission.class);
        if (permission != null) {
            //数据权限集合
            ArrayList<String> permissionList = new ArrayList<>();
            //如果指定了过滤字段
            if (StringUtils.isNotBlank(permission.fieldName())) {
                list.add(cb.in(root.get(permission.fieldName())).value(permissionList));
            }
        }
        try {
            //获取所有属性
            List<Field> fields = getAllFields(query.getClass(), new ArrayList<>());
            for (Field field : fields) {
                // 设置对象的访问权限，保证对private的属性的访问
                field.setAccessible(true);
                //查看当前属性是否有Query注解
                Query q = field.getAnnotation(Query.class);
                if (q != null) {
                    //获取属性名称
                    String attributeName = field.getName();
                    //获取传入值值
                    Object val = field.get(query);
                    if (ObjectUtil.isNull(val) || "".equals(val)) {
                        continue;
                    }
                    switch (q.type()) {
                        case EQUAL:
                            list.add(cb.equal(root.get(attributeName), val));
                            break;
                        case IN:
                            if (CollUtil.isNotEmpty((Collection<Object>) val)) {
                                list.add(cb.in(root.get(attributeName)).value((Collection<Object>) val));
                            }
                            break;
                        case NOT_IN:
                            if (CollUtil.isNotEmpty((Collection<Object>) val)) {
                                list.add(cb.in(root.get(attributeName)).value((Collection<Object>) val).not());
                            }
                            break;
                        case NOT_EQUAL:
                            list.add(cb.notEqual(root.get(attributeName), val));
                            break;
                        case NOT_NULL:
                            list.add(cb.isNotNull(root.get(attributeName)));
                            break;
                        case IS_NULL:
                            list.add(cb.isNull(root.get(attributeName)));
                            break;
                        case BETWEEN:
                            List<Object> between = new ArrayList<>((List<Object>) val);
//                            先判断是不是两个参数
                            if (between.size() == 2) {
//                            先判断是什么类型
                                cb.between(root.get(attributeName)
                                        .as((Class<? extends Comparable>) between.get(0).getClass()),
                                        (Comparable) between.get(0), (Comparable) between.get(1));
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int size = list.size();
        Predicate[] predicates = list.toArray(new Predicate[size]);
        return cb.and(predicates);
    }


    private static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static List<Field> getAllFields(Class clazz, List<Field> fields) {
        if (clazz != null) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            getAllFields(clazz.getSuperclass(), fields);
        }
        return fields;
    }
}
