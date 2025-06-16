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
            System.out.println("Critical error, panicked: " + e.getMessage());
        }
    }

    /**
     * Gibt einen Frame mit der gegebenen ID aus.
     * 
     * @param id Die angeforderte ID.
     * @param error Wenn nicht {@code null}, ein Fehler, welcher von dem Eingabefeld angezeigt wird.
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

        Integer enteredId;
        try {
            enteredId = (new Scanner(System.in)).nextInt() - 1;
        } catch (Exception e) {
            enteredId = null;
        }
        
        if (enteredId == null || enteredId < 0 || enteredId >= f.options.size()) {
            render(id, "Ungültige Eingabe.");
        }

        render(f.options.get(enteredId).to, null);
    }

    public static void main(String[] args) {
        new TextRenderer();
    }   
}

