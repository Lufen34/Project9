package com.dummy.myerp.model.bean.comptabilite;

import com.dummy.myerp.model.exceptions.EmptyStringException;
import com.dummy.myerp.model.exceptions.StringSizeTooBigException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JournalComptableTest {

    private JournalComptable journal;
    private List<JournalComptable> journalList;

    @BeforeAll
    void init_List() {
        journalList = new ArrayList<>();
        try {
            journalList.add(new JournalComptable("1234", "test"));
            journalList.add(new JournalComptable("2345", "test"));
            journalList.add(new JournalComptable("3456", "test"));
            journalList.add(new JournalComptable("4567", "test"));
        } catch (StringSizeTooBigException | EmptyStringException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void init_journal(){
        try {
            journal = new JournalComptable("12543", "test");
        } catch (StringSizeTooBigException | EmptyStringException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void unRef(){
        journal = null;
    }

    @Test
    void getCode() {
        assertThat(journal.getCode()).isEqualTo("12543");
        try {
            journal = new JournalComptable("19", "test");
        } catch (StringSizeTooBigException | EmptyStringException e) {
            e.printStackTrace();
        }
        assertThat(journal.getCode()).isEqualTo("19");
        try {
            journal = new JournalComptable("125", "test");
        } catch (StringSizeTooBigException | EmptyStringException e) {
            e.printStackTrace();
        }
        assertThat(journal.getCode()).isEqualTo("125");
        try {
            journal = new JournalComptable("1", "test");
        } catch (StringSizeTooBigException | EmptyStringException e) {
            e.printStackTrace();
        }
        assertThat(journal.getCode()).isEqualTo("1");
    }

    @Test
    void getCode_NotTooBig(){
        StringSizeTooBigException e = assertThrows(StringSizeTooBigException.class, ()->
                    journal = new JournalComptable("11414464", "test")
                );
        assertThat(e.getMessage()).contains("Code length is superior to 5 value.");
    }

    @Test
    void getCode_IsCodeNotEmpty(){
        EmptyStringException e = assertThrows(EmptyStringException.class, ()->
                journal = new JournalComptable("", "test")
        );
        assertThat(e.getMessage()).contains("Code length is empty ensure that you have at least 1 character.");
    }

    @Test
    void setCode_NotTooBig() {
        StringSizeTooBigException e = assertThrows(StringSizeTooBigException.class, ()->
                journal.setCode("123456")
        );
        assertThat(e.getMessage()).contains("Code length is superior to 5 value.");
    }

    @Test
    void setCode_IsNotEmpty() {
        EmptyStringException e = assertThrows(EmptyStringException.class, ()->
                journal.setCode("")
        );
        assertThat(e.getMessage()).contains("Code length is empty ensure that you have at least 1 character.");
    }

    @Test
    void getLibelle() {
        assertThat(journal.getLibelle()).isEqualTo("test");
    }

    @Test
    void getLibelle_NotTooBig(){
        StringSizeTooBigException e = assertThrows(StringSizeTooBigException.class, ()->
                journal = new JournalComptable("1141", "testezfffffffffffffffffffffffffffffffffffffffffffffffffzefezfz" +
                        "efzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzfezzzzzzzzzzzzzzzzzzzzzzzzzefz"+
                        "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                        "efzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                        "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                        "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                        "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                        "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                        "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                        "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                        "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                        "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                        "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                        "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                        "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                        "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz")
        );
        assertThat(e.getMessage()).contains("libelle length is superior to max length(150).");
    }

    @Test
    void getLibelle_IsNotEmpty(){
        EmptyStringException e = assertThrows(EmptyStringException.class, ()->
                journal = new JournalComptable("124", "")
        );
        assertThat(e.getMessage()).contains("libelle length is empty ensure that you have at least 1 character.");
    }

    @Test
    void setLibelle() {
        try {
            journal.setLibelle("1234");
        } catch (EmptyStringException | StringSizeTooBigException e) {
            e.printStackTrace();
        }
        assertThat(journal.getLibelle()).isEqualTo("1234");
    }

    @Test
    void setLibelle_NotTooBig() {
        StringSizeTooBigException e = assertThrows(StringSizeTooBigException.class, ()->
                journal.setLibelle("testezfffffffffffffffffffffffffffffffffffffffffffffffffzefezfz" +
                                "efzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzfezzzzzzzzzzzzzzzzzzzzzzzzzefz"+
                                "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                                "efzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                                "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                                "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                                "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                                "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                                "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                                "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                                "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                                "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                                "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                                "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                                "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"+
                                "fezzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz")
        );
        assertThat(e.getMessage()).contains("libelle length is superior to max length(150).");
    }

    @Test
    void getByCode() {
        assertThat(JournalComptable.getByCode(journalList, "1234").toString()).hasToString(journalList.get(0).toString());
        assertThat(JournalComptable.getByCode(journalList, "2345").toString()).hasToString(journalList.get(1).toString());
        assertThat(JournalComptable.getByCode(journalList, "3456").toString()).hasToString(journalList.get(2).toString());
        assertThat(JournalComptable.getByCode(journalList, "4567").toString()).hasToString(journalList.get(3).toString());
        assertThat(JournalComptable.getByCode(journalList, "5678")).isNull();
        assertThat(JournalComptable.getByCode(journalList, "6789")).isNull();

    }

    @ParameterizedTest
    @ValueSource(ints = {1, 4, 33, 11, 22})
    void getByCode_IfListIsNull_ThrowIndexOutOfBoundsException(int arg){
        List<JournalComptable> listTestNull = new ArrayList<>();
        IndexOutOfBoundsException e = assertThrows(IndexOutOfBoundsException.class, ()->
                listTestNull.get(arg)
        );
        assertThat(e.getMessage()).isEqualTo("Index " + arg + " out of bounds for length 0");
    }


}