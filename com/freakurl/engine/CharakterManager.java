package com.freakurl.engine;
import java.util.ArrayList;

/**
 * Klasse zum erstellen / abrufen von Charakter Objekten.
 * 
 * @author Julian Hack
 * 
 */
public class CharakterManager
{
    static ArrayList<Charakter> charakterArray = new ArrayList<>();

    /**
     * Erstellt ein Charakter Objekt mit den übergebenen Parametern
     *
     * @param id Text-Id eines Charakters name Der Name des Charakters imagePath Der Dateipfad zu dem Bild/Grafik des Charakters.
     * @throw EngineExeption wenn versucht wird eine ID mehrmals zu vergeben.
     */
    static void createCharakter(String id, String name, String imagePath) throws EngineException {
        for(int i = 0; i < charakterArray.size(); i++) {
            if(charakterArray.get(i).id.compareTo(id) == 0) {

                throw new EngineException("Failed to create Charakter with ID: " + id + "\n ID already exists");
            }
        }
        Charakter newCharakter = new Charakter(id, name, imagePath);
        charakterArray.add(newCharakter);
    }

    /**
     * Gibt den Charakter mit der jeweiligen Id zurück.
     * 
     * @param id ID des jeweiligen Charakter Objektes, das zurückgegeben werden soll.
     * @return charakterArray.get(i) Gibt das Charakter Objekt mit der jeweiligen Id zurück.
     * @throw EngineExeption wenn kein Charakter mit der jeweiligen ID im Array existiert.
     */
    static Charakter getCharakter(String id) throws EngineException {

        for(int i = 0; i < charakterArray.size(); i++) {

            if(id.compareTo(charakterArray.get(i).id) == 0) {

                return charakterArray.get(i);
            }

        }
        throw new EngineException("No Charakter registered of ID: " + id);
    }
}