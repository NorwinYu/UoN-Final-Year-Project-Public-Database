package com.uraltranscom.calculaterate.service.impl;

import com.uraltranscom.calculaterate.dao.GetCountryStationDAO;
import com.uraltranscom.calculaterate.dao.GetTotalModelDAO;
import com.uraltranscom.calculaterate.model.RatesList;
import com.uraltranscom.calculaterate.model.conflicts.Conflict;
import com.uraltranscom.calculaterate.model.route.Route;
import com.uraltranscom.calculaterate.model_ex.TotalModel;
import com.uraltranscom.calculaterate.util.GetVolumeGroup;
import com.uraltranscom.calculaterate.util.PrepareMapParams;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * Класс основной логике
 *
 * @author Vladislav Klochkov
 * @version 1.0
 * @create 26.12.2018
 *
 * 26.12.2018
 *   1. Версия 1.0
 *
 */

@Component
@Getter
public class CommonLogicClass {
    private final Logger logger = LoggerFactory.getLogger(CommonLogicClass.class);

    @Autowired
    private GetTotalModelDAO getTotalModelDAO;
    @Autowired
    private GetCountryStationDAO getCountryStationDAO;
    private ArrayList<TotalModel> totalListModels = new ArrayList<>();
    private TotalModel totalModel = null;
    private Conflict conflict = null;

    @Autowired
    private GetListRates getListRates;
    private Set<RatesList> listRates = new HashSet<>();


    public void startLogic(String idStationDeparture, String idStationDestination, String idCargo, int volumeWagon, File fileRates) {
        logger.info("Start process with entry params: idStationDeparture - {}; idStationDestination - {}; idCargo - {}; volumeWagon - {}", idStationDeparture, idStationDestination, idCargo, volumeWagon);
        if (fileRates != null && listRates.isEmpty()) {
            listRates = getListRates.getListRates(fileRates);
        }
        totalModel = null;
        conflict = null;
        Object object = getTotalModelDAO.getObject(PrepareMapParams.prepareMapWithParams(idStationDeparture, idStationDestination, idCargo, GetVolumeGroup.getVolumeGroup(volumeWagon)));
        if (object instanceof TotalModel) {
            totalModel = (TotalModel) object;
            if (listRates != null) {
                totalModel.setActualYield(getActualRate(totalModel, listRates, GetVolumeGroup.getVolumeGroup(volumeWagon)));
            }
            totalListModels.add(totalModel);
        } else {
            conflict = (Conflict) object;
        }
    }

    private Object getActualRate(TotalModel totalModel, Set<RatesList> listRates, int volume){
        double actualRate;
        for (Route route : totalModel.getTotalList()) {
            if (route.isFlagNeedCalc()) {
                for (RatesList ratesList : listRates) {
                    if (ratesList.getVolume() == volume &&
                        (ratesList.getStationFrom().getNameStation().equals(route.getStationDeparture().getNameStation()) && ratesList.getStationFrom().getRoad().getNameRoad().equals(route.getStationDeparture().getRoad().getNameRoad()))) {
                        String nameCountry = getCountryStationDAO.getObject(PrepareMapParams.prepareMapWithParams(route.getStationDestination().getIdStation())).getNameCountry();
                        if (nameCountry.equals("Россия")) {
                            if (ratesList.getStationTo().getRoad().getNameRoad().equals(route.getStationDestination().getRoad().getNameRoad())) {
                                actualRate = ratesList.getActualRate();
                                return actualRate;
                            }
                        } else {
                            if (ratesList.getStationTo().getNameStation().equals(route.getStationDestination().getNameStation())) {
                                actualRate = ratesList.getActualRate();
                                return actualRate;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
