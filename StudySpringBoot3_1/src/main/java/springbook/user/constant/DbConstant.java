package springbook.user.constant;

import lombok.Getter;

@Getter
public enum DbConstant {
    JDBC_URL("jdbc:h2:mem:testdb;MODE=MySQL"),
    ID("sa"),
    PASSWORD("");

    private String str;

    DbConstant(String str) {
        this.str = str;
    }
}
