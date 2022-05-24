package receipt;

import java.util.Map;

public interface DataMap<K, V> {

    Map<K, V> get();

    boolean contains(K key);

    V getValue(K key);

    void save(String fileName);

}
