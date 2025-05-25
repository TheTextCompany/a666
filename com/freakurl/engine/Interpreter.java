package com.freakurl.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * Schnittstelle zum Interpretieren von XML Spieldateien.
 * 
 * @author Jakob
 */
public class Interpreter {
    private static ArrayList<String> globalIds = new ArrayList<String>();
    
    /**
     * Lade eine Routine mit dem Standarddateinamen.
     * 
     * @see Interpreter#loadRoutine(parent, name)
     * 
     * @param dir Ein absoluter Pfad zum übergeordneten Verzeichnis.
     * @param name Dateiname mit Dateiendung im Verzeichnis {@code parent}.
     * @throws EngineException Wird ausgelöst, sollte der Ordner oder die Datei nicht existieren, die Datei nicht richtig formatiert sein oder diese einen nicht-evaluierbaren Wert enthalten. Siehe {@link EngineException#getMessage()} für mehr Details.
     * @return Die geparste {@link Routine} mit allen {@link Frame}s.
     */
    static Routine loadRoutine() throws EngineException {
        return loadRoutine("assets", "main.xml");
    };
    
    /**
     * Lade eine Routine mit dem übergebenen Dateinamen.
     * 
     * <p>
     * Liest die Datei aus und lädt sie. Daraufhin werden {@link Routine#summary} und {@link Routine#sound} ausgelesen.
     * <br>Im Anschluss werden {@link Routine#frames} geparst.
     * <br>Sollte ein Feld {@code imports} mit Inhalt existieren, wird dieses evaluiert.
     * </p>
     * 
     * @param parent Ein absoluter Pfad zum übergeordneten Verzeichnis.
     * @param name Dateiname mit Dateiendung im Verzeichnis {@code dir}.
     * @throws EngineException Wird ausgelöst, sollte der Ordner oder die Datei nicht existieren, die Datei nicht richtig formatiert sein oder diese einen nicht-evaluierbaren Wert enthalten. Siehe {@link EngineException#getMessage()} für mehr Details.
     * @return Die geparste {@link Routine} mit allen {@link Frame}s.
     */
    static Routine loadRoutine(String parent, String name) throws EngineException {
        File f = (new File(parent, name)).getAbsoluteFile();
        
        Document doc;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            
            doc = factory.newDocumentBuilder().parse(f);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            throw new EngineException(e.getMessage());
        }
        
        NodeList routineList = doc.getElementsByTagName("routine");
        if (routineList.getLength() != 1) {
            throw new EngineException("Document didn't include ROUTINE element");
        }
        Node routine = routineList.item(0);
        
        Node summaryNode = getChildElementByTagName(routine, "summary");
        if (summaryNode == null) {
            throw new EngineException("Document didn't include ROUTINE.SUMMARY element");
        }
        final String summary = summaryNode.getTextContent().trim();
        
        Node soundNodeGlobal = getChildElementByTagName(routine, "sound");
        final String soundGlobal = (soundNodeGlobal != null) ? soundNodeGlobal.getAttributes().getNamedItem("src").getTextContent() : null;
        
        Node framesNode = getChildElementByTagName(routine, "frames");
        framesNode.normalize();
        if (framesNode == null) {
            throw new EngineException("Document didn't include ROUTINE.FRAMES element");
        }
        
        ArrayList<Integer> usedIds = new ArrayList<Integer>();
        ArrayList<Frame> frames = new ArrayList<Frame>();
        NodeList framesChildren = framesNode.getChildNodes();

