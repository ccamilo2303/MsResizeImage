package com.farmu.MsResizeImage.dto;

import lombok.Data;

/**
 * @author Cristian Camilo Beltrán
 * @since  19/12/2022
 */
@Data
public class RequestResizeImage {
    private String fileName;
    private String data;
    private int width;
    private int height;
}
