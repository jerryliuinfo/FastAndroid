package com.apache.fastandroid.demo.hawk.impl;

import com.apache.fastandroid.demo.hawk.bean.DataInfo;
import com.apache.fastandroid.demo.hawk.interfaces.IConverter;
import com.apache.fastandroid.demo.hawk.interfaces.IParser;
import com.google.gson.reflect.TypeToken;
import com.tesla.framework.common.util.Preconditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Concrete implementation of encoding and decoding.
 * List types will be encoded/decoded by parser
 * Serializable types will be encoded/decoded object stream
 * Not serializable objects will be encoded/decoded by parser
 */
public final class HawkConverter implements IConverter {

  private final IParser parser;

  public HawkConverter(IParser parser) {
    if (parser == null) {
      throw new NullPointerException("Parser should not be null");
    }
    this.parser = parser;
  }

  @Override public <T> String toString(T value) {
    if (value == null) {
      return null;
    }
    if (String.class.isAssignableFrom(value.getClass())){
      return (String) value;
    }
    return parser.toJson(value);
  }

  @SuppressWarnings("unchecked")
  @Override public <T> T fromString(String value, DataInfo info) throws Exception {
    if (value == null) {
      return null;
    }
    Preconditions.checkNotNull(info,"data info");

    Class<?> keyType = info.keyClazz;
    Class<?> valueType = info.valueClazz;

    switch (info.dataType) {
      case DataInfo.TYPE_OBJECT:
        return toObject(value, keyType);
      case DataInfo.TYPE_LIST:
        return toList(value, keyType);
      case DataInfo.TYPE_MAP:
        return toMap(value, keyType, valueType);
      case DataInfo.TYPE_SET:
        return toSet(value, keyType);
      default:
        return null;
    }
  }

  private <T> T toObject(String json, Class<?> type) throws Exception {
    return parser.fromJson(json, type);
  }

  @SuppressWarnings("unchecked")
  private <T> T toList(String json, Class<?> type) throws Exception {
    if (type == null) {
      return (T) new ArrayList<>();
    }
    List<T> list = parser.fromJson(
        json,
        new TypeToken<List<T>>() {
        }.getType()
    );

    int size = list.size();
    for (int i = 0; i < size; i++) {
      list.set(i, (T) parser.fromJson(parser.toJson(list.get(i)), type));
    }
    return (T) list;
  }

  @SuppressWarnings("unchecked")
  private <T> T toSet(String json, Class<?> type) throws Exception {
    Set<T> resultSet = new HashSet<>();
    if (type == null) {
      return (T) resultSet;
    }
    Set<T> set = parser.fromJson(json, new TypeToken<Set<T>>() {
    }.getType());

    for (T t : set) {
      String valueJson = parser.toJson(t);
      T value = parser.fromJson(valueJson, type);
      resultSet.add(value);
    }
    return (T) resultSet;
  }

  @SuppressWarnings("unchecked")
  private <K, V, T> T toMap(String json, Class<?> keyType, Class<?> valueType) throws Exception {
    Map<K, V> resultMap = new HashMap<>();
    if (keyType == null || valueType == null) {
      return (T) resultMap;
    }
    Map<K, V> map = parser.fromJson(json, new TypeToken<Map<K, V>>() {
    }.getType());

    for (Map.Entry<K, V> entry : map.entrySet()) {
      String keyJson = parser.toJson(entry.getKey());
      K k = parser.fromJson(keyJson, keyType);

      String valueJson = parser.toJson(entry.getValue());
      V v = parser.fromJson(valueJson, valueType);
      resultMap.put(k, v);
    }
    return (T) resultMap;
  }

}
