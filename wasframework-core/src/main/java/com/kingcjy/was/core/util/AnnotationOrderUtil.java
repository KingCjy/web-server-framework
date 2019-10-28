package com.kingcjy.was.core.util;

import com.kingcjy.was.core.annotations.Order;

import java.util.*;

public class AnnotationOrderUtil {

    public static List sort(List<?> list) {
        Map<Integer, Integer> map = new HashMap<>();

        int noOrder = 100000;
        for(int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            Order order = o.getClass().getAnnotation(Order.class);

            if(order == null) {
                map.put(noOrder++, i);
            } else {
                map.put(order.value(), i);
            }
        }

        List<Integer> keyList = Arrays.asList(map.keySet().toArray(new Integer[]{}));
        Collections.sort(keyList);

        List<Object> result = new ArrayList<>();

        for (Integer key : keyList) {
            Object object = list.get(map.get(key));

            result.add(object);
        }

        return result;
    }
}
