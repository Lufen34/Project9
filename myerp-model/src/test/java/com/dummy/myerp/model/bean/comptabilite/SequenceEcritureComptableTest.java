package com.dummy.myerp.model.bean.comptabilite;

import com.dummy.myerp.model.exceptions.InvalidYearException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SequenceEcritureComptableTest {

    private SequenceEcritureComptable actualSequence;

    @BeforeEach
    void init() {
        try {
            actualSequence = new SequenceEcritureComptable(2020, 300);
        } catch (InvalidYearException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAnnee() {
        assertThat(actualSequence.getAnnee()).isEqualTo(2020);

        try {
            actualSequence = new SequenceEcritureComptable(2019, 565);
        } catch (InvalidYearException e) {
            e.printStackTrace();
        }
        SequenceEcritureComptable expectedSequence = null;
        try {
            expectedSequence = new SequenceEcritureComptable(2019, 565);
        } catch (InvalidYearException e) {
            e.printStackTrace();
        }

        assertThat(actualSequence.toString()).isEqualTo(expectedSequence.toString());
    }

    @ParameterizedTest
    @ValueSource(ints = {1996, 1284, 2013, 2019, 2020})
    void setAnnee(int args) {
        assertDoesNotThrow(() -> {
            actualSequence.setAnnee(args);
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {3569, 2247, 2113, 2100})
    void setAnnee_ThrowsExceptionIfValueInFuture(int args){
        assertThrows(InvalidYearException.class, ()-> {
           actualSequence.setAnnee(args);
        });
    }

    @Test
    void getDerniereValeur() {
        assertThat(actualSequence.getDerniereValeur()).isEqualTo(300);
        try {
            actualSequence = new SequenceEcritureComptable(2019, 565);
        } catch (InvalidYearException e) {
            e.printStackTrace();
        }
        assertThat(actualSequence.getDerniereValeur()).isEqualTo(565);

    }

    @ParameterizedTest
    @ValueSource(ints = {3569, 2247, 2113, 2100})
    void setDerniereValeur(int arg) {
        actualSequence.setDerniereValeur(arg);
        assertThat(actualSequence.getDerniereValeur()).isEqualTo(arg);
    }
}