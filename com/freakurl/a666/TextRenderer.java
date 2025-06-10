package com.freakurl.a666;

import java.util.Scanner; 

import com.freakurl.engine.FreakEngine;
import com.freakurl.engine.EngineException;

/**
 * Text-only-Version des Spiels.
 * 
 * @author Kitan
 */
public class TextRenderer {
    FreakEngine engine;
    public TextRenderer() {
        try {
            engine = new FreakEngine();
            render(0, null);
        } catch (Exception e) {
            System.out.println("Critical Error, panicked! " + e.getMessage());
        }
    }

    public void render(int id) throws EngineException {

        render(id, null);

    }
    
   /**
    * render gibt Text auf der Konsole aus.
    * @param id der Textelemente
    * @param String error ist ein Fehler
    */
    
    public void render(int id, String error) throws EngineException {
        System.out.print("\u000C");
        var f = engine.getFrame(id);
        System.out.println(" == " + f.title + " ==");
        System.out.println("\n" + f.text);

        System.out.println("\nOptionen:");
        for (int i = 0; i < f.options.size(); i++) {
            System.out.println(" " + (i + 1) + ") " + f.options.get(i).body);
        }

        System.out.println(""); 
        if (error != null) {
            System.out.println("> " + error);
        }
        System.out.print("> ");

        //System.out.println(ANSI_GREEN + "> " + ANSI_RESET + " " + ANSI_RED + "Please type an option" + ANSI_RESET);
        Integer enteredId;

        try {

            enteredId = (new Scanner(System.in)).nextInt() - 1;

        } catch (Exception e) {

            enteredId = null;

        }
        if (enteredId < 0 || enteredId >= f.options.size()) {
            render(id, "Ungültige Eingabe.");
        }

        render(f.options.get(enteredId).to, null);
    }

    public static void main(String[] args) {
        new TextRenderer();
    }   
    /**
    public static final String ANSI_RESET = "\u001B[0m";
    //zum reseten der Farbe
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    //weitere Farben möglich
     */
}

