package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CompteComptableTest {

    private CompteComptable acc;

    @BeforeEach
    void init() {
        acc = new CompteComptable();
        acc.setNumero(1);
        acc.setLibelle("test");
    }

    @AfterEach
    void unRef(){
        acc = null;
    }

    @Test
    void getNumero() {
        assertThat(acc.getNumero()).isEqualTo(1);
        acc = new CompteComptable(33);
        assertThat(acc.getNumero()).isEqualTo(33);
        acc = new CompteComptable(3900);
        assertThat(acc.getNumero()).isEqualTo(3900);
    }

    @ParameterizedTest(name = "value tested : {0}")
    @ValueSource(ints = {1, 4, 6, 11, 88, 206047, 59787, 3265})
    void setNumero(Integer args) {
        acc.setNumero(args);
        assertThat(acc.getNumero()).isEqualTo(args);
    }

    @Test
    void getLibelle() {
        assertThat(acc.getLibelle()).isEqualTo("test");
        acc = new CompteComptable(33, "azerty");
        assertThat(acc.getLibelle()).isEqualTo("azerty");
        acc = new CompteComptable(11, "ezfezfgezgnezkjgzehiuogeziopghuogezapùozeghùozegoezrhzgeihezgphjeg68767897ezrg8ze");
        assertThat(acc.getLibelle()).isEqualTo("ezfezfgezgnezkjgzehiuogeziopghuogezapùozeghùozegoezrhzgeihezgphjeg68767897ezrg8ze");
    }

    @ParameterizedTest
    @CsvSource({"test", "azerty", "ezfezfgezgnezkjgzehiuogeziopghuogezapùozeghùozegoezrhzgeihezgphjeg68767897ezrg8ze"})
    void setLibelle(String arg) {
        acc.setLibelle(arg);
        assertThat(acc.getLibelle()).isEqualTo(arg);
    }

    @Test
    void getByNumero() {
        List<CompteComptable> list = new ArrayList<>();
        list.add(new CompteComptable(2));
        list.add(new CompteComptable(24854));
        list.add(new CompteComptable(624));
        list.add(new CompteComptable(24));
        assertThat(CompteComptable.getByNumero(list, 2)).isEqualTo(list.get(0));
        assertThat(CompteComptable.getByNumero(list, 24854)).isEqualTo(list.get(1));
        assertThat(CompteComptable.getByNumero(list, 624)).isEqualTo(list.get(2));
        assertThat(CompteComptable.getByNumero(list, 24)).isEqualTo(list.get(3));
        assertThat(CompteComptable.getByNumero(list, 3)).isNull();
        assertThat(CompteComptable.getByNumero(list, 11)).isNull();
        assertThat(CompteComptable.getByNumero(list, 25)).isNull();
        assertThat(CompteComptable.getByNumero(list, 354894454)).isNull();
    }
}