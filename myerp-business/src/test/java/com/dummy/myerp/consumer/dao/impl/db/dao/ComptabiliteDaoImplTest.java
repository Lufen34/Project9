package com.dummy.myerp.consumer.dao.impl.db.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ComptabiliteDaoImplTest {

    @Test
    void getInstance() {
    }

    @Test
    void setSQLgetListCompteComptable() {
    }

    @Test
    void getListCompteComptable() {
    }

    @Test
    void setSQLgetListJournalComptable() {
    }

    @Test
    void getListJournalComptable() {
    }

    @Test
    void setSQLgetListEcritureComptable() {
    }

    @Test
    void getListEcritureComptable() {
    }

    @Test
    void setSQLgetEcritureComptable() {
    }

    @Test
    void getEcritureComptable() {
    }

    @Test
    void setSQLgetEcritureComptableByRef() {
    }

    @Test
    void getEcritureComptableByRef() {
    }

    @Test
    void setSQLloadListLigneEcriture() {
    }

    @Test
    void loadListLigneEcriture() {
    }

    @Test
    void setSQLinsertEcritureComptable() {
    }

    @Test
    void insertEcritureComptable() {
    }

    @Test
    void setSQLinsertListLigneEcritureComptable() {
    }

    @Test
    void insertListLigneEcritureComptable() {
    }

    @Test
    void setSQLupdateEcritureComptable() {
    }

    @Test
    void updateEcritureComptable() {
    }

    @Test
    void setSQLdeleteEcritureComptable() {
    }

    @Test
    void deleteEcritureComptable() {
    }

    @Test
    void setSQLdeleteListLigneEcritureComptable() {
    }

    @Test
    void deleteListLigneEcritureComptable() {
    }

    @Test
    void setSQLgetListSequenceEcritureComptable() {
    }

    @Test
    void getListSequenceEcritureComptable() {
    }

    @Test
    void setSQLupdateSequenceEcritureComptable() {
    }

    @Test
    void updateSequenceEcritureComptable() {
    }

    @Test
    void setSQLinsertSequenceEcritureComptable() {
    }

    @Test
    void insertSequenceEcritureComptable() {
    }

    @ParameterizedTest
    @CsvSource("SELECT max(derniere_valeur) FROM MYERP.sequence_ecriture_comptable where YEAR(annee) = :annee;")
    void setSQLgetLastFromSpecificYearSequenceEcritureComptable(String pSQLinsertSequenceEcritureComptable) {
        String test = pSQLinsertSequenceEcritureComptable;
        assertThat(test).isEqualTo("SELECT max(derniere_valeur) FROM MYERP.sequence_ecriture_comptable where YEAR(annee) = :annee;");
    }

    @Test
    void getLastFromSpecificYearSequenceEcritureComptable() {
    }
}