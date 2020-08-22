package com.dummy.myerp.model.bean.comptabilite;

import com.dummy.myerp.model.exceptions.EmptyStringException;
import com.dummy.myerp.model.exceptions.StringSizeTooBigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Bean représentant un Journal Comptable
 */
public class JournalComptable {

    Logger logger = LoggerFactory.getLogger(JournalComptable.class);

    // ==================== Attributs ====================
    /** code */
    @NotNull
    @Size(min = 1, max = 5)
    private String code;

    /** libelle */
    @NotNull
    @Size(min = 1, max = 150)
    private String libelle;


    // ==================== Constructeurs ====================
    /**
     * Instantiates a new Journal comptable.
     */
    public JournalComptable() {
    }

    /**
     * Instantiates a new Journal comptable.
     *
     * @param pCode the p code
     * @param pLibelle the p libelle
     */
    public JournalComptable(String pCode, String pLibelle) throws StringSizeTooBigException, EmptyStringException{
        code = conformCodeValue(pCode);
        libelle = conformLibelledValue(pLibelle);
    }


    // ==================== Getters/Setters ====================
    public String getCode() {
        return code;
    }
    public void setCode(String pCode) throws StringSizeTooBigException, EmptyStringException {
            code = conformCodeValue(pCode);
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String pLibelle) throws EmptyStringException, StringSizeTooBigException {
        libelle = conformLibelledValue(pLibelle);
    }


    // ==================== Méthodes ====================
    @Override
    public String toString() {
        final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
        final String vSEP = ", ";
        vStB.append("{")
            .append("code='").append(code).append('\'')
            .append(vSEP).append("libelle='").append(libelle).append('\'')
            .append("}");
        return vStB.toString();
    }

    private String conformCodeValue(String code) throws StringSizeTooBigException, EmptyStringException {
        if (code.length() > 5) throw  new StringSizeTooBigException("Code length is superior to 5 value.");
        else if (code.length() <= 0) throw  new EmptyStringException("Code length is empty ensure that you have at least 1 character.");
        return code;
    }

    private String conformLibelledValue(String libelle) throws StringSizeTooBigException, EmptyStringException {
        if (libelle.length() > 150) throw  new StringSizeTooBigException("libelle length is superior to max length(150).");
        else if (libelle.length() <= 0) throw  new EmptyStringException("libelle length is empty ensure that you have at least 1 character.");
        return libelle;
    }

    // ==================== Méthodes STATIC ====================
    /**
     * Renvoie le {@link JournalComptable} de code {@code pCode} s'il est présent dans la liste
     *
     * @param pList la liste où chercher le {@link JournalComptable}
     * @param pCode le code du {@link JournalComptable} à chercher
     * @return {@link JournalComptable} ou {@code null}
     */
    public static JournalComptable getByCode(List<? extends JournalComptable> pList, String pCode) {
        JournalComptable vRetour = null;
        for (JournalComptable vBean : pList) {
            if (vBean != null && (vBean.getCode().equals(pCode))) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }

}
