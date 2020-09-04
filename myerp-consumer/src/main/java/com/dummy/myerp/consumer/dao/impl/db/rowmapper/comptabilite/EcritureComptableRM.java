package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dummy.myerp.technical.exception.FunctionalException;
import org.springframework.jdbc.core.RowMapper;
import com.dummy.myerp.consumer.ConsumerHelper;
import com.dummy.myerp.consumer.dao.impl.cache.JournalComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;


/**
 * {@link RowMapper} de {@link EcritureComptable}
 */
public class EcritureComptableRM implements RowMapper<EcritureComptable> {

    /** JournalComptableDaoCache */
    private final JournalComptableDaoCache journalComptableDaoCache = new JournalComptableDaoCache();

    @Override
    public EcritureComptable mapRow(ResultSet pRS, int pRowNum) throws SQLException {
        EcritureComptable vBean = null;
        try {
            vBean = new EcritureComptable.Builder()
                    .Id(pRS.getInt("id"))
                    .journal(journalComptableDaoCache.getByCode(pRS.getString("journal_code")))
                    .date(pRS.getDate("date"))
                    .libelle(pRS.getString("libelle"))
                    .build();
        } catch (FunctionalException e) {
            e.printStackTrace();
        }

        // Chargement des lignes d'écriture
        ConsumerHelper.getDaoProxy().getComptabiliteDao().loadListLigneEcriture(vBean);

        return vBean;
    }


}
