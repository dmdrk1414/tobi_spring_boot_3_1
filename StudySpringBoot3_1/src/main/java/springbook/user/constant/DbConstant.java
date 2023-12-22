package springbook.user.constant;

import lombok.Getter;

@Getter
public enum DbConstant {
    //    JDBC_URL("jdbc:h2:mem:testdb;MODE=MySQL"),
//    DRIVER_CLASS("com.mysql.jdbc.Driver.class"),
    DRIVER_CLASS("com.mysql.cj.jdbc.Driver"),
    JDBC_URL("jdbc:mysql://localhost/springbook?characterEncoding=UTF-8"),
    ID("root"),
    PASSWORD("qkrtmdcks1!");

    private String str;

    DbConstant(String str) {
        this.str = str;
    }
}
