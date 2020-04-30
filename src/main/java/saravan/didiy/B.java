package saravan.didiy;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Prototype
@Data
@EqualsAndHashCode(of = {"name"})
public class B {
    String name;

    @Autowired
    A mya;

    public B(String name) {
        this.name = name;
    }

    private B() {}

}
