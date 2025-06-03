package com.freakurl.engine;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Klasse zum Erstellen und Abrufen von Character-Objekten.
 * 
 * @author Julian Hack
 */
public class CharacterManager {
    private static ArrayList<Character> characters = new ArrayList<>();

    public CharacterManager(){}

    /**
     * Erstellt ein Character Objekt mit den übergebenen Parametern.
     *
     * @param id Text-ID eines Characters.
     * @param name Der Name des Characters.
     * @param presentedIn Die Frame-ID in welcher der Character vorkommt.
     * @param summary Zusatzinformationen zu dem Character.
     * @throws EngineException Wird geschmissen, wenn versucht wird eine ID mehrmals zu vergeben.
     */
    public static void createCharacter(String id, String name, Optional<Integer> presentedIn, Optional<String> summary) throws EngineException {
        for(int i = 0; i < characters.size(); i++) {
            if(characters.get(i).id.equals(id)) {
                throw new EngineException("Failed to create Character with ID: " + id + "; already exists");
            }
        }

        characters.add(new Character(id, name, presentedIn, summary));
    }

    /**
     * Gibt den Character mit der jeweiligen ID zurück.
     * 
     * @param id ID des jeweiligen Character Objektes, das zurückgegeben werden soll.
     * @return Character Der angeforderte Character.
     * @throws EngineException Wird geschmissen, wenn kein Character mit der jeweiligen ID im Speicher existiert.
     */
    public static Character getCharacter(String id) throws EngineException {
        for(int i = 0; i < characters.size(); i++) {
            Character currentCharacter = characters.get(i);
            if(id.equals(currentCharacter.id)) {
                return currentCharacter;
            }
        }

        throw new EngineException("No Character registered of ID: " + id);
    }
}