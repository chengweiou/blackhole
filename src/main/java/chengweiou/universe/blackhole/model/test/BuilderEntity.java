package chengweiou.universe.blackhole.model.test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BuilderEntity {
    private String stringType;
    private Integer integerType;
    private Long longType;
    private Double doubleType;
    private BuilderEntity objectType;
    private Boolean booleanType;
    private boolean booleanSmallType;
    private int intSmallType;
    private LocalDate localDateType;
    private LocalDateTime localDateTimeType;
    private Instant instantType;

}
