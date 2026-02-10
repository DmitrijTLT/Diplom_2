package praktikum.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {
    private boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;
}
