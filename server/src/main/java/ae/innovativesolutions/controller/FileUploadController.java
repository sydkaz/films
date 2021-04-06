package ae.innovativesolutions.controller;


import ae.innovativesolutions.payload.UploadedFilePayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/public/")
public class FileUploadController implements  ServletContextAware {


    @Value("${file.uploadLocation}")
    String fileUploadLocation;

    public void setServletContext(ServletContext servletContext) {
        String rootPath = System.getProperty("user.dir");
        fileUploadLocation = rootPath+fileUploadLocation;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    List<UploadedFilePayload> upload(MultipartHttpServletRequest request ) throws IOException {

        //the name should be passd as name
        System.out.println(request.getParameter("name"));

        // Getting uploaded files from the request object
        Map<String, MultipartFile> fileMap = request.getFileMap();

        // Maintain a list to send back the files info. to the client side
        List<UploadedFilePayload> uploadedFiles = new ArrayList<>();

        // Iterate through the map
        for (MultipartFile multipartFile : fileMap.values()) {

            // Save the file to local disk
            saveFileToLocalDisk(multipartFile);

            UploadedFilePayload fileInfo = getUploadedFileInfo(multipartFile,request.getHeader("host"));

            // Save the file info to database
            //fileInfo = saveFileToDatabase(fileInfo);

            // adding the file info to the list
            uploadedFiles.add(fileInfo);
        }

        return uploadedFiles;
    }




  /*@RequestMapping(value = "/get/{fileId}", method = RequestMethod.GET)
  public void getFile(HttpServletResponse response, @PathVariable Long fileId) {

    UploadedFile dataFile = uploadService.getFile(fileId);

    File file = new File(dataFile.getLocation(), dataFile.getName());

    try {
      response.setContentType(dataFile.getType());
      response.setHeader("Content-disposition", "attachment; filename=\"" + dataFile.getName()
          + "\"");

      FileCopyUtils.copy(FileUtils.readFileToByteArray(file), response.getOutputStream());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }*/


    private void saveFileToLocalDisk(MultipartFile multipartFile) throws IOException,
            FileNotFoundException {

        String outputFileName = getOutputFilename(multipartFile);

        FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(outputFileName));
    }

    private UploadedFilePayload saveFileToDatabase(UploadedFilePayload uploadedFile) {
        return uploadedFile;
    }

    private String getOutputFilename(MultipartFile multipartFile) {

        return fileUploadLocation + multipartFile.getOriginalFilename();
    }

    private UploadedFilePayload getUploadedFileInfo(MultipartFile multipartFile, String host) throws IOException {
        UploadedFilePayload fileInfo = new UploadedFilePayload();
        fileInfo.setName(multipartFile.getOriginalFilename());
        fileInfo.setSize(multipartFile.getSize());
        fileInfo.setType(multipartFile.getContentType());
        fileInfo.setLocation("http://"+host+"/upload/static/images/"+multipartFile.getOriginalFilename());

        return fileInfo;
    }

}
