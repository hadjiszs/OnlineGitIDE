package com.multimif.dao;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Created by p1317074 on 20/10/16.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntityFactoryManagerTest {
    @Test
    public void createEntityManager() {
        EntityFactoryManager.persistance();
    }

    @Test
    public void removeEntityManager(){
        EntityFactoryManager.close();
    }

}