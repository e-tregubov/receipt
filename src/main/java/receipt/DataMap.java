package receipt;

public interface DataMap<K, V> {

    boolean contains(K key);

    V getValue(K key);

    void save(String fileName);

}
