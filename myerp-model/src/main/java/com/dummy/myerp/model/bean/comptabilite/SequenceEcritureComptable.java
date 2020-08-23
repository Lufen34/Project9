package com.dummy.myerp.model.bean.comptabilite;


import com.dummy.myerp.model.exceptions.InvalidYearException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Bean représentant une séquence pour les références d'écriture comptable
 */
public class SequenceEcritureComptable {

    // ==================== Attributs ====================
    /** L'année */
    private Integer annee;
    /** La dernière valeur utilisée */
    private Integer derniereValeur;

    // ==================== Constructeurs ====================
    /**
     * Constructeur
     */
    public SequenceEcritureComptable() {
    }

    /**
     * Constructeur
     *
     * @param pAnnee -
     * @param pDerniereValeur -
     */
    public SequenceEcritureComptable(Integer pAnnee, Integer pDerniereValeur) throws InvalidYearException {
        annee = checkYear(pAnnee);
        derniereValeur = pDerniereValeur;
    }


    // ==================== Getters/Setters ====================
    public Integer getAnnee() {
        return annee;
    }
    public void setAnnee(Integer pAnnee) throws InvalidYearException {
        annee = checkYear(pAnnee);
    }
    public Integer getDerniereValeur() {
        return derniereValeur;
    }
    public void setDerniereValeur(Integer pDerniereValeur) {
        derniereValeur = pDerniereValeur;
    }


    // ==================== Méthodes ====================
    @Override
    public String toString() {
        final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
        final String vSEP = ", ";
        vStB.append("{")
            .append("annee=").append(annee)
            .append(vSEP).append("derniereValeur=").append(derniereValeur)
            .append("}");
        return vStB.toString();
    }

    public Integer checkYear(int year) throws InvalidYearException {
        Date actualDate = new Date();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
        calendar.setTime(actualDate);

        if (year > calendar.get(Calendar.YEAR)) throw new InvalidYearException("The year provided is in the future, which is impossible.");

        return year;
    }
}
