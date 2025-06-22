package com.freakurl.a666;

import com.freakurl.engine.FreakEngine;
import com.freakurl.engine.EngineException;
import com.freakurl.engine.Frame;
import java.awt.*;
import javax.swing.*;
import java.io.File;
/**
 * Überklasse der einzelnen Renderer
 * 
 * @author Julian Hack, Kitan Egresi
 * 
 */
public class Renderer {
  
    
    public static void renderFrame(Frame currFrame) {
        
        if (currFrame.sound.isPresent() == true) {
            
            SoundRenderer renderer = new SoundRenderer();
            renderer.renderWAV(currFrame.sound.get());
            
            
            
        }
        
        if (currFrame.image.isPresent() == true) {
            
            TextRenderer renderer = new TextRenderer();
            renderer.render(currFrame.id, null);
            
        }
        
        
        
        
        
        
    }
    
  
        
        
        
        
        
        
        
        
        
        
        
        
        
    
 
}
