package com.uraltranscom.calculaterate.dao;

import com.uraltranscom.calculaterate.model.conflicts.Conflict;
import com.uraltranscom.calculaterate.model.route.Cargo;
import com.uraltranscom.calculaterate.model.station.Road;
import com.uraltranscom.calculaterate.model.route.Route;
import com.uraltranscom.calculaterate.model.station.Station;
import com.uraltranscom.calculaterate.model_ex.TotalModel;
import com.uraltranscom.calculaterate.util.connect.ConnectionDB;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author vladislav.klochkov
 * @project CalculationRate_1.0
 * @date 13.01.2019
 */

@Component
@NoArgsConstructor
public class GetTotalModelDAO {
    private static Logger logger = LoggerFactory.getLogger(GetTotalModelDAO.class);
    private static final String SQL_CALL_NAME = " { call  test_rate.list_routes_and_rate(?,?,?,?) } ";

    @Autowired
    private ConnectionDB connectionDB;

    public Object getObject(Map<String, Object> params) {
        String conflictCode;
        String conflictType;
        String conflictMessage;
        Conflict conflict = null;

        List<Route> totalListRoute = new ArrayList<>();
        TotalModel totalModel = null;

        Connection connection = null;
        CallableStatement callableStatement;

        try {
            connection = connectionDB.getDataSource().getConnection();
            connection.setAutoCommit(false);
            callableStatement = connection.prepareCall(SQL_CALL_NAME);
            for (int i = 1; i < params.size() + 1; i++) {
                callableStatement.setObject(i, params.get("param" + i));
            }
            ResultSet resultSet = callableStatement.executeQuery();

            if (resultSet.next()) {
                conflictCode = (String) resultSet.getObject(3);
                if (conflictCode != null) {
                    conflictType = (String) resultSet.getObject(4);
                    conflictMessage = resultSet.getString(5);
                    conflict = new Conflict(conflictCode, conflictType, conflictMessage);
                    return conflict;
                } else {
                    ResultSet routesSet = (ResultSet) resultSet.getObject(1);
                    while (routesSet.next()) {
                        int idGroup = routesSet.getInt(2);
                        int num = routesSet.getInt(3);
                        String idStationDeparture = routesSet.getString(4);
                        String nameStationDeparture = routesSet.getString(5);
                        int idRoadDeparture = routesSet.getInt(6);
                        String nameRoadDeparture = routesSet.getString(7);
                        String idStationDestination = routesSet.getString(8);
                        String nameStationDestination = routesSet.getString(9);
                        int idRoadDestination = routesSet.getInt(10);
                        String nameRoadDestination = routesSet.getString(11);
                        String idCargo = routesSet.getString(12);
                        String nameCargo = routesSet.getString(13);
                        int distance = routesSet.getInt(14);
                        int countDays = routesSet.getInt(15);
                        int countDaysLoadAndUnload = routesSet.getInt(16);
                        int fullCountDays = routesSet.getInt(17);
                        double rate = routesSet.getDouble(18);
                        double tariff = routesSet.getDouble(19);
                        boolean flagNeedCalc = routesSet.getBoolean(20);

                        totalListRoute.add(
                                new Route(
                                        new Station(idStationDeparture, nameStationDeparture, new Road(idRoadDeparture, nameRoadDeparture)),
                                        new Station(idStationDestination, nameStationDestination, new Road(idRoadDestination, nameRoadDestination)),
                                        distance,
                                        new Cargo(idCargo, nameCargo),
                                        countDays,
                                        countDaysLoadAndUnload,
                                        fullCountDays,
                                        rate,
                                        tariff * (-1),
                                        flagNeedCalc
                                )
                        );
                    }

                    ResultSet paramsRoutSet = (ResultSet) resultSet.getObject(2);
                    while (paramsRoutSet.next()) {
                        int idGroup = paramsRoutSet.getInt(2);
                        int sumDistance = paramsRoutSet.getInt(3);
                        int sumCountDays = paramsRoutSet.getInt(4);
                        int sumCountDaysLoadAndUnload = paramsRoutSet.getInt(5);
                        int sumFullCountDays = paramsRoutSet.getInt(6);
                        double sumRateOrTariff = paramsRoutSet.getDouble(7);

                        double yield = sumRateOrTariff / sumFullCountDays;

                        totalModel = new TotalModel(totalListRoute, idGroup, sumDistance, sumCountDays, sumCountDaysLoadAndUnload, sumFullCountDays, sumRateOrTariff, yield);
                    }
                    logger.debug("Get info for: {}: {}", params, totalModel);
                    return totalModel;
                }
            }
            connection.commit();
        } catch (SQLException sqlEx) {
            logger.error("Error query: {}", sqlEx.getMessage());
            try {
                connection.rollback();
                logger.info("Rollback transaction!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.debug("Error close connection!");
            }
        }
        return null;
    }
}
