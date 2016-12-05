package com.multimif.controller;


import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import com.multimif.git.GitConstantes;
import java.util.logging.Logger;
import java.util.zip.ZipOutputStream;

/**
 * Created by Alexis on 05/12/2016.
 */
@RestController
@RequestMapping("/zipFiles")
public class ZipFilesController {

    private static final int BUFFER_SIZE = 4096;

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());


    @RequestMapping(value = "/{file}", method = RequestMethod.GET, produces="application/zip")
    public void zipFiles(@PathVariable(value = "file") String file, HttpServletResponse response) throws IOException{

        File zip = new File(GitConstantes.ZIP_DIRECTORY + file + ".zip");
        FileInputStream inputStream = new FileInputStream(zip);
        response.setContentLength((int) zip.length());

        response.addHeader("Content-Disposition", "attachment; filename=\""+file + ".zip\"");

        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();

    }
}
