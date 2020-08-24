package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        EcritureComptable vBean = new EcritureComptable.Builder()
                .Id(pRS.getInt("id"))
                .journal(journalComptableDaoCache.getByCode(pRS.getString("journal_code")))
                .reference(pRS.getString("reference"))
                .date(pRS.getDate("date"))
                .libelle(pRS.getString("libelle"))
                .build();

        // Chargement des lignes d'écriture
        ConsumerHelper.getDaoProxy().getComptabiliteDao().loadListLigneEcriture(vBean);

        return vBean;
    }
}
