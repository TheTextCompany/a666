package com.freakurl.a666;

import com.freakurl.engine.FreakEngine;
import com.freakurl.engine.EngineException;
import java.io.File;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.Image;

/**
 * GUI-Version des Spiels.
 * 
 * @author Kitan und Julian Hack
 */
public class GuiRenderer extends JFrame {  
    File prevPng;
    
    
    public void checkFile(File currPng) throws EngineException {
        
           if (currPng.exists() == false ) {
               
            if (prevPng.exists() == false) {
                throw new EngineException("No png file loadble");
                
            }

            prevPng = currPng;  
        }

            
        }
        
        
    
    
    
    

 
       
        
    
         public void paintComponent(Graphics graphic) {
            super.paintComponent(graphic);
            ImageIcon imageIcon = new ImageIcon(currPng.getAbsolutePath());
            Image image = imageIcon.getImage();
            if(image == null){
                
                
                
                
            }
            
        }
    
    }




