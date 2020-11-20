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
package com.pangjie.baseAuthority.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.pangjie.baseAuthority.config.annotation.Query;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author Zheng Jie
 * @date 2019-6-4 14:59:48
 */
@Slf4j
@SuppressWarnings({"unchecked","all"})
public class QueryHelp {

    public static <R, Q> Predicate getPredicate(Root<R> root, Q query, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<>();
        if(query == null){
            return cb.and(list.toArray(new Predicate[0]));
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
                    //属性类型
                    Class<?> fieldType = field.getType();
                    //获取传入值值
                    Object val = field.get(query);
                    if (ObjectUtil.isNull(val) || "".equals(val)) {
                        continue;
                    }
                    Join join = null;
                    switch (q.type()) {
                        case EQUAL:
                            list.add(cb.equal(getExpression(attributeName,join,root)
                                    .as((Class<? extends Comparable>) fieldType),val));
                            break;
                        case IN:
                            if (CollUtil.isNotEmpty((Collection<Long>)val)) {
                                list.add(getExpression(attributeName,join,root).in((Collection<Long>) val));
                            }
                            break;
                        case NOT_EQUAL:
                            list.add(cb.notEqual(getExpression(attributeName,join,root), val));
                            break;
                        case NOT_NULL:
                            list.add(cb.isNotNull(getExpression(attributeName,join,root)));
                            break;
                        case IS_NULL:
                            list.add(cb.isNull(getExpression(attributeName,join,root)));
                            break;
                        case BETWEEN:
                            List<Object> between = new ArrayList<>((List<Object>)val);
                            list.add(cb.between(getExpression(attributeName, join, root).as((Class<? extends Comparable>) between.get(0).getClass()),
                                    (Comparable) between.get(0), (Comparable) between.get(1)));
                            break;
                        default: break;
                    }
                }
//                field.setAccessible(accessible);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int size = list.size();
        return cb.and(list.toArray(new Predicate[size]));
    }

    @SuppressWarnings("unchecked")
    private static <T, R> Expression<T> getExpression(String attributeName, Join join, Root<R> root) {
        if (ObjectUtil.isNotEmpty(join)) {
            return join.get(attributeName);
        } else {
            return root.get(attributeName);
        }
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
