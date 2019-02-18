package com.git.hui.boot.web.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by @author yihui in 22:02 19/2/12.
 */
@RestController
@RequestMapping(path = "/file")
public class FileUploadRest {

    /**
     * 保存上传的文件
     *
     * @param file
     * @return
     */
    private String saveFileToLocal(MultipartFile file) {
        try {
            String name = "/tmp/out_" + System.currentTimeMillis() + file.getName();
            FileOutputStream writer = new FileOutputStream(new File(name));
            writer.write(file.getBytes());
            writer.flush();
            writer.close();
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @PostMapping(path = "upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        String ans = saveFileToLocal(file);
        return ans;
    }

    @GetMapping(path = "get")
    public String get() {
        return "hello";
    }
}
