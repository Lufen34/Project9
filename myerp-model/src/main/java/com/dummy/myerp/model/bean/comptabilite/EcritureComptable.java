package com.dummy.myerp.model.bean.comptabilite;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.dummy.myerp.model.exceptions.EmptyStringException;
import com.dummy.myerp.model.exceptions.StringSizeTooBigException;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Bean représentant une Écriture Comptable
 */
public class EcritureComptable {

    Logger logger = LoggerFactory.getLogger(EcritureComptable.class);

    // ==================== Attributs ====================
    /** The Id. */
    private Integer id;
    /** Journal comptable */
    @NotNull private JournalComptable journal;
    /** The Reference. */
    @Pattern(regexp = "^[A-Z]{2}-\\d{4}/\\d{5}")
    private String reference;
    /** The Date. */
    @NotNull private Date date;

    /** The Libelle. */
    @NotNull
    @Size(min = 1, max = 200)
    private String libelle;

    /** La liste des lignes d'écriture comptable. */
    @Valid
    @Size(min = 2)
    private final List<LigneEcritureComptable> listLigneEcriture = new ArrayList<>();

    // ==================== Constructeur ====================
    private EcritureComptable(Builder builder) throws FunctionalException {
        id = builder.id;
        journal = builder.journal;
        date = builder.date;
        libelle = builder.libelle;
        /* Toujours créé la référence en dernier */
        reference = getReference();
    }

    // ==================== Builder ====================
    public static class Builder {

        /** The Id. */
        @NotNull private Integer id;

        /** Journal comptable */
        @NotNull private JournalComptable journal;

        /** The Reference. */
        /* Indépendant de l'utilisateur */
        @Pattern(regexp = "^[A-Z]{2}-\\d{4}/\\d{5}")
        private String reference;

        /** The Date. */
        @NotNull private Date date;

        /** The Libelle. */
        @NotNull
        @Size(min = 1, max = 200)
        private String libelle;

        public Builder Id(int value){
            id = value;
            return this;
        }

        public Builder journal(JournalComptable value) {
            journal = value;
            return this;
        }

        public Builder date(Date value) {
            date = value;
            return this;
        }

        public Builder libelle(String value) {
            libelle = value;
            return this;
        }

        public EcritureComptable build() throws FunctionalException {
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(date);
            } catch (NullPointerException e) {
                e.printStackTrace();
                throw new FunctionalException("Please make sure that you entered a valid Date.");
            }
            if (journal == null)
                throw new FunctionalException("Please ensure that the journal parameter is not null.");
            StringBuilder sb = new StringBuilder();
            sb.append(journal.getCode())
                    .append('-')
                    .append(cal.get(Calendar.YEAR))
                    .append('/')
                    .append(String.format("%05d", id));
            reference = sb.toString();
            return new EcritureComptable(this);
        }
    }

    // ==================== Getters/Setters ====================
    public Integer getId() {
        return id;
    }
    public void setId(Integer pId) {
        id = pId;
    }
    public JournalComptable getJournal() {
        return journal;
    }
    public void setJournal(JournalComptable pJournal) {
        journal = pJournal;
    }
    public String getReference() {
            StringBuilder sb = new StringBuilder();

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            sb.append(journal.getCode())
                    .append('-')
                    .append(cal.get(Calendar.YEAR))
                    .append('/')
                    .append(String.format("%05d", Math.abs(id)));
            return sb.toString();
    }

    public void setReference(String codeJournal, String libelleJournal, Date pDate, int pId) {
        try {
            journal = new JournalComptable(codeJournal, libelleJournal);
        } catch (StringSizeTooBigException e) {
            e.printStackTrace();
        } catch (EmptyStringException e) {
            e.printStackTrace();
        }
        date = pDate;
        id = pId;
        reference = getReference();
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date pDate) {
        date = pDate;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }
    public List<LigneEcritureComptable> getListLigneEcriture() {
        return listLigneEcriture;
    }

    /**
     * Calcul et renvoie le total des montants au débit des lignes d'écriture
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} si aucun montant au débit
     */
    public BigDecimal getTotalDebit() {
        BigDecimal vRetour = BigDecimal.ZERO;
        for (LigneEcritureComptable vLigneEcritureComptable : listLigneEcriture) {
            if (vLigneEcritureComptable.getDebit() != null) {
                vRetour = vRetour.add(vLigneEcritureComptable.getDebit());
            }
        }
        return vRetour;
    }

    /**
     * Calcul et renvoie le total des montants au crédit des lignes d'écriture
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} si aucun montant au crédit
     */
    public BigDecimal getTotalCredit() {
        BigDecimal vRetour = BigDecimal.ZERO;
        for (LigneEcritureComptable vLigneEcritureComptable : listLigneEcriture) {
            if (vLigneEcritureComptable.getCredit() != null) {
                vRetour = vRetour.add(vLigneEcritureComptable.getCredit());
            }
        }
        return vRetour;
    }

    /**
     * Renvoie si l'écriture est équilibrée (TotalDebit = TotalCrédit)
     * @return boolean
     */
    public boolean isEquilibree() {
        return this.getTotalDebit().compareTo(getTotalCredit()) == 0;
    }

    // ==================== Méthodes ====================
    @Override
    public String toString() {
        final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
        final String vSEP = ", ";
        vStB.append("{")
            .append("id=").append(id)
            .append(vSEP).append("journal=").append(journal)
            .append(vSEP).append("reference='").append(reference).append('\'')
            .append(vSEP).append("date=").append(date)
            .append(vSEP).append("libelle='").append(libelle).append('\'')
            .append(vSEP).append("totalDebit=").append(this.getTotalDebit().toPlainString())
            .append(vSEP).append("totalCredit=").append(this.getTotalCredit().toPlainString())
            .append(vSEP).append("listLigneEcriture=[\n")
            .append(StringUtils.join(listLigneEcriture, "\n")).append("\n]")
            .append("}");
        return vStB.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcritureComptable that = (EcritureComptable) o;

        if (!id.equals(that.id)) return false;
        return libelle.equals(that.libelle);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + libelle.hashCode();
        return result;
    }
}
