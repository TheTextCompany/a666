package com.freakurl.engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Container-Klasse für eine Frame-Option.
 * 
 * @author Jakob
 */
public class FrameOption {
    /**
     * Die ID, zu welcher diese Option führt.
     * 
     * <p>
     * Es handelt sich dabei um eine ID, die zu einer {@link Frame#id} gehört.
     * <br>Dieser Wert ist validiert und muss somit zu einer ID gehören.
     * </p>
     */
    public final int to;
    
    /**
     * Ob diese Option als Standard angezeigt wird.
     * 
     * <p>
     * Das heißt: Wenn der Nutzer keine Auswahl trifft, wird diese ausgewählt.
     * <br>Sollte keine Option in {@link Frame#options} als {@code isDefault} definiert sein, wird die erste Option gewählt.
     * <br>Sollten mehrere mit {@code isDefault} definiert sein, wird die erste, die das Attribut hat, gewählt.
     * </p>
     */
    public final boolean isDefault;
    
    /**
     * Anzuwendende Style-Attribute.
     */
    public final List<FrameOptionStyle> style;
    
    /**
     * Der Text, welcher für den Button angezeigt werden soll.
     * 
     * <p>
     * Dieser kann nicht leer sein, ansonsten wird ein Fehler geschmissen.
     * <br>Dessen Inhalt sollte kurz und direkt sein.
     * </p>
     */
    public final String body;
    
    final String ifFlag;
    final String unlessFlag;
    
    FrameOption(
        int to,
        boolean isDefault,
        String[] style,
        String ifFlag,
        String unlessFlag,
        String body
    ) {
        this.to = to;
        this.isDefault = isDefault;
        this.body = body;
        
        this.ifFlag = ifFlag;
        this.unlessFlag = unlessFlag;
        
        ArrayList<FrameOptionStyle> styleParsed = new ArrayList<FrameOptionStyle>();
        for (var i = 0; i < style.length; i++) {
            if (!style[i].trim().isEmpty()) {
                try {
                    styleParsed.add(FrameOptionStyle.valueOf(style[i]));
                } catch (Exception e) {}
            }
        }
        this.style = List.of(styleParsed.toArray(new FrameOptionStyle[styleParsed.size()]));
    }
}
