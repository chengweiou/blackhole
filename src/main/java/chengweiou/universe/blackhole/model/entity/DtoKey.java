package chengweiou.universe.blackhole.model.entity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DtoKey {
    /**
     * true: valid this property will no have the same in a table, same as in a group only have one property
     * false: valid this property will no have the same in a table. e.g. select * from person where phone=#{phone}
     * @return
     */
    boolean single() default false;
    /**
     * 目前只适用于 string
     * true: can be empty string, when empty, will not check exisit. if empty inside a group, then the whole group for this column will not be a key.
     * false:
     * @return
     */
    boolean canEmpty() default false;
    /**
     * will valid properties within a same group name. if more than one group. will need to valid properties in the same group are unique
     * every group can identify a unique entity. usually one group is enough. more group just have more way to identify it. like: phone and email can be two single group
     * do NOT use noGroup, basicGroup as custom group name
     * @return
     */
    String group() default "basicGroup";
}
