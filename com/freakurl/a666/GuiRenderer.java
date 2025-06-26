package com.freakurl.a666;

import com.freakurl.engine.FreakEngine;
import com.freakurl.engine.Frame;
import com.freakurl.engine.EngineException;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.nio.*;
import java.util.Scanner;
/**
 * GUI-Version des Spiels.
 * 
 * @author Kitan, Julian
 */
public class GuiRenderer extends JFrame {
    public JTextField textField;
    JButton submit;
    File imageFile;
    static Integer input;
    public GuiRenderer(){}

    /** 
     * Fügt eine GUI hinzu. Diese besteht aus einem Text-Feld und einem Bild.
     * @param frame der zu ladende Frame, wird von Engine übergeben.
     */
    public void render(Frame frame) throws EngineException {
        handleImage(frame);
        JFrame backroundFrame = new JFrame(frame.title);
        backroundFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        backroundFrame.setLayout(null);
        backroundFrame.setSize(500,500);
        ImageIcon image = new ImageIcon(imageFile.getAbsolutePath());
        backroundFrame.add(new JLabel(image));

        textField = new JTextField();
        textField.setBounds(50, 20, 200, 25);
        backroundFrame.add(textField);

        
        submit = new JButton("Bestätigen");
        submit.setBounds(100, 60, 100, 30);
        backroundFrame.add(submit);
        submit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String userInput = textField.getText();
                    textField.setText("");
                    input = Integer.parseInt(userInput);
                }
            });
        backroundFrame.add(submit);

        if (frame.sound.isPresent()) {
            File fileWav = frame.sound.get();
            SoundRenderer renderer = new SoundRenderer(fileWav);
            renderer.renderWav();

        }   
    }
    
    /**
     * Überprüft ob ein Bild im Frame vorhanden ist und passt dementsprechend das zu rendernde Bild an.
     * @param frame der aktuelle Frame, welcher zu rendern ist.
     */
    private void handleImage(Frame frame) {
        if (frame.image.isPresent()){
            imageFile = frame.image.get();
            return ;
        }

        else {
            return ;   
        }
    }
}