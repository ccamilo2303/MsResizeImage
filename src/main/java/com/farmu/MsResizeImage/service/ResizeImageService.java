package com.farmu.MsResizeImage.service;

import com.farmu.MsResizeImage.dto.RequestResizeImage;
import com.farmu.MsResizeImage.dto.ResponseStateProcess;
import java.io.File;
import org.springframework.stereotype.Service;

/**
 * @author Cristian Camilo Beltrán
 * @since  19/12/2022
 */
public interface ResizeImageService {
    
    /**
     * @param requestResizeImage
     * @return 
     */
    public long processImage(RequestResizeImage requestResizeImage);
    
    /**
     * @param idProcess
     * @return 
     */
    public ResponseStateProcess queryState(long idProcess);
    
    /**
     * @param idProcess
     * @return 
     */
    public File downloadFile(long idProcess);
    
}
