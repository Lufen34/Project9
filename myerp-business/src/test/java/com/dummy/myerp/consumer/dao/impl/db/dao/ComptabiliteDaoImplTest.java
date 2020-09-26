package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.SpringRegistry;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.model.exceptions.EmptyStringException;
import com.dummy.myerp.model.exceptions.InvalidYearException;
import com.dummy.myerp.model.exceptions.StringSizeTooBigException;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ComptabiliteDaoImplTest {


    private ComptabiliteDaoImpl comptabiliteDaoUnderTest;

    private static EcritureComptable testEcritureComptable;

    private static EcritureComptable testLigneEcritureComptable;

    private static SequenceEcritureComptable testSequenceEcritureComptable;

    @BeforeAll
    static void contextInit() throws EmptyStringException, StringSizeTooBigException, FunctionalException, InvalidYearException {
        SpringRegistry.init();
        testEcritureComptable = new EcritureComptable.Builder()
                .Id(-4)
                .journal(new JournalComptable("AC", "Achat"))
                .date(new Date())
                .libelle("test")
                .build();
        testEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(706, "test"),
                "test", new BigDecimal(123),
                new BigDecimal(123)));
        testEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(706, "test"),
                "test",  new BigDecimal(1234),
                new BigDecimal(1234)));

        testLigneEcritureComptable = new EcritureComptable.Builder()
                .Id(-99)
                .journal(new JournalComptable("AC", "Achat"))
                .date(new Date())
                .libelle("test")
                .build();
        testLigneEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(706, "test"),
                "test", new BigDecimal(123),
                new BigDecimal(123)));
        testLigneEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(706, "test"),
                "test",  new BigDecimal(1234),
                new BigDecimal(1234)));

        testSequenceEcritureComptable = new SequenceEcritureComptable(2016, 999);
    }

    @AfterAll
    static void unRefStatic() {
        testSequenceEcritureComptable = null;
        testEcritureComptable = null;
        testLigneEcritureComptable = null;
    }

    @BeforeEach
    void init() {
        comptabiliteDaoUnderTest = new ComptabiliteDaoImpl();
    }

    @AfterEach
    void unRef() {
        comptabiliteDaoUnderTest = null;
    }

    @Test
    void getInstance() {
        assertThat(ComptabiliteDaoImpl.getInstance()).isNotNull();
    }

    @ParameterizedTest
    @CsvSource("SELECT * FROM myerp.compte_comptable;")
    void setSQLgetListCompteComptable(String pSQLgetListCompteComptable) {
        String test = pSQLgetListCompteComptable;
        assertThat(test).isEqualTo("SELECT * FROM myerp.compte_comptable;");
    }

    @Test
    void getListCompteComptable() {
        assertThat(comptabiliteDaoUnderTest.getListCompteComptable()).isNotNull();
    }

    @ParameterizedTest
    @CsvSource("SELECT * FROM myerp.journal_comptable;")
    void setSQLgetListJournalComptable(String pSQLgetListJournalComptable) {
        String test = pSQLgetListJournalComptable;
        assertThat(test).isEqualTo("SELECT * FROM myerp.journal_comptable;");
    }

    @Test
    void getListJournalComptable() {
        assertThat(comptabiliteDaoUnderTest.getListCompteComptable()).isNotEmpty();
    }

    @ParameterizedTest
    @CsvSource("SELECT * FROM myerp.ecriture_comptable;")
    void setSQLgetListEcritureComptable(String pSQLgetListEcritureComptable) {
        String test = pSQLgetListEcritureComptable;
        assertThat(test).isEqualTo("SELECT * FROM myerp.ecriture_comptable;");
    }

    @Test
    void getListEcritureComptable() {
        assertThat(comptabiliteDaoUnderTest.getListEcritureComptable()).isNotEmpty();
    }

    @ParameterizedTest
    @CsvSource("SELECT * FROM myerp.ecriture_comptable" +
            "WHERE id = :id;")
    void setSQLgetEcritureComptable(String pSQLgetEcritureComptable) {
        String test = pSQLgetEcritureComptable;
        assertThat(test).isEqualTo("SELECT * FROM myerp.ecriture_comptable" +
"WHERE id = :id;");
    }

    @Test
    @Order(1)
    void getEcritureComptable() throws NotFoundException {
        assertThat(comptabiliteDaoUnderTest.getEcritureComptable(-1)).isNotNull();
    }

    @ParameterizedTest
    @CsvSource("SELECT * FROM myerp.ecriture_comptable" +
            "WHERE reference = :reference;")
    void setSQLgetEcritureComptableByRef(String pSQLgetEcritureComptableByRef) {
        String test = pSQLgetEcritureComptableByRef;
        assertThat(test).isEqualTo("SELECT * FROM myerp.ecriture_comptable" +
        "WHERE reference = :reference;");
    }

    @Test
    void getEcritureComptableByRef() throws NotFoundException {
        assertThat(comptabiliteDaoUnderTest.getEcritureComptableByRef("AC-2016/00001")).isNotNull();
    }

    @ParameterizedTest
    @CsvSource("SELECT * FROM myerp.ligne_ecriture_comptable" +
            "WHERE ecriture_id = :ecriture_id" +
            "ORDER BY ligne_id")
    void setSQLloadListLigneEcriture(String pSQLloadListLigneEcriture) {
        String test = pSQLloadListLigneEcriture;
        assertThat(test).isEqualTo("SELECT * FROM myerp.ligne_ecriture_comptable" +
        "WHERE ecriture_id = :ecriture_id" +
        "ORDER BY ligne_id");
    }

    @ParameterizedTest
    @ValueSource(strings = "INSERT INTO myerp.ecriture_comptable (" +
            "id," +
            "journal_code, reference, date, libelle" +
            ") VALUES (" +
            "nextval('myerp.ecriture_comptable_id_seq')," +
            ":journal_code, :reference, :date, :libelle" +
            ")")
    void setSQLinsertEcritureComptable(String pSQLinsertEcritureComptable) {
        String test = pSQLinsertEcritureComptable;
        assertThat(test).isEqualTo("INSERT INTO myerp.ecriture_comptable (" +
            "id," +
            "journal_code, reference, date, libelle" +
            ") VALUES (" +
            "nextval('myerp.ecriture_comptable_id_seq')," +
            ":journal_code, :reference, :date, :libelle" +
            ")");
    }

    @Test
    @Order(2)
    void insertEcritureComptable() throws NotFoundException {
        comptabiliteDaoUnderTest.insertEcritureComptable(testEcritureComptable);
        assertThat(comptabiliteDaoUnderTest.getEcritureComptable(testEcritureComptable.getId())).isNotNull();
    }

    @ParameterizedTest
    @ValueSource(strings = "INSERT INTO myerp.ligne_ecriture_comptable (" +
            "ecriture_id, ligne_id, compte_comptable_numero, libelle, debit," +
            "credit" +
            ") VALUES (" +
            ":ecriture_id, :ligne_id, :compte_comptable_numero, :libelle, :debit," +
            ":credit" +
            ")")
    void setSQLinsertListLigneEcritureComptable(String pSQLinsertListLigneEcritureComptable) {
        String test = pSQLinsertListLigneEcritureComptable;
        assertThat(test).isEqualTo("INSERT INTO myerp.ligne_ecriture_comptable (" +
            "ecriture_id, ligne_id, compte_comptable_numero, libelle, debit," +
            "credit" +
            ") VALUES (" +
            ":ecriture_id, :ligne_id, :compte_comptable_numero, :libelle, :debit," +
            ":credit" +
            ")");
    }

    @Test
    @Order(4)
    void getListLigneEcritureComptable() {
        assertThat(comptabiliteDaoUnderTest.getListLigneEcritureComptable(testEcritureComptable.getId())).isNotEmpty();
        assertThat(comptabiliteDaoUnderTest.getListLigneEcritureComptable(testLigneEcritureComptable.getId())).isEmpty();
    }

    @Test
    @Order(6)
    void insertListLigneEcritureComptable() {
        comptabiliteDaoUnderTest.insertEcritureComptable(testLigneEcritureComptable);
        assertThat(comptabiliteDaoUnderTest.getListLigneEcritureComptable(testLigneEcritureComptable.getId())).isNotEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = "UPDATE myerp.ecriture_comptable SET" +
            "journal_code = :journal_code," +
                    "reference = :reference," +
                    "date = :date," +
                    "libelle = :libelle" +
                    "WHERE" +
                    "id = :id")
    void setSQLupdateEcritureComptable(String pSQLupdateEcritureComptable) {
        String test = pSQLupdateEcritureComptable;
        assertThat(test).isEqualTo("UPDATE myerp.ecriture_comptable SET" +
        "journal_code = :journal_code," +
        "reference = :reference," +
        "date = :date," +
        "libelle = :libelle" +
        "WHERE" +
        "id = :id");
    }

    @Test
    @Order(3)
    void updateEcritureComptable() throws NotFoundException {
        testEcritureComptable.setLibelle("This was changed after a test");
        comptabiliteDaoUnderTest.updateEcritureComptable(testEcritureComptable);
        assertThat(comptabiliteDaoUnderTest.getEcritureComptable(testEcritureComptable.getId()).getLibelle()).isEqualTo("This was changed after a test");
    }

    @ParameterizedTest
    @CsvSource("DELETE FROM myerp.ecriture_comptable" +
            "WHERE id = :id")
    void setSQLdeleteEcritureComptable(String pSQLdeleteEcritureComptable) {
        String test = pSQLdeleteEcritureComptable;
        assertThat(test).isEqualTo("DELETE FROM myerp.ecriture_comptable" +
                                    "WHERE id = :id");
    }

    @Test
    @Order(5)
    void deleteEcritureComptable() throws NotFoundException {
        comptabiliteDaoUnderTest.deleteEcritureComptable(testEcritureComptable.getId());
        assertThrows(NotFoundException.class, ()-> {
            comptabiliteDaoUnderTest.getEcritureComptable(testEcritureComptable.getId());
        });
    }

    @ParameterizedTest
    @CsvSource("DELETE FROM myerp.ligne_ecriture_comptable" +
            "WHERE ecriture_id = :ecriture_id")
    void setSQLdeleteListLigneEcritureComptable(String pSQLdeleteListLigneEcritureComptable) {
        String test = pSQLdeleteListLigneEcritureComptable;
        assertThat(test).isEqualTo("DELETE FROM myerp.ligne_ecriture_comptable" +
        "WHERE ecriture_id = :ecriture_id");
    }

    @Test
    @Order(7)
    void deleteListLigneEcritureComptable() {
        comptabiliteDaoUnderTest.deleteEcritureComptable(testEcritureComptable.getId());
        comptabiliteDaoUnderTest.deleteListLigneEcritureComptable(testLigneEcritureComptable.getId());
        assertThat(comptabiliteDaoUnderTest.getListLigneEcritureComptable(testLigneEcritureComptable.getId())).isEmpty();
    }

    @ParameterizedTest
    @CsvSource("SELECT * FROM myerp.sequence_ecriture_comptable")
    void setSQLgetListSequenceEcritureComptable(String pSQLgetListSequenceEcritureComptable) {
        String test = pSQLgetListSequenceEcritureComptable;
        assertThat(test).isEqualTo("SELECT * FROM myerp.sequence_ecriture_comptable");
    }

    @Test
    void getListSequenceEcritureComptable() {
        assertThat(comptabiliteDaoUnderTest.getListSequenceEcritureComptable()).isNotEmpty();
    }

    @ParameterizedTest
    @CsvSource("UPDATE myerp.sequence_ecriture_comptable" +
            "SET derniere_valeur = :derniere_valeur" +
            "WHERE" +
            "id = :id")
    void setSQLupdateSequenceEcritureComptable(String pSQLupdateSequenceEcritureComptable) {
        String test = pSQLupdateSequenceEcritureComptable;
        assertThat(test).isEqualTo("UPDATE myerp.sequence_ecriture_comptable" +
                                "SET derniere_valeur = :derniere_valeur" +
                                "WHERE" +
                                "id = :id");
    }

    @ParameterizedTest
    @ValueSource(strings = "INSERT INTO myerp.sequence_ecriture_comptable (" +
            "journal_code, annee, derniere_valeur" +
            ") VALUES (" +
            ":journal_code, :annee, :derniere_valeur" +
            ")")
    void setSQLinsertSequenceEcritureComptable(String pSQLinsertSequenceEcritureComptable) {
        String test = pSQLinsertSequenceEcritureComptable;
        assertThat(test).isEqualTo("INSERT INTO myerp.sequence_ecriture_comptable (" +
                                    "journal_code, annee, derniere_valeur" +
                                    ") VALUES (" +
                                    ":journal_code, :annee, :derniere_valeur" +
                                    ")");
    }

    @ParameterizedTest
    @CsvSource("SELECT max(derniere_valeur) FROM MYERP.sequence_ecriture_comptable where YEAR(annee) = :annee;")
    void setSQLgetLastFromSpecificYearSequenceEcritureComptable(String pSQLgetLastFromSpecificYearSequenceEcritureComptable) {
        String test = pSQLgetLastFromSpecificYearSequenceEcritureComptable;
        assertThat(test).isEqualTo("SELECT max(derniere_valeur) FROM MYERP.sequence_ecriture_comptable where YEAR(annee) = :annee;");
    }

    @Test
    void getLastFromSpecificYearSequenceEcritureComptable() {
        assertThat(comptabiliteDaoUnderTest.getLastFromSpecificYearSequenceEcritureComptable(2016)).isNotNull();
    }
}