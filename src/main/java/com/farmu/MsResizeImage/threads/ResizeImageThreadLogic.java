/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.farmu.MsResizeImage.threads;

import com.farmu.MsResizeImage.dao.ResizeImageDao;
import com.farmu.MsResizeImage.dto.RequestResizeImage;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Cristian Camilo Beltrán
 * @since  19/12/2022
 */
@Component
public class ResizeImageThreadLogic implements Runnable {
    
    String name;
    private RequestResizeImage requestResizeImage;
    private File file;
    private File fileOuput;
    private long idProcess;
    
    @Autowired
    private ResizeImageDao resizeImageDao;
    
    @Override
    public void run() {
        try{
            writeTempFile();
            resizeImage();
            System.out.println("com.farmu.MsResizeImage.threads.ResizeImageThreadLogic.run(): "+resizeImageDao+" --- "+this);
            resizeImageDao.finishProcess(idProcess, fileOuput.getPath());
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void writeTempFile() throws IOException{
        byte[] data = DatatypeConverter.parseBase64Binary(requestResizeImage.getData());
        file = Files.createTempFile("file", ".png").toFile();
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
        outputStream.write(data);
    }
    
    private void resizeImage() throws IOException{
        BufferedImage inputImage = ImageIO.read(file);
        
        BufferedImage outputImage = new BufferedImage(requestResizeImage.getWidth(),
                requestResizeImage.getHeight(), 
                inputImage.getType());
        
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, requestResizeImage.getWidth(), requestResizeImage.getHeight(), null);
        g2d.dispose();
        fileOuput = Files.createTempFile("resize", ".png").toFile();
        
        ImageIO.write(outputImage, "png", fileOuput);
    }
    
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RequestResizeImage getRequestResizeImage() {
        return requestResizeImage;
    }

    public void setRequestResizeImage(RequestResizeImage requestResizeImage) {
        this.requestResizeImage = requestResizeImage;
    }

    public long getIdProcess() {
        return idProcess;
    }

    public void setIdProcess(long idProcess) {
        this.idProcess = idProcess;
    }
    
    
}

