package chengweiou.universe.blackhole.model.test;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Override
    public String toString() {
        return "BuilderEntity{" +
                "stringType='" + stringType + '\'' +
                ", integerType=" + integerType +
                ", longType=" + longType +
                ", doubleType=" + doubleType +
                ", objectType=" + objectType +
                ", booleanType=" + booleanType +
                ", booleanSmallType=" + booleanSmallType +
                ", intSmallType=" + intSmallType +
                ", localDateType=" + localDateType +
                ", localDateTimeType=" + localDateTimeType +
                '}';
    }

    public String getStringType() {
        return stringType;
    }

    public void setStringType(String stringType) {
        this.stringType = stringType;
    }

    public Integer getIntegerType() {
        return integerType;
    }

    public void setIntegerType(Integer integerType) {
        this.integerType = integerType;
    }

    public Long getLongType() {
        return longType;
    }

    public void setLongType(Long longType) {
        this.longType = longType;
    }

    public Double getDoubleType() {
        return doubleType;
    }

    public void setDoubleType(Double doubleType) {
        this.doubleType = doubleType;
    }

    public BuilderEntity getObjectType() {
        return objectType;
    }

    public void setObjectType(BuilderEntity objectType) {
        this.objectType = objectType;
    }

    public Boolean getBooleanType() {
        return booleanType;
    }

    public void setBooleanType(Boolean booleanType) {
        this.booleanType = booleanType;
    }

    public boolean isBooleanSmallType() {
        return booleanSmallType;
    }

    public void setBooleanSmallType(boolean booleanSmallType) {
        this.booleanSmallType = booleanSmallType;
    }

    public int getIntSmallType() {
        return intSmallType;
    }

    public void setIntSmallType(int intSmallType) {
        this.intSmallType = intSmallType;
    }

    public LocalDate getLocalDateType() {
        return localDateType;
    }

    public void setLocalDateType(LocalDate localDateType) {
        this.localDateType = localDateType;
    }

    public LocalDateTime getLocalDateTimeType() {
        return localDateTimeType;
    }

    public void setLocalDateTimeType(LocalDateTime localDateTimeType) {
        this.localDateTimeType = localDateTimeType;
    }
}
