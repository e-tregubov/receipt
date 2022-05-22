package receipt.receipt;

import java.util.ArrayList;

public interface Form {

    ArrayList<String> get();
    void print();
    void save(String fileName);

}
