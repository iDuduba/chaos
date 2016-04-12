package wang.laic.chaos.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

//@RestController
@Controller
@RequestMapping("/images")
public class ImageController {
	private static final Logger logger = LoggerFactory.getLogger(ImageController.class);
	
	  // The Environment object will be used to read parameters from the 
	  // application.yml configuration file
	  @Autowired
	  private Environment env;
	  
	  /**
	   * Show the index page containing the form for uploading a file.
	   */
	  @RequestMapping("/upload")
	  public String index() {
	    return "upload";
	  }
	  
	  
	  @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	  @ResponseBody
	  public ResponseEntity<?> uploadFile(@RequestParam("uploadfile") MultipartFile uploadfile) {
	    
	    try {
	      // Get the filename and build the local file path
	      String filename = uploadfile.getOriginalFilename();
	      String directory = env.getProperty("chaos.paths.uploadedFiles");
	      String filepath = Paths.get(directory, filename).toString();
	      
	      // Save the file locally
	      BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
	      stream.write(uploadfile.getBytes());
	      stream.close();
	    } catch (Exception e) {
	      logger.error(e.getMessage());
	      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	    
	    return new ResponseEntity<>(HttpStatus.OK);
	  } // method uploadFile
	  
}
