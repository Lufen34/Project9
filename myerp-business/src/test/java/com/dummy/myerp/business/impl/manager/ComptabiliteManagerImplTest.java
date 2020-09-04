package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;


import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ComptabiliteManagerImplTest {

    @Mock
    private ComptabiliteDaoImpl cDao;
    @InjectMocks
    private ComptabiliteManagerImpl manager;

    @BeforeEach
    void init(){
        manager = new ComptabiliteManagerImpl();
    }

    @AfterEach
    void unRef() {
        manager = null;
    }

    @Test
    public void checkEcritureComptableUnit_Libelle() throws Exception {
        EcritureComptable vEcritureComptable;

        vEcritureComptable = new EcritureComptable.Builder()
                .journal(new JournalComptable("AC", "Achat"))
                .date(new Date())
                .libelle("")
                .build();
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                 null, null,
                                                                                    new BigDecimal(123)));
        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableUnit(vEcritureComptable);
        });

    }

    @Test
    public void checkEcritureComptableUnit_IsParamDateValid() throws Exception {
        assertThrows(FunctionalException.class, () -> {
            EcritureComptable vEcritureComptable = new EcritureComptable.Builder()
                    .journal(new JournalComptable("AC", "Achat"))
                    .date(null)
                    .libelle("test")
                    .build();
        });
    }

    @Test
    public void checkEcritureComptableUnit_CheckDateStandard() throws Exception {
        EcritureComptable vEcritureComptable;

        vEcritureComptable = new EcritureComptable.Builder()
                .journal(new JournalComptable("AC", "Achat"))
                .date(new Date())
                .libelle("test")
                .build();
        Calendar cal = Calendar.getInstance();
        cal.setTime(vEcritureComptable.getDate());
        assertThat(cal.get(Calendar.YEAR)).isEqualTo(Integer.parseInt(vEcritureComptable.getReference().substring(3, 7)));
    }

    @Test
    //TODO faire le test que Journal n'est pas vide (comme la Date)
    public void checkEcritureComptableUnit_Journal() throws Exception {
        assertThrows(FunctionalException.class, () -> {
            EcritureComptable vEcritureComptable = new EcritureComptable.Builder()
                    .journal(null)
                    .date(new Date())
                    .libelle("test")
                    .build();
        });
    }

    @Test
    public void checkEcritureComptableUnitViolation() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable.Builder().date(new Date())
                .journal(new JournalComptable("AC", "Des stylos pour gérard"))
                .build();
        assertThrows(FunctionalException.class, ()-> {
            manager.checkEcritureComptableUnit(vEcritureComptable);
        });
    }


    @Test
    public void checkEcritureComptableUnitRG2() throws Exception {
        EcritureComptable vEcritureComptable = new EcritureComptable.Builder()
                .journal(new JournalComptable("AC", "Achat"))
                .date(new Date())
                .libelle("Libelle")
                .build();

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                 null, null,
                                                                                 new BigDecimal(1234)));
        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableUnit(vEcritureComptable);
        });
    }

    @Test
    public void checkEcritureComptableUnitRG3() throws Exception {
        EcritureComptable vEcritureComptable = new EcritureComptable.Builder()
                .journal(new JournalComptable("AC", "Achat"))
                .date(new Date())
                .libelle("Libelle")
                .build();

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableUnit(vEcritureComptable);
        });
    }


    @Test
    public void checkEcritureComptableUnitRG4() throws Exception {
        EcritureComptable vEcritureComptable = new EcritureComptable.Builder()
                .Id(1)
                .journal(new JournalComptable("AC", "Achat"))
                .date(new Date())
                .libelle("Libelle")
                .build();
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                "test", new BigDecimal(123),
                new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                "test",  new BigDecimal(1234),
                new BigDecimal(1234)));
        assertDoesNotThrow(()-> {
           manager.checkEcritureComptableUnit(vEcritureComptable);
        });
    }

    @Test
    public void checkEcritureComptableUnitRG5() throws Exception {
        EcritureComptable vEcritureComptable = new EcritureComptable.Builder()
                .journal(new JournalComptable("BQ", "Achat"))
                .date(new Date())
                .libelle("Libelle")
                .Id(22)
                .build();

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null,  new BigDecimal(1234),
                new BigDecimal(1234)));

        assertDoesNotThrow(()-> {
            manager.checkEcritureComptableUnit(vEcritureComptable);
        });
    }

    @Test
    void getListCompteComptable() {
        fail("not implemented yet");
    }

    @Test
    void getListJournalComptable() {
        fail("not implemented yet");
    }

    @Test
    void getListEcritureComptable() {
        fail("not implemented yet");
    }

    @Test
    void addReference() {

    }

    @Test
    void addReference_isRequestFrom_LastValueFromYearValid() {
        when(cDao.getLastFromSpecificYearSequenceEcritureComptable(2016)).thenReturn(88);

        final int result = manager.getLastFromSpecificYearSequenceEcritureComptable(2016);

        verify(manager).getLastFromSpecificYearSequenceEcritureComptable(2016);
        assertThat(result).isEqualTo(88);
    }

    @Test
    void checkEcritureComptable() {
        fail("not implemented yet");
    }

    @Test
    void checkEcritureComptableContext() {
        fail("not implemented yet");
    }

    @Test
    void insertEcritureComptable() {
        fail("not implemented yet");
    }

    @Test
    void updateEcritureComptable() {
        fail("not implemented yet");
    }

    @Test
    void deleteEcritureComptable() {
        fail("not implemented yet");
    }
}
