package com.freakurl.engine;
import java.util.ArrayList;

/**
 * Klasse zum Erstellen und Abrufen von Charakter Objekten.
 * 
 * @author Julian Hack
 */
public class CharakterManager {
   private static ArrayList<Charakter> charakterArray = new ArrayList<>();

    /**
     * Erstellt ein Charakter Objekt mit den übergebenen Parametern.
     *
     * @param id Text-ID eines Charakters.
     * @param name Der Name des Charakters.
     * @param imagePath Der Dateipfad zu dem Bild/Grafik des Charakters.
     * @throws EngineException Wird geschmissen, wenn versucht wird eine ID mehrmals zu vergeben.
     */
    static void createCharakter(String id, String name, String imagePath) throws EngineException {
        for(int i = 0; i < charakterArray.size(); i++) {
            if(charakterArray.get(i).id.equals(id)) {
                throw new EngineException("Failed to create Charakter with ID: " + id + "; already exists");
            }
        }
        Charakter newCharakter = new Charakter(id, name, imagePath);
        charakterArray.add(newCharakter);
    }

    /**
     * Gibt den Charakter mit der jeweiligen ID zurück.
     * 
     * @param id ID des jeweiligen Charakter Objektes, das zurückgegeben werden soll.
     * @return Charakter Der angeforderte Charakter.
     * @throws EngineException Wird geschmissen, wenn kein Charakter mit der jeweiligen ID im Speicher existiert.
     */
    static Charakter getCharakter(String id) throws EngineException {
        for(int i = 0; i < charakterArray.size(); i++) {
            Charakter currentCharakter = charakterArray.get(i);
            if(id.equals(currentCharakter.id)) {
                return currentCharakter;
            }
        }
        
        throw new EngineException("No Charakter registered of ID: " + id);
    }
}