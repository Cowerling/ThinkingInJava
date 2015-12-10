package containers;

import java.util.*;

/**
 * Created by cowerling on 15-12-7.
 */
public class MapEntry<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;

    public MapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V setValue(V value) {
        V result = this.value;
        this.value = value;
        return result;
    }

    public int hashCode() {
        return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
    }

    public boolean equals(Object object) {
        if(!(object instanceof MapEntry))
            return false;

        MapEntry mapEntry = (MapEntry)object;
        return key == null ? mapEntry.getKey() == null : key.equals(mapEntry.getKey()) &&
                value == null ? mapEntry.getValue() == null : value.equals(mapEntry.getKey());
    }

    public String toString() {
        return key + "=" + value;
    }
}
