package praktikum.user;

import java.time.LocalDate;

public class UserData {
    public String email = "testSPB" + LocalDate.now() + "burger@test.ru";
    String newEmail = "testSPB" + LocalDate.now() + "SPBtest" + "@test.ru";
    public String password = "TestTLT";
    String newPassword = "TestTLT" + System.currentTimeMillis();
    public String name = "Дмитрий";
    String newName = "Сергей";
}
