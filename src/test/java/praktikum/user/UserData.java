package praktikum.user;

import java.time.LocalDate;

public class UserData {
    public static final String EMAIL = "testSPB" + LocalDate.now() + "burger@test.ru";
    public static final String NEW_EMAIL = "testSPB" + LocalDate.now() + "SPBtest" + "@test.ru";
    public static final String PASSWORD = "TestTLT";
    public static final String NEW_PASSWORD = "TestTLT" + System.currentTimeMillis();
    public static final String NAME = "Дмитрий";
    public static final String NEW_NAME = "Сергей";
}
