package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LigneEcritureComptableTest {

    private LigneEcritureComptable actualEcriture;

    @BeforeEach
    void init(){
        actualEcriture = new LigneEcritureComptable(new CompteComptable(13, "test"), "test", new BigDecimal("200"), new BigDecimal("200"));
    }

    @AfterEach
    void unRef(){
        actualEcriture = null;
    }

    @Test
    void getCompteComptable() {
        assertThat(actualEcriture.getCompteComptable().getNumero()).isEqualTo(13);
        assertThat(actualEcriture.getCompteComptable().getLibelle()).isEqualTo("test");

        LigneEcritureComptable expectedEcriture = new LigneEcritureComptable(new CompteComptable(13, "test"),
                "test", new BigDecimal("200"), new BigDecimal("200"));

        assertThat(actualEcriture.toString()).hasToString(expectedEcriture.toString());
    }

    @Test
    void setCompteComptable() {
        LigneEcritureComptable expectedEcriture = new LigneEcritureComptable(new CompteComptable(13, "test"),
                "test", new BigDecimal("200"), new BigDecimal("200"));
        assertThat(actualEcriture.getCompteComptable().toString()).hasToString(expectedEcriture.getCompteComptable().toString());

        actualEcriture.setCompteComptable(new CompteComptable(123, "test pas mal!"));
        expectedEcriture.setCompteComptable(new CompteComptable(123, "test pas mal!"));
        assertThat(actualEcriture.getCompteComptable().toString()).hasToString(expectedEcriture.getCompteComptable().toString());
    }

    @Test
    void setCompteComptable_NotNull() {
        assertThat(actualEcriture.getCompteComptable()).isNotNull();
    }

    @Test
    void getLibelle() {
        assertThat(actualEcriture.getLibelle()).isEqualTo("test");
        actualEcriture = new LigneEcritureComptable(new CompteComptable(13, "test"),
                "test pas mal!", new BigDecimal("200"), new BigDecimal("200"));
        assertThat(actualEcriture.getLibelle()).isEqualTo("test pas mal!");
    }

    @ParameterizedTest
    @CsvSource({"test", "efjfjpzijfizefiepfzjfejzfezjfezfezfezfezfezzfezfzefezfzfz", "This is an other test", "Why not 487484 é mù$ù*mùmdfsfdsfzefzefdzfze"})
    void setLibelle(String args) {
        actualEcriture.setLibelle(args);
        assertThat(actualEcriture.getLibelle()).isEqualTo(args);
    }

    @Test
    void getDebit() {
        assertThat(actualEcriture.getDebit()).isEqualTo(new BigDecimal(200));
        actualEcriture = new LigneEcritureComptable(new CompteComptable(13, "test"),
                "test pas mal!", new BigDecimal("2500"), new BigDecimal("200"));
        assertThat(actualEcriture.getDebit()).isEqualTo(new BigDecimal(2500));
    }

    @ParameterizedTest
    @CsvSource({"250", "30014", "45987", "-5097"})
    void setDebit(String arg) {
        actualEcriture.setDebit(new BigDecimal(arg));
        assertThat(actualEcriture.getDebit()).isEqualTo(new BigDecimal(arg));
    }

    @Test
    void getCredit() {
        assertThat(actualEcriture.getCredit()).isEqualTo((new BigDecimal(200)));
        actualEcriture = new LigneEcritureComptable(new CompteComptable(13, "test"),
                "test pas mal!", new BigDecimal("20"), new BigDecimal("2500"));
        assertThat(actualEcriture.getCredit()).isEqualTo((new BigDecimal(2500)));
    }

    @ParameterizedTest
    @CsvSource({"250", "30014", "45987", "-5097"})
    void setCredit(String arg) {
        actualEcriture.setCredit(new BigDecimal(arg));
        assertThat(actualEcriture.getCredit()).isEqualTo((new BigDecimal(arg)));
    }
}