package com.dummy.myerp.consumer.dao.impl.db.dao;


import java.sql.Types;
import java.util.*;

import com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite.*;
import com.dummy.myerp.model.bean.comptabilite.*;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.db.AbstractDbConsumer;
import com.dummy.myerp.consumer.db.DataSourcesEnum;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


/**
 * Implémentation de l'interface {@link ComptabiliteDao}
 */
public class ComptabiliteDaoImpl extends AbstractDbConsumer implements ComptabiliteDao {

    // ==================== Constructeurs ====================
    /** Instance unique de la classe (design pattern Singleton) */
    private static final ComptabiliteDaoImpl INSTANCE = new ComptabiliteDaoImpl();
    private static final String REFERENCE = "reference";
    private static final String ECRITURE_ID = "ecriture_id";
    private static final String LIBELLE = "libelle";

    /**
     * Renvoie l'instance unique de la classe (design pattern Singleton).
     *
     * @return {@link ComptabiliteDaoImpl}
     */
    public static ComptabiliteDaoImpl getInstance() {
        return ComptabiliteDaoImpl.INSTANCE;
    }

    /**
     * Constructeur.
     */
    protected ComptabiliteDaoImpl() {
        super();
        Map<DataSourcesEnum, DataSource> vMapDataSource = new EnumMap<>(DataSourcesEnum.class);
        vMapDataSource.put(DataSourcesEnum.MYERP, dataSource());
        configure(vMapDataSource);
    }


    // ==================== Méthodes ====================

