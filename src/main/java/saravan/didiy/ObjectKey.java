package saravan.didiy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@EqualsAndHashCode(of = {"type", "param"})
@NoArgsConstructor
@AllArgsConstructor
public class ObjectKey implements Serializable {
    String type;
    String param;


    /*
    @Override
    public int hashCode() {
        return Objects.hash(type, param);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof ObjectKey))
            return false;
        if (obj == this)
            return true;
        return this.type.equals(((ObjectKey) obj).type) &&
                this.param.equals(((ObjectKey) obj).param);
    }
    */

}
