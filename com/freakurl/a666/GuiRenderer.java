package com.freakurl.a666;

import com.freakurl.engine.FreakEngine;
import com.freakurl.engine.EngineException;
import java.io.File;

/**
 * GUI-Version des Spiels.
 * 
 * @author Kitan und Julian Hack
 */
public class GuiRenderer {
    File PrevPng;

    public GuiRenderer() {}

    /**
     *  @author Julian Hack
     *  Übergibt den absoluten Dateipfad an @link{displayImage()}.
     *  
     *  @param filePng Ein File Objekt einer PNG-Datei.
     *  @throws EngineException Wird geschmissen, wenn die aktuelle und vorherige Bilddatei nicht existiert.
     */

    public int checkImage(File filePng) throws EngineException {
        if (filePng.exists() == false ) {
            if (PrevPng.exists() == false) {
                throw new EngineException("No png file loadble");
                return -1;
            }
            PrevPng = filePng;
             
            return  0;  
        
    }
        
}













public void setJPanelPos()