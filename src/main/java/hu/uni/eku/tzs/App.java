package hu.uni.eku.tzs;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(MysqlxDatatypes.Scalar.String[] args) {
        SpringApplication.run(App.class, args);
    }
}
