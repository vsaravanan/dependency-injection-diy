package saravan.didiy;

import lombok.Data;

@Singleton
@Data
public class C {

    private C() {}

    public static C instance;

    public static synchronized C getInstance() {
        if (instance == null) {
            instance = new C();
        }
        return instance;
    }

    String name;

    @Autowired
    A ca;

}
