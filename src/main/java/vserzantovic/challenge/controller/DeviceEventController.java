package vserzantovic.challenge.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vserzantovic.challenge.service.DeviceEventService;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class DeviceEventController {

    @Autowired
    private DeviceEventService deviceEventService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/file-upload")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file) {
        String json = deviceEventService.uploadFile(file);
        return json;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/download", produces = MediaType.ALL_VALUE)
    public ResponseEntity<Object> getImageWithMediaType() {

        String filePath = System.getProperty("user.dir") + "\\output.json";
        Path path = Paths.get(filePath);
        Resource resource = null;

        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (resource.exists()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return new ResponseEntity<Object>("File Not Found ", HttpStatus.OK);
        }

    }

}
