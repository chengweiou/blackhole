package chengweiou.universe.blackhole.model;


import chengweiou.universe.blackhole.model.test.NotNullObjEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NullObjTest {

    @Test
    public void newInstance() {
        NotNullObjEntity notNullObjEntity = Builder.to(new NotNullObjEntity());
        Assertions.assertEquals(true, notNullObjEntity.isNotNull());

        NotNullObjEntity nullObjEntity = NotNullObjEntity.NULL;
        Assertions.assertEquals(true, !nullObjEntity.isNotNull());
        Assertions.assertEquals(true, !nullObjEntity.getObj().isNotNull());
    }

}
