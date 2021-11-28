package com.company.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Une classe utilitaire qui est un label sur plusieurs lignes.
 */
public class JMultilineLabel extends JTextArea {
    private static final long serialVersionUID = 1L;
    public JMultilineLabel(String text){
        super(text);
        setEditable(false);
        setCursor(null);
        setOpaque(false);
        setFocusable(false);
        setFont(UIManager.getFont("Label.font"));
        setWrapStyleWord(true);
        setLineWrap(true);
        //According to Mariana this might improve it
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setAlignmentY(JLabel.CENTER_ALIGNMENT);
    }
}