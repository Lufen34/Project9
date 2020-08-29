package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.model.exceptions.InvalidYearException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceEcritureComptableRM implements RowMapper<SequenceEcritureComptable> {
    @Override
    public SequenceEcritureComptable mapRow(ResultSet resultSet, int i) throws SQLException {
        SequenceEcritureComptable seq = new SequenceEcritureComptable();
        try {
            seq.setAnnee(resultSet.getInt("annee"));
        } catch (InvalidYearException e) {
            e.printStackTrace();
        }
        seq.setDerniereValeur(resultSet.getInt("derniere_valeur"));

        return seq;
    }
}
