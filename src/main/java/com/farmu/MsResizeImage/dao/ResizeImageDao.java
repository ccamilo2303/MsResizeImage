package com.farmu.MsResizeImage.dao;

import com.farmu.MsResizeImage.dto.RequestResizeImage;

/**
 * @author Cristian Camilo Beltrán
 * @since  19/12/2022
 */
public interface ResizeImageDao {
    
    /**
     * @param requestResizeImage
     * @return 
     */
    public long registryProcess(RequestResizeImage requestResizeImage);
    
    /**
     * @param idProcess
     * @param outputPath 
     */
    public void finishProcess(long idProcess, String outputPath);
    
    /**
     * @param idProcess
     * @return 
     */
    public boolean queryProcess(long idProcess);
    
    /**
     * @param idProcess
     * @return 
     */
    public String queryPathDownload(long idProcess);
    
}
