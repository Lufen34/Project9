package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dummy.myerp.model.exceptions.EmptyStringException;
import com.dummy.myerp.model.exceptions.StringSizeTooBigException;
import org.springframework.jdbc.core.RowMapper;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;


/**
 * {@link RowMapper} de {@link JournalComptable}
 */
public class JournalComptableRM implements RowMapper<JournalComptable> {

    @Override
    public JournalComptable mapRow(ResultSet pRS, int pRowNum) throws SQLException {
        JournalComptable vBean = null;
        try {
            vBean = new JournalComptable(pRS.getString("code"), pRS.getString("libelle"));
        } catch (StringSizeTooBigException | EmptyStringException e) {
            e.printStackTrace();
        }

        return vBean;
    }
}
