package com.uraltranscom.calculaterate.dao;

import com.uraltranscom.calculaterate.configuration.AppConfig;
import com.uraltranscom.calculaterate.configuration.AppInit;
import com.uraltranscom.calculaterate.configuration.DBConfig;
import com.uraltranscom.calculaterate.model.conflicts.Conflict;
import com.uraltranscom.calculaterate.model_ex.TotalModel;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Vladislav.Klochkov
 * @project CalculationRate_1.0
 * @date 13.02.2019
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        AppInit.class,
        DBConfig.class,
        AppConfig.class})
@WebAppConfiguration
public class GetTotalModelDAOTest {

    @Autowired
    private GetTotalModelDAO getTotalModelDAO;
    private TotalModel totalModel;
    private Conflict conflict;

    @Test
    public void testGetTotalModel() {
        Object object = getTotalModelDAO.getObject(PrepareMapParams.prepareMapWithParams("190900", "191208", "023002", 120));
        if (object instanceof TotalModel) {
            totalModel = (TotalModel) object;
        } else {
            conflict = (Conflict) object;
        }
        Assert.assertNotNull("It's not ok", totalModel);
    }
}
