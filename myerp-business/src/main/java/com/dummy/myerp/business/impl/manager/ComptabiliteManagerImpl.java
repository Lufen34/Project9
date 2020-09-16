package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.dummy.myerp.business.contrat.BusinessProxy;
import com.dummy.myerp.business.impl.BusinessProxyImpl;
import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.contrat.DaoProxy;
import com.dummy.myerp.consumer.dao.impl.DaoProxyImpl;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.model.exceptions.InvalidYearException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;


/**
 * Comptabilite manager implementation.
 */
public class ComptabiliteManagerImpl extends AbstractBusinessManager implements ComptabiliteManager {

    // ==================== Attributs ====================
    private Logger logger = LoggerFactory.getLogger(ComptabiliteManagerImpl.class);

    // ==================== Constructeurs ====================
    /**
     * Instantiates a new Comptabilite manager.
     */
    public ComptabiliteManagerImpl() {
        DaoProxy vDaoProxy = DaoProxyImpl.getInstance();
        BusinessProxy vBusinessProxy = BusinessProxyImpl.getInstance(vDaoProxy, TransactionManager.getInstance());

        configure(vBusinessProxy, vDaoProxy, TransactionManager.getInstance());
    }


    // ==================== Getters/Setters ====================
    @Override
    public List<CompteComptable> getListCompteComptable() {
        return getDaoProxy().getComptabiliteDao().getListCompteComptable();
    }

    @Override
    public List<SequenceEcritureComptable> getListSequenceEcritureComptable() {
        return getDaoProxy().getComptabiliteDao().getListSequenceEcritureComptable();
    }

    @Override
    public Integer getLastFromSpecificYearSequenceEcritureComptable(Integer pYear){
        return getDaoProxy().getComptabiliteDao().getLastFromSpecificYearSequenceEcritureComptable(pYear);
    }

    @Override
    public List<JournalComptable> getListJournalComptable() {
        return getDaoProxy().getComptabiliteDao().getListJournalComptable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        return getDaoProxy().getComptabiliteDao().getListEcritureComptable();
    }

    /**
     * {@inheritDoc}
     */
    // TODO à tester
    @Override
    public synchronized SequenceEcritureComptable addReference(EcritureComptable pEcritureComptable) {
        // Bien se réferer à la JavaDoc de cette méthode !
        /* Le principe :
                1.  Remonter depuis la persitance la dernière valeur de la séquence du journal pour l'année de l'écriture
                    (table sequence_ecriture_comptable)
                2.  * S'il n'y a aucun enregistrement pour le journal pour l'année concernée :
                        1. Utiliser le numéro 1.
                    * Sinon :
                        1. Utiliser la dernière valeur + 1
                3.  Mettre à jour la référence de l'écriture avec la référence calculée (RG_Compta_5)
                4.  Enregistrer (insert/update) la valeur de la séquence en persitance
                    (table sequence_ecriture_comptable)
         */
        Calendar cal = Calendar.getInstance();
        cal.setTime(pEcritureComptable.getDate());

        /* === Enregistre dans la bdd la sequence d'écriture comptable === */
        /* vérifie qu l'on a bien une valeur pour l'année concernée */
        if (getLastFromSpecificYearSequenceEcritureComptable(cal.get(Calendar.YEAR)) != null){
            SequenceEcritureComptable seq = null;
            try {
                /* On récupère la dernière valeur de la sequence pour l'année et rajoutons + 1 à derniere_valeur */
                seq = new SequenceEcritureComptable(cal.get(Calendar.YEAR),
                        getLastFromSpecificYearSequenceEcritureComptable(cal.get(Calendar.YEAR) + 1));
            } catch (InvalidYearException e) {
                    e.printStackTrace();
            }
            /* on vérifie si l'écriture comptable est toujours conformes aux standards */
            try {
                checkEcritureComptable(pEcritureComptable);
            } catch (FunctionalException e) {
                e.printStackTrace();
            }
            /* on update l'entité de l'écriture comptable dans la bdd */
            getDaoProxy().getComptabiliteDao().updateEcritureComptable(pEcritureComptable);
            /* on insère l'entité de la sequence d'écriture dans la bdd */
            getDaoProxy().getComptabiliteDao().insertSequenceEcritureComptable(seq);
            return seq;
        }

        /* Dans le cas où nous n'avons rien pour l'année concernée, nous créons une entité dans la table séquence. */
        else {
            SequenceEcritureComptable seq = null;
            try {
                /* On récupère la dernière valeur de la sequence pour l'année et rajoutons + 1 à derniere_valeur */
                seq = new SequenceEcritureComptable(cal.get(Calendar.YEAR), 1);
            } catch (InvalidYearException e) {
                e.printStackTrace();
            }
            /* on vérifie si l'écriture comptable est toujours conformes aux standards */
            try {
                checkEcritureComptable(pEcritureComptable);
            } catch (FunctionalException e) {
                e.printStackTrace();
            }
            /* on update l'entité de l'écriture comptable dans la bdd */
            getDaoProxy().getComptabiliteDao().updateEcritureComptable(pEcritureComptable);
            /* on insère l'entité de la sequence d'écriture dans la bdd */
            getDaoProxy().getComptabiliteDao().insertSequenceEcritureComptable(seq);
            return seq;
        }
    }

