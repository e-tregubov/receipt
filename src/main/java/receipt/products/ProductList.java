package receipt.products;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ProductList {

    String FILE_NAME = "pl.csv"; // имя файла сохранения

    Map<Integer, Product> productList = new LinkedHashMap<>();

    default boolean contains(int id) { return productList.containsKey(id); }
    default Product getProductByID(int id) { return productList.get(id); }

    void save(String fileName); // сохраняет БД (хэшмап) в файл в виде CSV

}
