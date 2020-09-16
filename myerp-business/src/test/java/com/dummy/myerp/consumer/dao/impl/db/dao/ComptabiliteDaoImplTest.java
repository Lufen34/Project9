package com.dummy.myerp.consumer.dao.impl.db.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ComptabiliteDaoImplTest {

    @Test
    void getInstance() {
        fail("Not implemented yet");
    }

    @Test
    void setSQLgetListCompteComptable() {
        fail("Not implemented yet");
    }

    @Test
    void getListCompteComptable() {
        fail("Not implemented yet");
    }

    @Test
    void setSQLgetListJournalComptable() {
        fail("Not implemented yet");
    }

    @Test
    void getListJournalComptable() {
        fail("Not implemented yet");
    }

    @Test
    void setSQLgetListEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void getListEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void setSQLgetEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void getEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void setSQLgetEcritureComptableByRef() {
        fail("Not implemented yet");
    }

    @Test
    void getEcritureComptableByRef() {
        fail("Not implemented yet");
    }

    @Test
    void setSQLloadListLigneEcriture() {
        fail("Not implemented yet");
    }

    @Test
    void loadListLigneEcriture() {
        fail("Not implemented yet");
    }

    @Test
    void setSQLinsertEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void insertEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void setSQLinsertListLigneEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void insertListLigneEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void setSQLupdateEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void updateEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void setSQLdeleteEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void deleteEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void setSQLdeleteListLigneEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void deleteListLigneEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void setSQLgetListSequenceEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void getListSequenceEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void setSQLupdateSequenceEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void updateSequenceEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void setSQLinsertSequenceEcritureComptable() {
        fail("Not implemented yet");
    }

    @Test
    void insertSequenceEcritureComptable() {
        fail("Not implemented yet");
    }

    @ParameterizedTest
    @CsvSource("SELECT max(derniere_valeur) FROM MYERP.sequence_ecriture_comptable where YEAR(annee) = :annee;")
    void setSQLgetLastFromSpecificYearSequenceEcritureComptable(String pSQLinsertSequenceEcritureComptable) {
        String test = pSQLinsertSequenceEcritureComptable;
        assertThat(test).isEqualTo("SELECT max(derniere_valeur) FROM MYERP.sequence_ecriture_comptable where YEAR(annee) = :annee;");
    }

    @Test
    void getLastFromSpecificYearSequenceEcritureComptable() {
        fail("Not implemented yet");
    }
}