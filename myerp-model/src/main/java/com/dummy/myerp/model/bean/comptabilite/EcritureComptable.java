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
    @Pattern(regexp = "\\d{1,5}-\\d{4}/\\d{5}")
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
    private EcritureComptable(Builder builder) {
        id = builder.id;
        journal = builder.journal;
        reference = builder.reference;
        date = builder.date;
        libelle = builder.libelle;
    }

    // ==================== Builder ====================
    public static class Builder {

        /** The Id. */
        private Integer id;

        /** Journal comptable */
        @NotNull private JournalComptable journal;

        /** The Reference. */
        @Pattern(regexp = "\\d{1,5}-\\d{4}/\\d{5}")
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

        public Builder reference(String value) {
            reference = value;
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

        public EcritureComptable build() {
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
                .append(String.format("%05d", id));
        return sb.toString();
    }
    public void setReference(String pReference) {
        reference = pReference;
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
}
