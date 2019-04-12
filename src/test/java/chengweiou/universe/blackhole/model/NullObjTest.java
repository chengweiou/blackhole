package chengweiou.universe.blackhole.model;


import chengweiou.universe.blackhole.model.entity.NotNullObjEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NullObjTest {

    @Test
    public void newInstance() {
        NotNullObjEntity notNullObjEntity = Builder.to(new NotNullObjEntity());
        Assertions.assertEquals(true, !notNullObjEntity.isNull());

        NotNullObjEntity nullObjEntity = NotNullObjEntity.NULL;
        Assertions.assertEquals(true, nullObjEntity.isNull());
        Assertions.assertEquals(true, nullObjEntity.getObj().isNull());
    }

}
