package com.freakurl.engine;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Klasse zum Erstellen und Abrufen von {@link Character}-Objekten.
 * 
 * @author Julian Hack
 */
public class CharacterManager {
    private static ArrayList<Character> characters = new ArrayList<>();

    /**
     * Erstellt ein {@link Character}-Objekt mit den übergebenen Parametern und fügt diesen der ArrayList hinzu
     *
     * @param id Text-ID des {@link Character}s.
     * @param name Name des {@link Character}s.
     * @param summary Kurze Zusammenfassung der Rolle des {@link Character} in der Geschichte.
     * @param presentedIn Die Frame-ID, in welcher dieser {@link Character} vorgestellt wird.
     * @throws EngineException Wird geschmissen, wenn versucht wird eine ID mehrmals zu vergeben.
     */
    public static void createCharacter(String id, String name, Optional<String> summary, Optional<Integer> presentedIn) throws EngineException {
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).id.equals(id)) {
                throw new EngineException("Failed to create Character with ID: " + id + "; already exists");
            }
        }

        characters.add(new Character(id, name, summary, presentedIn));
    }

    /**
     * Überprüft ob ein {@link Character}-Objekt mit der übergebenen ID in der ArrayList existiert.
     * 
     * @param id Text-ID des {@link Character}s.
     * @return true Wenn ein {@link Character}-Objekt mit der übergebenen ID existiert.
     * @return false Wenn kein {@link Character}-Objekt mit der übergebenen ID existiert.
     */
    public static boolean checkForCharacter(String id) {
        for (int i = 0; i < characters.size(); i++) {
            if (characters.get(i).id.equals(id)) {        
                return true;
            }
        }    
        
        return false;
    }

    /**
     * Gibt den Character mit der jeweiligen ID zurück.
     * 
     * @param id ID des jeweiligen {@link Character} Objektes, das zurückgegeben werden soll.
     * @return Character Der angeforderte {@link Character}.
     * @throws EngineException Wird geschmissen, wenn kein {@link Character} mit der jeweiligen ID im Speicher existiert.
     */
    public static Character getCharacter(String id) throws EngineException {
        for (int i = 0; i < characters.size(); i++) {
            Character currentCharacter = characters.get(i);
            if (id.equals(currentCharacter.id)) {
                return currentCharacter;
            }
        }

        throw new EngineException("No Character registered of ID: " + id);
    }
}