    @Bean
    private DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:9032/db_myerp");
        ds.setUsername("usr_myerp");
        ds.setPassword("myerp");
        return ds;
    }

    /** SQLgetListCompteComptable */
    private static String sqlGetListCompteComptable;
    public static void setSQLgetListCompteComptable(String pSQLgetListCompteComptable) {
        sqlGetListCompteComptable = pSQLgetListCompteComptable;
    }
    @Override
    public List<CompteComptable> getListCompteComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        CompteComptableRM vRM = new CompteComptableRM();
        return vJdbcTemplate.query(sqlGetListCompteComptable, vRM);
    }


    /** SQLgetListJournalComptable */
    private static String sqlGetListJournalComptable;
    public static void setSQLgetListJournalComptable(String pSQLgetListJournalComptable) {
        sqlGetListJournalComptable = pSQLgetListJournalComptable;
    }
    @Override
    public List<JournalComptable> getListJournalComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        JournalComptableRM vRM = new JournalComptableRM();
        return  vJdbcTemplate.query(sqlGetListJournalComptable, vRM);
    }

    // ==================== EcritureComptable - GET ====================

    /** SQLgetListEcritureComptable */
    private static String sqlGetListEcritureComptable;
    public static void setSQLgetListEcritureComptable(String pSQLgetListEcritureComptable) {
        sqlGetListEcritureComptable = pSQLgetListEcritureComptable;
    }
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        EcritureComptableRM vRM = new EcritureComptableRM();
        return vJdbcTemplate.query(sqlGetListEcritureComptable, vRM);
    }


    /** SQLgetEcritureComptable */
    private static String sqlGetEcritureComptable;
    public static void setSQLgetEcritureComptable(String pSQLgetEcritureComptable) {
        sqlGetEcritureComptable = pSQLgetEcritureComptable;
    }
    @Override
    public EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pId);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(sqlGetEcritureComptable, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : id=" + pId);
        }
        return vBean;
    }


    /** SQLgetEcritureComptableByRef */
    private static String sqlGetEcritureComptableByRef;
    public static void setSQLgetEcritureComptableByRef(String pSQLgetEcritureComptableByRef) {
        sqlGetEcritureComptableByRef = pSQLgetEcritureComptableByRef;
    }
    @Override
    public EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(REFERENCE, pReference);
        EcritureComptableRM vRM = new EcritureComptableRM();
        EcritureComptable vBean;
        try {
            vBean = vJdbcTemplate.queryForObject(sqlGetEcritureComptableByRef, vSqlParams, vRM);
        } catch (EmptyResultDataAccessException vEx) {
            throw new NotFoundException("EcritureComptable non trouvée : reference=" + pReference);
        }
        return vBean;
    }


    /** SQLloadListLigneEcriture */
    private static String sqlLoadListLigneEcriture;
    public static void setSQLloadListLigneEcriture(String pSQLloadListLigneEcriture) {
        sqlLoadListLigneEcriture = pSQLloadListLigneEcriture;
    }
    @Override
    public void loadListLigneEcriture(EcritureComptable pEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ECRITURE_ID, pEcritureComptable.getId());
        LigneEcritureComptableRM vRM = new LigneEcritureComptableRM();
        List<LigneEcritureComptable> vList = vJdbcTemplate.query(sqlLoadListLigneEcriture, vSqlParams, vRM);
        pEcritureComptable.getListLigneEcriture().clear();
        pEcritureComptable.getListLigneEcriture().addAll(vList);
    }


    // ==================== EcritureComptable - INSERT ====================

    /** SQLinsertEcritureComptable */
    private static String sqlInsertEcritureComptable;
    public static void setSQLinsertEcritureComptable(String pSQLinsertEcritureComptable) {
        sqlInsertEcritureComptable = pSQLinsertEcritureComptable;
    }
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) {
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("journal_code", pEcritureComptable.getJournal().getCode());
        vSqlParams.addValue(REFERENCE, pEcritureComptable.getReference());
        vSqlParams.addValue("date", pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue(LIBELLE, pEcritureComptable.getLibelle());

        vJdbcTemplate.update(sqlInsertEcritureComptable, vSqlParams);

        // ----- Récupération de l'id
        Integer vId = this.queryGetSequenceValuePostgreSQL(DataSourcesEnum.MYERP, "myerp.ecriture_comptable_id_seq",
                                                           Integer.class);
        pEcritureComptable.setId(vId);

        // ===== Liste des lignes d'écriture
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }

    /** SQLinsertListLigneEcritureComptable */
    private static String sqlInsertListLigneEcritureComptable;
    public static void setSQLinsertListLigneEcritureComptable(String pSQLinsertListLigneEcritureComptable) {
        sqlInsertListLigneEcritureComptable = pSQLinsertListLigneEcritureComptable;
    }
    /**
     * Insert les lignes d'écriture de l'écriture comptable
     * @param pEcritureComptable l'écriture comptable
     */
    protected void insertListLigneEcritureComptable(EcritureComptable pEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ECRITURE_ID, pEcritureComptable.getId());

        int vLigneId = 0;
        for (LigneEcritureComptable vLigne : pEcritureComptable.getListLigneEcriture()) {
            vLigneId++;
            vSqlParams.addValue("ligne_id", vLigneId);
            vSqlParams.addValue("compte_comptable_numero", vLigne.getCompteComptable().getNumero());
            vSqlParams.addValue(LIBELLE, vLigne.getLibelle());
            vSqlParams.addValue("debit", vLigne.getDebit());

            vSqlParams.addValue("credit", vLigne.getCredit());

            vJdbcTemplate.update(sqlInsertListLigneEcritureComptable, vSqlParams);
        }
    }


    // ==================== EcritureComptable - UPDATE ====================

    /** SQLupdateEcritureComptable */
    private static String sqlUpdateEcritureComptable;
    public static void setSQLupdateEcritureComptable(String pSQLupdateEcritureComptable) {
        sqlUpdateEcritureComptable = pSQLupdateEcritureComptable;
    }
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) {
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pEcritureComptable.getId());
        vSqlParams.addValue("journal_code", pEcritureComptable.getJournal().getCode());
        vSqlParams.addValue(REFERENCE, pEcritureComptable.getReference());
        vSqlParams.addValue("date", pEcritureComptable.getDate(), Types.DATE);
        vSqlParams.addValue(LIBELLE, pEcritureComptable.getLibelle());

        vJdbcTemplate.update(sqlUpdateEcritureComptable, vSqlParams);

        // ===== Liste des lignes d'écriture
        this.deleteListLigneEcritureComptable(pEcritureComptable.getId());
        this.insertListLigneEcritureComptable(pEcritureComptable);
    }


    // ==================== EcritureComptable - DELETE ====================

    /** SQLdeleteEcritureComptable */
    private static String sqlDeleteEcritureComptable;
    public static void setSQLdeleteEcritureComptable(String pSQLdeleteEcritureComptable) {
        sqlDeleteEcritureComptable = pSQLdeleteEcritureComptable;
    }
    @Override
    public void deleteEcritureComptable(Integer pId) {
        // ===== Suppression des lignes d'écriture
        this.deleteListLigneEcritureComptable(pId);

        // ===== Suppression de l'écriture
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("id", pId);
        vJdbcTemplate.update(sqlDeleteEcritureComptable, vSqlParams);
    }

    /** SQLdeleteListLigneEcritureComptable */
    private static String sqlDeleteListLigneEcritureComptable;
    public static void setSQLdeleteListLigneEcritureComptable(String pSQLdeleteListLigneEcritureComptable) {
        sqlDeleteListLigneEcritureComptable = pSQLdeleteListLigneEcritureComptable;
    }
    /**
     * Supprime les lignes d'écriture de l'écriture comptable d'id {@code pEcritureId}
     * @param pEcritureId id de l'écriture comptable
     */
    protected void deleteListLigneEcritureComptable(Integer pEcritureId) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue(ECRITURE_ID, pEcritureId);
        vJdbcTemplate.update(sqlDeleteListLigneEcritureComptable, vSqlParams);
    }

    // ==================== SequenceEcritureComptable ====================
    /** SQLgetListJournalComptable */
    private static String sqlGetListSequenceEcritureComptable;
    public static void setSQLgetListSequenceEcritureComptable(String pSQLgetListSequenceEcritureComptable) {
        sqlGetListSequenceEcritureComptable = pSQLgetListSequenceEcritureComptable;
    }
    public List<SequenceEcritureComptable> getListSequenceEcritureComptable() {
        JdbcTemplate vJdbcTemplate = new JdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        SequenceEcritureComptableRM vRM = new SequenceEcritureComptableRM();
        return vJdbcTemplate.query(sqlGetListSequenceEcritureComptable, vRM);
    }

    private static String sqlUpdateSequenceEcritureComptable;
    public static void setSQLupdateSequenceEcritureComptable(String pSQLupdateSequenceEcritureComptable) {
        sqlUpdateSequenceEcritureComptable = pSQLupdateSequenceEcritureComptable;
    }


    @Override
    public void updateSequenceEcritureComptable(SequenceEcritureComptable pSequenceEcritureComptable) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();

        vSqlParams.addValue("derniere_valeur", pSequenceEcritureComptable.getDerniereValeur());

        vJdbcTemplate.update(sqlUpdateSequenceEcritureComptable, vSqlParams);
    }

    private static String sqlInsertSequenceEcritureComptable;
    public static void setSQLinsertSequenceEcritureComptable(String pSQLinsertSequenceEcritureComptable) {
        sqlInsertSequenceEcritureComptable = pSQLinsertSequenceEcritureComptable;
    }

    @Override
    public void insertSequenceEcritureComptable(SequenceEcritureComptable pSequenceEcritureComptable) {
        // ===== Ecriture Comptable
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("annee", pSequenceEcritureComptable.getAnnee());
        vSqlParams.addValue("derniere_valeur", pSequenceEcritureComptable.getDerniereValeur() + 1);

        vJdbcTemplate.update(sqlInsertSequenceEcritureComptable, vSqlParams);
    }

    private static String sqlGetLastFromSpecificYearSequenceEcritureComptable;
    public static void setSQLgetLastFromSpecificYearSequenceEcritureComptable(String pSQLgetLastFromSpecificYearSequenceEcritureComptable) {
        sqlGetLastFromSpecificYearSequenceEcritureComptable = pSQLgetLastFromSpecificYearSequenceEcritureComptable;
    }

    @Override
    public Integer getLastFromSpecificYearSequenceEcritureComptable(Integer pYear) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(getDataSource(DataSourcesEnum.MYERP));
        MapSqlParameterSource vSqlParams = new MapSqlParameterSource();
        vSqlParams.addValue("annee", pYear);

        return vJdbcTemplate.queryForObject(sqlGetLastFromSpecificYearSequenceEcritureComptable, vSqlParams, Integer.class);
    }

    private static String sqlGetListLigneEcritureComptable;
    public static void setSQLgetListLigneEcritureComptable(String pSQLgetListLigneEcritureComptable) {
        sqlGetListLigneEcritureComptable = pSQLgetListLigneEcritureComptable;
    }

    @Override
    public List<LigneEcritureComptable> getListLigneEcritureComptable(Integer pId) {
        NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(this.getDataSource(DataSourcesEnum.MYERP));
        LigneEcritureComptableRM vRM = new LigneEcritureComptableRM();
        Map<String, Integer> namedParameters = Collections.singletonMap(ECRITURE_ID, pId);

        return vJdbcTemplate.query(sqlGetListLigneEcritureComptable, namedParameters, vRM);
    }

}
