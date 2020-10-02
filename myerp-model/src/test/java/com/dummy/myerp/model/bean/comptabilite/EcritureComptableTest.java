package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dummy.myerp.model.exceptions.EmptyStringException;
import com.dummy.myerp.model.exceptions.StringSizeTooBigException;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

 class EcritureComptableTest {
    private EcritureComptable vEcriture;

    @BeforeEach
    public void initTests() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1996, 1, 1);

        Date dateTest = calendar.getTime();

        try {
            vEcriture = new EcritureComptable.Builder()
                    .Id(123)
                    .date(dateTest)
                    .journal(new JournalComptable("AC", "test"))
                    .build();
        } catch (StringSizeTooBigException | FunctionalException | EmptyStringException e) {
            e.printStackTrace();
        }
    }

   @AfterEach
    public void unrefTests() {
        vEcriture = null;
    }

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                                     .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                                                                    vLibelle,
                                                                    vDebit, vCredit);
        return vRetour;
    }


    @Test
     void isEquilibree() {
        System.out.println(vEcriture.getListLigneEcriture().isEmpty());
        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        assertThat(vEcriture.isEquilibree()).isTrue();
    }

    @Test
     void isNotEquilibree() {
        System.out.println(vEcriture.getListLigneEcriture().isEmpty());
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        assertThat(vEcriture.isEquilibree()).isFalse();
    }

    @Test
    void getId() {
        vEcriture.setId(1);
        assertThat(vEcriture.getId()).isEqualTo(1);
    }


    @ParameterizedTest(name = "Id equals : {0}")
    @ValueSource(ints = { 1, 4, 6, 3})
    void setId(Integer pId) {
        vEcriture.setId(pId);
        assertThat(vEcriture.getId()).isEqualTo(pId);
    }

    @Test
    void getJournal() {
        JournalComptable j = new JournalComptable();
        JournalComptable k = new JournalComptable();
        vEcriture.setJournal(j);
        assertThat(vEcriture.getJournal()).isEqualTo(j);
        vEcriture.setJournal(k);
        assertThat(vEcriture.getJournal()).isEqualTo(k);
    }

    @Test
    void setJournal() {
        JournalComptable j = new JournalComptable();
        JournalComptable k = new JournalComptable();
        vEcriture.setJournal(j);
        assertThat(vEcriture.getJournal()).isEqualTo(j);
        vEcriture.setJournal(k);
        assertThat(vEcriture.getJournal()).isEqualTo(k);
    }

    @Test
    void getReference() {
        assertThat(vEcriture.getReference()).isEqualTo("AC-1996/00123");
    }

    @Test
    void getDate() {
        Date date = new Date();
        vEcriture.setDate(date);
        assertThat(vEcriture.getDate()).isEqualTo(date);
    }

    @Test
    void setDate() {
        Date date = new Date();
        vEcriture.setDate(date);
        assertThat(vEcriture.getDate()).isEqualTo(date);
    }

    @Test
    void getLibelle() {
        vEcriture.setLibelle("test");
        assertThat(vEcriture.getLibelle()).isEqualTo("test");
    }

    @Test
    void setLibelle() {
        vEcriture.setLibelle("test");
        assertThat(vEcriture.getLibelle()).isEqualTo("test");
    }

    @Test
    void getListLigneEcriture() {
        List<LigneEcritureComptable> data = new ArrayList<>();
        data.add(this.createLigne(1, "200.50", null));
        data.add(this.createLigne(1, "100.50", "33"));
        data.add(this.createLigne(2, null, "301"));
        data.add(this.createLigne(2, "40", "7"));

        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));

        assertThat(vEcriture.getListLigneEcriture().toString()).hasToString(data.toString());
    }

    @Test
    void getTotalDebit() {
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "400", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100", null));
        assertThat(vEcriture.getTotalDebit()).isEqualTo(new BigDecimal(700));
    }

    @Test
    void getTotalCredit() {
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, "200"));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, "400"));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, null, "100"));
        assertThat(vEcriture.getTotalCredit()).isEqualTo(new BigDecimal(700));
    }

    @Test
    void setReference() {
        vEcriture.setReference("AC", "test", new Date(), 3);
        assertThat(vEcriture.getReference()).isEqualTo("AC-2020/00003");
    }
}
