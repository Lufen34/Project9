package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.exceptions.EmptyStringException;
import com.dummy.myerp.model.exceptions.StringSizeTooBigException;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.testbusiness.business.SpringRegistry;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComptabiliteManagerImplTest {

    @Mock
    private ComptabiliteDaoImpl cDao;
    @Mock
    private DaoProxy daoProxy;
    @Mock
    private TransactionManager transactionManager;
    @Mock
    private BusinessProxy businessProxy;
    @Mock
    private List<CompteComptable> compteComptableMockedList;
    @Mock
    private List<JournalComptable> journalComptableMockedList;
    @Mock
    private List<EcritureComptable> ecritureComptableMockedList;
    @InjectMocks
    private final ComptabiliteManagerImpl managerUnderTest = new ComptabiliteManagerImpl();;


    @BeforeAll
    static void initContext(){
        SpringRegistry.init();
    }

    @BeforeEach
    void init(){
       // AbstractBusinessManager.configure(businessProxy, daoProxy, transactionManager);
    }

    @AfterEach
    void unRef() {
        //managerUnderTest = null;
    }

    @Test
    public void checkEcritureComptableUnit_Libelle() throws Exception {
        EcritureComptable vEcritureComptable;

        vEcritureComptable = new EcritureComptable.Builder()
                .Id(-6)
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
            managerUnderTest.checkEcritureComptableUnit(vEcritureComptable);
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
                .Id(-7)
                .journal(new JournalComptable("AC", "Achat"))
                .date(new Date())
                .libelle("test")
                .build();
        Calendar cal = Calendar.getInstance();
        cal.setTime(vEcritureComptable.getDate());
        assertThat(cal.get(Calendar.YEAR)).isEqualTo(Integer.parseInt(vEcritureComptable.getReference().substring(3, 7)));
    }

    @Test
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
                .Id(-5)
                .journal(new JournalComptable("AC", "Des stylos pour gérard"))
                .build();
        assertThrows(FunctionalException.class, ()-> {
            managerUnderTest.checkEcritureComptableUnit(vEcritureComptable);
        });
    }


    @Test
    public void checkEcritureComptableUnitRG2() throws Exception {
        EcritureComptable vEcritureComptable = new EcritureComptable.Builder()
                .Id(-5)
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
            managerUnderTest.checkEcritureComptableUnit(vEcritureComptable);
        });
    }

    @Test
    public void checkEcritureComptableUnitRG3() throws Exception {
        EcritureComptable vEcritureComptable = new EcritureComptable.Builder()
                .Id(-5)
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
            managerUnderTest.checkEcritureComptableUnit(vEcritureComptable);
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
           managerUnderTest.checkEcritureComptableUnit(vEcritureComptable);
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
                "test", new BigDecimal(123),
                new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                "test",  new BigDecimal(1234),
                new BigDecimal(1234)));
        assertDoesNotThrow(()-> {
            managerUnderTest.checkEcritureComptableUnit(vEcritureComptable);
        });
    }

    // TODO en discuter avec mon mentor
    @Test
    void getListCompteComptable_CheckBySize() {

       when(compteComptableMockedList.size()).thenReturn(7);

        final int resultSize = managerUnderTest.getListCompteComptable().size();
        final int expectedSize = compteComptableMockedList.size();

        assertThat(resultSize).isEqualTo(expectedSize);
        verify(compteComptableMockedList).size();
    }

    @Test
    void getListCompteComptable_CheckByNumero() {
        when(compteComptableMockedList.get(0)).thenReturn(new CompteComptable(401));

        final int resultNumero = managerUnderTest.getListCompteComptable().get(0).getNumero();
        final int expectedNumero = compteComptableMockedList.get(0).getNumero();

        assertThat(resultNumero).isEqualTo(expectedNumero);
        verify(compteComptableMockedList).get(0);
    }

    @Test
    void getListJournalComptable_CheckBySize() {

        when(journalComptableMockedList.size()).thenReturn(4);

        final int resultSize = managerUnderTest.getListJournalComptable().size();
        final int expectedSize = journalComptableMockedList.size();

        assertThat(resultSize).isEqualTo(expectedSize);
        verify(journalComptableMockedList).size();
    }

    @Test
    void getListJournalComptable_CheckByLibelle() throws EmptyStringException, StringSizeTooBigException {
        when(journalComptableMockedList.get(0)).thenReturn(new JournalComptable("ac", "Achat"));

        final String resultLibelle = managerUnderTest.getListJournalComptable().get(0).getLibelle();
        final String expectedLibelle = journalComptableMockedList.get(0).getLibelle();

        assertThat(resultLibelle).isEqualTo(expectedLibelle);
        verify(journalComptableMockedList).get(0);
    }

    @Test
    void getListEcritureComptable_CheckBySize() {
        when(ecritureComptableMockedList.size()).thenReturn(5);

        final int resultSize = managerUnderTest.getListEcritureComptable().size();
        final int expectedSize = ecritureComptableMockedList.size();

        assertThat(resultSize).isEqualTo(expectedSize);
        verify(ecritureComptableMockedList).size();
    }

    @Test
    void addReference() {
        fail("not implemented yet");
    }

    //TODO Talk with my mentor about how ungeneralized this test is.
    @Test
    void addReference_isRequestFrom_LastValueFromYearValid() {
        when(cDao.getLastFromSpecificYearSequenceEcritureComptable(2016)).thenReturn(88);

        final int result = managerUnderTest.getLastFromSpecificYearSequenceEcritureComptable(2016);
        final int expected = cDao.getLastFromSpecificYearSequenceEcritureComptable(2016);

        assertThat(result).isEqualTo(expected);
        // verify that the behavior happened
        verify(cDao).getLastFromSpecificYearSequenceEcritureComptable(2016);
    }

    @Test
    void checkEcritureComptable() throws EmptyStringException, StringSizeTooBigException, FunctionalException {
        EcritureComptable vEcritureComptable = new EcritureComptable.Builder()
                .Id(11)
                .journal(new JournalComptable("AC", "Achat"))
                .date(new Date())
                .libelle("des stylos")
                .build();
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null,  new BigDecimal(1234),
                new BigDecimal(1234)));

        assertDoesNotThrow(()-> {
            managerUnderTest.checkEcritureComptable(vEcritureComptable);
        });
    }

    @Test
    void checkEcritureComptable_Exception() throws EmptyStringException, StringSizeTooBigException, FunctionalException {
        EcritureComptable vEcritureComptable = new EcritureComptable.Builder()
                .Id(11)
                .journal(new JournalComptable("AC", "Achat"))
                .date(new Date())
                .libelle("") // empty
                .build();
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null,  new BigDecimal(1234),
                new BigDecimal(1234)));

        assertThrows(Exception.class, () -> {
           managerUnderTest.checkEcritureComptable(vEcritureComptable);
        });
    }

    @Test
    void checkEcritureComptableContext_IfEcritureExists() throws EmptyStringException, StringSizeTooBigException, FunctionalException {
        Calendar date = Calendar.getInstance();
        date.set(2016, Calendar.DECEMBER, 31);
        Date specificDate = date.getTime();

        EcritureComptable vEcritureComptable = new EcritureComptable.Builder()
                .Id(-1)
                .journal(new JournalComptable("AC", "Achat"))
                .date(specificDate)
                .libelle("Cartouches d'imprimante") // em
                .build();
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null,  new BigDecimal(1234),
                new BigDecimal(1234)));
        assertThrows(Exception.class, ()->{
            managerUnderTest.checkEcritureComptableContext(vEcritureComptable);
        });
    }

    @Test
    void insertEcritureComptable() throws FunctionalException, EmptyStringException, StringSizeTooBigException {
        EcritureComptable vEcritureComptable = new EcritureComptable.Builder()
                .Id(-6)
                .journal(new JournalComptable("AC", "Achat"))
                .date(new Date())
                .libelle("test")
                .build();
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                new BigDecimal(123)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null,  new BigDecimal(1234),
                new BigDecimal(1234)));

        managerUnderTest.insertEcritureComptable(vEcritureComptable);
        assertThat(managerUnderTest.getListEcritureComptable().contains(vEcritureComptable)).isTrue();
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
