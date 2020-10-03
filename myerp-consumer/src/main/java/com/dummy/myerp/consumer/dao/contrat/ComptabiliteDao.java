package com.dummy.myerp.consumer.dao.contrat;

import java.util.List;

import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;


/**
 * Interface de DAO des objets du package Comptabilite
 */
public interface ComptabiliteDao {

    /**
     * Renvoie la liste des Comptes Comptables
     * @return {@link List}
     */
    List<CompteComptable> getListCompteComptable();


    /**
     * Renvoie la liste des Journaux Comptables
     * @return {@link List}
     */
    List<JournalComptable> getListJournalComptable();


    // ==================== EcritureComptable ====================

    /**
     * Renvoie la liste des Écritures Comptables
     * @return {@link List}
     */
    List<EcritureComptable> getListEcritureComptable();

    /**
     * Renvoie l'Écriture Comptable d'id {@code pId}.
     *
     * @param pId l'id de l'écriture comptable
     * @return {@link EcritureComptable}
     * @throws NotFoundException : Si l'écriture comptable n'est pas trouvée
     */
    EcritureComptable getEcritureComptable(Integer pId) throws NotFoundException;

    /**
     * Renvoie l'Écriture Comptable de référence {@code pRef}.
     *
     * @param pReference la référence de l'écriture comptable
     * @return {@link EcritureComptable}
     * @throws NotFoundException : Si l'écriture comptable n'est pas trouvée
     */
    EcritureComptable getEcritureComptableByRef(String pReference) throws NotFoundException;

    /**
     * Charge la liste des lignes d'écriture de l'écriture comptable {@code pEcritureComptable}
     *
     * @param pEcritureComptable -
     */
    void loadListLigneEcriture(EcritureComptable pEcritureComptable);

    /**
     * Insert une nouvelle écriture comptable.
     *
     * @param pEcritureComptable -
     */
    void insertEcritureComptable(EcritureComptable pEcritureComptable);

    /**
     * Met à jour l'écriture comptable.
     *
     * @param pEcritureComptable -
     */
    void updateEcritureComptable(EcritureComptable pEcritureComptable);

    /**
     * Supprime l'écriture comptable d'id {@code pId}.
     *
     * @param pId l'id de l'écriture
     */
    void deleteEcritureComptable(Integer pId);

    /**
     * Renvoie la liste des Sequence Ecriture Comptable
     * @return {@link List}
     */
    List<SequenceEcritureComptable> getListSequenceEcritureComptable();

    /**
     * Met à jour l'écriture comptable.
     *
     * @param pSequenceEcritureComptable -
     */
    void updateSequenceEcritureComptable(SequenceEcritureComptable pSequenceEcritureComptable);

    /**
     * Insere une séquence ecriture comptable
     * 
     * @param pSequenceEcritureComptable la séquence à inserer
     */
    void insertSequenceEcritureComptable(SequenceEcritureComptable pSequenceEcritureComptable);

    /**
     * Renvoie la derniere valeur dans la table en fonction de l'année
     * @param pYear l'année de la sequence
     * @return la derniere valeur de l'année saisie
     */
    Integer getLastFromSpecificYearSequenceEcritureComptable(Integer pYear);

    /**
     * Renvoie la list de LigneEcritureComptable dans la table en fonction de l'id
     * @param pId l'id de la ligne d'écriture comptable
     * @return la liste ligne ecriture comptable
     */
    List<LigneEcritureComptable> getListLigneEcritureComptable(Integer pId);
}
