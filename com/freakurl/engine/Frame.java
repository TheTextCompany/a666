package com.freakurl.engine;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import org.w3c.dom.*;

/**
 * Container-Klasse für eine Textbox.
 * 
 * @author Jakob
 */
public class Frame {
    /**
     * Eine einzigartige ID, welche diesen Frame identifiziert.
     * 
     * <p>
     * Diese kann verwendet werden, um diesen Frame zu laden in {@link FreakEngine#getFrame(id)}.
     * </p>
     */
    public final int id;
    
    /**
     * Der Autor dieses Frames.
     * 
     * <p>
     * Hier wird eigentlich nur ein Feld unterstützt, jedoch können mehrere Autoren durch ein Komma getrennt angegeben werden.
     * </p>
     */
    public final Optional<String> author;
    
    /**
     * Eine Flag, welche nach dem Anzeigen dieses Frames in {@link FreakEngine} angestellt wird.
     * 
     * <p>
     * Kann in {@link FrameOption}s {@code ifFlag} und {@code unlessFlag} verwendet werden.
     * Alternativ im {@code if}-Tag, welcher in {@code title}- und {@code text}-Body verwendet werden kann.
     * </p>
     */
    public final Optional<String> flag;
    
    /**
     * Als Zusammenfassung dienender Titel.
     * 
     * <p>
     * Dieser wird vielleicht, vielleicht aber auch nicht verwendet.
     * <br>Er sollte nicht zu lang sein.
     * </p>
     */
    public final String title;
    private NodeList titleNodes;
    
    /**
     * Der Hauptinhalt.
     * 
     * <p>
     * Der Text dieses Frames, welcher dem Nutzer präsentiert werden kann.
     * <br>Dieser hat eine unbestimmte Länge.
     * </p>
     */
    public final String text;
    private NodeList textNodes;
    
    /**
     * Optional angezeigtes Bild.
     * 
     * <p>
     * Das Bild, welches angezeigt werden soll.
     * <br>Sollte dieses nicht vorhanden sein, wird das vorherige Bild beibehalten.
     * <br>Die Datei hat einen absoluten Dateipfad. Dieser kann mit beispielsweise {@link File#getPath()} ausgelesen werden.
     * </p>
     */
    public final Optional<File> image;
    
    /**
     * Optional abzuspielender Soundeffekt.
     * 
     * <p>
     * Dieser wird einmalig beim Anzeigen des Frames ausgegeben.
     * <br>Die Datei hat einen absoluten Dateipfad. Dieser kann mit beispielsweise {@link File#getPath()} ausgelesen werden.
     * </p>
     */
    public final Optional<File> sound;
    
    /**
     * Verfügbare Optionen.
     * 
     * <p>
     * Der Nutzer bekommt eine Auswahl zwischen diesen präsentiert.
     * <br>Es kann daraufhin entscheiden, mit welcher Option er weitergehen möchte. Siehe {@link FrameOption#to}.
     * </p>
     */
    public final List<FrameOption> options;
    
    Frame(
        int id,
        String author,
        ArrayList<String> flags,
        String flag,
        NodeList title,
        NodeList text,
        String parent,
        String image,
        String sound,
        FrameOption[] options
    ) {
        this.id = id;
        this.author = Optional.ofNullable(author);
        this.flag = Optional.ofNullable(flag);
        
        this.titleNodes = (NodeList) title;
        this.title = composeText(titleNodes, flags);
        this.textNodes = (NodeList) text;
        this.text = composeText(textNodes, flags);
        
        if (image != null) {
            File imageFile = new File(parent, image);
            this.image = Optional.ofNullable(imageFile.exists() ? imageFile : null);
        } else {
            this.image = Optional.empty();
        }
        
        if (sound != null) {
            File soundFile = new File(parent, sound);
            this.sound = Optional.ofNullable(soundFile.exists() ? soundFile : null);
        } else {
            this.sound = Optional.empty();
        }
        
        this.options = List.of(options);
    }
    
    String composeText(NodeList nodes, ArrayList<String> flags) {
        var tmp = new StringBuilder("");
            
        for (var i = 0; i < nodes.getLength(); i++) {
            var node = nodes.item(i);
            node.normalize();
            if (node.getNodeName().equals("#text")) {
                tmp.append(node.getTextContent());
            } else if (node.getNodeName() == "if") {
                var ifNodes = node.getChildNodes();
                
                var flagAttribute = node.getAttributes().getNamedItem("flag");
                String flag = (flagAttribute != null) ? flagAttribute.getTextContent() : null;
                
                boolean isTmpIf = true;
                var tmpIf = new StringBuilder("");
                var tmpElse = new StringBuilder("");
                
                for (var j = 0; j < ifNodes.getLength(); j++) {
                    var node2 = ifNodes.item(j);
                    node2.normalize();
                    if (node2.getNodeName() == "#text") {
                        (isTmpIf ? tmpIf : tmpElse).append(node2.getTextContent());
                    } else if (node2.getNodeName() == "else") {
                        isTmpIf = false;
                    }
                }
                
                tmp.append(((flag == null || flags.contains(flag)) ? tmpIf : tmpElse).toString());
            }
        }
        
        return tmp.toString().trim();
    }
    
    Frame copyWithFlags(ArrayList<String> flags) {
        var tmp = new ArrayList<FrameOption>();
        
        for (var i : options) {
            if ((i.ifFlag == null && i.unlessFlag == null)
                || (i.ifFlag != null && flags.contains(i.ifFlag))
                || (i.unlessFlag != null && !flags.contains(i.unlessFlag))) {
                tmp.add(i);
            }
        }
        
        return new Frame(
            id,
            author.isPresent() ? author.get() : null,
            flags,
            flag.isPresent() ? flag.get() : null,
            titleNodes,
            textNodes,
            image.isPresent() ? image.get().getParent() : (sound.isPresent() ? sound.get().getParent() : null),
            image.isPresent() ? image.get().getName() : null,
            sound.isPresent() ? sound.get().getName() : null,
            tmp.toArray(new FrameOption[tmp.size()])
        );
    }
}
