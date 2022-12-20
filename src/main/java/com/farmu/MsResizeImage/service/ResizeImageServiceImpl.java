/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.farmu.MsResizeImage.service;

import com.farmu.MsResizeImage.dao.ResizeImageDao;
import com.farmu.MsResizeImage.dto.RequestResizeImage;
import com.farmu.MsResizeImage.dto.ResponseStateProcess;
import com.farmu.MsResizeImage.threads.ResizeImageThreadLogic;
import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Cristian Camilo Beltrán
 * @since  19/12/2022
 */
@Service
public class ResizeImageServiceImpl implements ResizeImageService{

    @Autowired
    private ResizeImageDao resizeImageDao;
    
    @Autowired
    private ResizeImageThreadLogic resizeImageThreadLogic;
    
    @Override
    @Transactional
    public long processImage(RequestResizeImage requestResizeImage) {
        long id = resizeImageDao.registryProcess(requestResizeImage);
        
        Executor executor = Executors.newSingleThreadExecutor();
        
        resizeImageThreadLogic.setName("resizeImageThreadLogic");
        resizeImageThreadLogic.setIdProcess(id);
        resizeImageThreadLogic.setRequestResizeImage(requestResizeImage);
        executor.execute(resizeImageThreadLogic);
        
        return id;
    }

    @Override
    public ResponseStateProcess queryState(long idProcess) {
        ResponseStateProcess responseStateProcess = new ResponseStateProcess();
        boolean res = resizeImageDao.queryProcess(idProcess);
        responseStateProcess.setIdProcess(idProcess);
        responseStateProcess.setFinished(res);
        return responseStateProcess;
    }

    @Override
    public File downloadFile(long idProcess) {
        String path = resizeImageDao.queryPathDownload(idProcess);
        return Paths.get(path).toFile();
    }
    
}
