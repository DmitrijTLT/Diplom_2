package praktikum.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateResponse {
    private String name;
    private Order order;
    private boolean success;
}
