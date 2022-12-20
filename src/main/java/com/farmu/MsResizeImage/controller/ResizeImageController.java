
package com.farmu.MsResizeImage.controller;

import com.farmu.MsResizeImage.dto.RequestResizeImage;
import com.farmu.MsResizeImage.service.ResizeImageService;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cristian Camilo Beltrán
 * @since  19/12/2022
 */
@RestController
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
@RequestMapping("/v1")
public class ResizeImageController {
    
    @Autowired
    private ResizeImageService resizeImageService;
    
    @PostMapping("/resize-image")
    public ResponseEntity resizeImage(@RequestBody RequestResizeImage requestResizeImage){
        try{
        long id = resizeImageService.processImage(requestResizeImage);
        return ResponseEntity.ok(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(-1);
    }
    
    @GetMapping("/query-state/{idProcess}")
    public ResponseEntity queryState(@PathVariable("idProcess") long idProcess){
        return ResponseEntity.ok(resizeImageService.queryState(idProcess));
        
    }
    
    @GetMapping("/download/{idProcess}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("idProcess") long idProcess) throws FileNotFoundException{
        
        InputStream in = new FileInputStream(resizeImageService.downloadFile(idProcess));
        
        return ResponseEntity.ok()
                .contentType( MediaType.IMAGE_PNG)
                .body(new InputStreamResource(in));
        
    }
    
    
}
