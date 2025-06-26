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
import static javax.swing.JOptionPane.showMessageDialog;
/**
 * GUI-Version des Spiels.
 * 
 * @author Kitan
 * @author Julian
 */
public class GuiRenderer extends JFrame {
    File imageFile;
    static Integer enteredId;
    private FreakEngine engine;
    private JTextArea textArea;
    private JTextField inputField;

    public GuiRenderer() throws EngineException{
        try{
            engine = new FreakEngine();
            render(0, null);

        }

        catch (Exception e) {
            showMessageDialog(null, "Critical error, panicked: " + e.getMessage());
        } 

    }

    /** 
     * Fügt eine GUI hinzu. Diese besteht aus einem Text-Feld und einem Bild.
     * @param id die ID des zu ladenden Frames.
     * @param error eine evlt mögliche Errormeldung.
     */
    private void render(int id, String error) throws EngineException {

        setLayout(new BorderLayout());
        Integer input = 0;
        var frame = engine.getFrame(id);
        handleImage(frame);

        // ----------- Image Panel ------------

        JLabel imageLabel = new JLabel(); 
        ImageIcon imageIcon = new ImageIcon(imageFile.getAbsolutePath());
        imageLabel.setIcon(imageIcon); 
        JPanel imagePanel = new JPanel(); 
        imagePanel.add(imageLabel);

        // ----------- Text Area Panel ------------

        StringBuilder options = new StringBuilder("\n\n\n\n Options:"); 
        for (int i = 0; i < frame.options.size(); i++){
            options.append( i + ");\n");
        }

        textArea = new JTextArea("==" + frame.title + "==\n" + frame.text + options); 
        textArea.setText("==" + frame.title + "==\n" + frame.text + options);
        setSize(500,500);
        setVisible(true);
        JPanel textPanel = new JPanel(); 

        // ----------- Input Panel ------------

        inputField = new JTextField(100);
        JButton confirmButton = new JButton("Bestätigen");

        confirmButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String UserInput = inputField.getText();
                    inputField.setText("");
                    int input = Integer.parseInt(UserInput);
                }
            });

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.add(inputField);
        inputPanel.add(confirmButton);

        // ------------ Layout anpassen ------------

        add(imagePanel, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // ------------ Fertigstellen ------------

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Fenster im Zentrum
        pack();
        setSize(1500, 1000);
        setVisible(true);

        if(frame.sound.isPresent()){

            SoundRenderer renderer = new SoundRenderer();
            renderer.renderWav(frame);
        }

        if (error != null) {
            showMessageDialog(null, error);
            setVisible(false);
            dispose();
        }

        try {
            enteredId = input;
        } catch (Exception e) {
            enteredId = null;
        }

        if (enteredId == null || enteredId < 0 || enteredId >= frame.options.size()) {
            render(id, "Ungültige Eingabe.");

            render(frame.options.get(enteredId).to, null);
        }
    }

    /**
     * Überprüft ob ein Bild im Frame vorhanden ist und passt dementsprechend das zu rendernde Bild an.
     * @param frame der aktuelle Frame, welcher zu rendern ist.
     */
    private void handleImage(Frame frame) {
        if (frame.image.isPresent() == true) {
            imageFile = frame.image.get();
        }
        return ;
    }
}