package receipt.products;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ProductList {

    String FILE_NAME = "pl.csv"; // имя файла для чтения/сохранения

    Map<Integer, Product> productList = new LinkedHashMap<>(); // БД - хэшмап товаров id : product

    boolean idExists(int id); // проверяет есть ли id в хэшмапе

    Product getProductByID(int productID); // возвращает объект товара (данные) по его ID

    void save(String fileName); // сохраняет БД (хэшмап) в файл в виде CSV

}
