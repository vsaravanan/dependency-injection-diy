package saravan.didiy;

import lombok.Data;


@Singleton
@Data

public class A {

    private A() {}

    public static A instance;

    public static synchronized A getInstance() {
        if (instance == null) {
            instance = new A();
        }
        return instance;
    }

    String name;

//    just for test
//    @Autowired
//    C myc;

}