    /**
     * {@inheritDoc}
     */
    // TODO à tester
    @Override
    public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptableUnit(pEcritureComptable);
        this.checkEcritureComptableContext(pEcritureComptable);
    }


    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion unitaires,
     * c'est à dire indépendemment du contexte (unicité de la référence, exercie comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    protected void checkEcritureComptableUnit(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== RG_Compta_1 : Vérification des contraintes unitaires sur les attributs de l'écriture
        Set<ConstraintViolation<EcritureComptable>> vViolations = getConstraintValidator().validate(pEcritureComptable);
        if (!vViolations.isEmpty()) {
            throw new FunctionalException("L'écriture comptable ne respecte pas les règles de gestion.",
                                          new ConstraintViolationException(
                                              "L'écriture comptable ne respecte pas les contraintes de validation",
                                              vViolations));
        }

        // ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
        if (!pEcritureComptable.isEquilibree()) {
            throw new FunctionalException("L'écriture comptable n'est pas équilibrée.");
        }

        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
        int vNbrCredit = 0;
        int vNbrDebit = 0;
        for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture()) {
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(),
                                                                    BigDecimal.ZERO)) != 0) {
                vNbrCredit++;
            }
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(),
                                                                    BigDecimal.ZERO)) != 0) {
                vNbrDebit++;
            }
        }

        if (pEcritureComptable.getListLigneEcriture().size() < 2
            || vNbrCredit < 1
            || vNbrDebit < 1) {
            throw new FunctionalException(
                "L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit.");
        }

        // ===== RG_Compta_5 : Format et contenu de la référence
        // La date de l'ecriture et la date de la reference doivent correspondre.
        Calendar dateEcriture = Calendar.getInstance();
        dateEcriture.setTime(pEcritureComptable.getDate());
        String yearFromRef = "";
        for (int i = 0; i < pEcritureComptable.getReference().length(); i++) {
            if (pEcritureComptable.getReference().charAt(i) == '-') {
                yearFromRef = pEcritureComptable.getReference().substring(i+1, i+5);
                break;
            }
        }
        if (dateEcriture.get(Calendar.YEAR) != Integer.parseInt(yearFromRef))
            throw new FunctionalException("La date de l'ecriture et la date de la reference doivent correspondre.");

    }


    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion liées au contexte
     * (unicité de la référence, année comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */
    protected void checkEcritureComptableContext(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
            try {
                // Recherche d'une écriture ayant la même référence
                EcritureComptable vECRef = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(
                    pEcritureComptable.getReference());

                // Si l'écriture à vérifier est une nouvelle écriture (id == null),
                // ou si elle ne correspond pas à l'écriture trouvée (id != idECRef), /!\ UPDATED : ou si elle correspond à l'écriture trouvée.
                // c'est qu'il y a déjà une autre écriture avec la même référence
                if (pEcritureComptable.getId() == null
                    || pEcritureComptable.getId().equals(vECRef.getId())) {
                    throw new FunctionalException("Une autre écriture comptable existe déjà avec la même référence.");
                }
            } catch (NotFoundException vEx) {
                // Dans ce cas, c'est bon, ça veut dire qu'on n'a aucune autre écriture avec la même référence.
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptable(pEcritureComptable);
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().insertEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().updateEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEcritureComptable(Integer pId) {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().deleteEcritureComptable(pId);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }
}
