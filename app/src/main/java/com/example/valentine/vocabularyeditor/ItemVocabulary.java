/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.valentine.vocabularyeditor;

/**
 * @author mollusc <MolluscLab@gmail.com>
 *         Item of the table Stemmator
 */
public class ItemVocabulary {
    //<editor-fold desc="Public Fields">

    /**
     * Stem
     */
    public final String stem;
    /**
     * Word
     */
    public String word;
    /**
     * Translation of the word
     */
    public String translate;
    /**
     * Is word known?
     */
    public boolean known;
    /**
     * Is word study?
     */
    public boolean study;
    /**
     * Number of meeting in the subtitle
     */
    public int meeting;
    //</editor-fold>

    //<editor-fold desc="Constructor">

    /**
     * Constructor of the class ItemVocabulary
     *
     * @param word      word
     * @param translate translation of the word
     * @param known     Is word known?
     * @param meeting   number of meeting in the subtitle
     * @param study     Is word study?
     */
    public ItemVocabulary(String stem, String word, String translate, boolean known, int meeting, boolean study) {
        this.stem = stem;
        this.word = word;
        this.translate = translate;
        this.known = known;
        this.meeting = meeting;
        this.study = study;
    }
    //</editor-fold>
}