        for (var i = 0; i < framesChildren.getLength(); i++) {
            final var frame = framesChildren.item(i);
            if (frame.getNodeType() == Node.ELEMENT_NODE
             && ((Element) frame).getTagName().equals("frame")) {
                int id;
                var idAttribute = frame.getAttributes().getNamedItem("id");
                if (idAttribute == null) {
                    throw new EngineException("Document didn't include ROUTINE.FRAMES[" + i + "].ID element");
                }
                try {
                    id = Integer.parseInt(idAttribute.getTextContent());
                    usedIds.add(id);
                } catch (Exception e) {
                    throw new EngineException("Invalid value at ROUTINE.FRAMES[" + i + "].ID: " + e.getMessage());
                }
                
                var authorAttribute = frame.getAttributes().getNamedItem("author");
                final String author = (authorAttribute != null) ? authorAttribute.getTextContent() : null;
                
                Node titleNode = getChildElementByTagName(frame, "title");
                if (titleNode == null) {
                    throw new EngineException("Document didn't include ROUTINE.FRAMES[" + i + "].TITLE element");
                }
                final String title = titleNode.getTextContent().trim();
                
                Node textNode = getChildElementByTagName(frame, "text");
                if (textNode == null) {
                    throw new EngineException("Document didn't include ROUTINE.FRAMES[" + i + "].TEXT element");
                }
                final String text = textNode.getTextContent().trim();
                
                Node soundNode = getChildElementByTagName(frame, "sound");
                final String sound = (soundNode != null) ? soundNode.getAttributes().getNamedItem("src").getTextContent() : null;
        
                Node imageNode = getChildElementByTagName(frame, "image");
                final String image = (imageNode != null) ? imageNode.getAttributes().getNamedItem("src").getTextContent() : null;
                
                Node optionsNode = getChildElementByTagName(frame, "options");
                if (framesNode == null) {
                    throw new EngineException("Document didn't include ROUTINE.FRAMES[" + i + "].OPTIONS element");
                }
                
                ArrayList<FrameOption> options = new ArrayList<FrameOption>();
                NodeList optionsChildren = optionsNode.getChildNodes();
                for (var j = 0; j < optionsChildren.getLength(); j++) {
                    final var option = optionsChildren.item(j);
                    if (option.getNodeType() == Node.ELEMENT_NODE
                     && ((Element) option).getTagName().equals("option")) {
                        int to;
                        var toAttribute = option.getAttributes().getNamedItem("to");
                        if (toAttribute == null) {
                            throw new EngineException("Document didn't include ROUTINE.FRAMES[" + i + "].OPTIONS[" + j + "].TO element");
                        }
                        try {
                            to = Integer.parseInt(toAttribute.getTextContent());
                        } catch (Exception e) {
                            throw new EngineException("Invalid value at ROUTINE.FRAMES[" + i + "].OPTIONS[" + j + "].TO: must me Integer");
                        }
                        
                        final boolean isDefault = option.getAttributes().getNamedItem("default") != null;
                        
                        var styleAttribute = option.getAttributes().getNamedItem("style");
                        final String[] style = ((styleAttribute != null) ? styleAttribute.getTextContent() : "").trim().split(" ");
                        
                        final String body = option.getTextContent().trim();
                        if (body.isEmpty()) {
                            throw new EngineException("Invalid value at ROUTINE.FRAMES[" + i + "].OPTIONS[" + j + "].BODY: body must not be empty");
                        }
                        
                        options.add(new FrameOption(to, isDefault, style, body));
                    }
                }
                
                frames.add(new Frame(id, author, title, text, f.getParent(), image, sound, options.toArray(new FrameOption[options.size()])));
            }
        }
        
        if (frames.isEmpty()) {
            throw new EngineException("Invalid value at ROUTINE.FRAMES: frames must not be empty");
        }
        
        Node importsNode = getChildElementByTagName(routine, "imports");
        if (importsNode != null) {
            NodeList importsChildren = importsNode.getChildNodes();
            for (var i = 0; i < importsChildren.getLength(); i++) {
                var node = importsChildren.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE
                 && ((Element) node).getTagName().equals("import")) {
                    for (var frame : frames) {
                        globalIds.add("" + frame.id);
                    }
                    final var importing = loadRoutine(f.getParent(), node.getAttributes().getNamedItem("src").getTextContent()).frames;
                    
                    check:
                    for (var frame : importing) {
                        for (var e : frames) {
                            if (e.id == frame.id) {
                                continue check;
                            }
                        }
                        frames.add(frame);
                    }
                    
                    globalIds.clear();
                }
            }
        }
        
        ArrayList<String> availableIds = new ArrayList<String>();
        availableIds.addAll(globalIds);
        ArrayList<String> duplicateIds = new ArrayList<String>();
        ArrayList<String> overflowIds = new ArrayList<String>();
        for (var frame : frames) {
            if (!availableIds.contains("" + frame.id)) {
                availableIds.add("" + frame.id);
            } else if (!duplicateIds.contains(frame.id)) {
                duplicateIds.add("" + frame.id);
            }
        }
        for (var frame : frames) {
            for (var option : frame.options) {
                if (!availableIds.contains("" + option.to) && !overflowIds.contains("" + option.to)) {
                    overflowIds.add("" + option.to);
                }
            }
        }
        
        if (!duplicateIds.isEmpty()) {
            throw new EngineException("Duplicate ID was registered: " + String.join(", ", duplicateIds.toArray(new String[duplicateIds.size()])));
        } else if (!overflowIds.isEmpty()) {
            throw new EngineException("Unregistered ID was referenced: " + String.join(", ", overflowIds.toArray(new String[overflowIds.size()])));
        }
        
        return new Routine(summary, f.getParent(), soundGlobal, frames.toArray(new Frame[frames.size()]));
    }
    
    static private Node getChildElementByTagName(Node parent, String tagName) {
        NodeList kids = parent.getChildNodes();
        for (var i = 0; i < kids.getLength(); i++) {
            var node = kids.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE
             && ((Element) node).getTagName().equals(tagName)) {
                return (Node) node;
            }
        }
        return null;
    }
}
