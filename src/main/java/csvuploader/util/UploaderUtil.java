package csvuploader.util;

import org.springframework.web.multipart.MultipartFile;

public class UploaderUtil {

	public static String TYPE = "text/csv";

    public static boolean validateCSVFile(MultipartFile file){
    	if(!file.isEmpty()) {
    		return TYPE.equals(file.getContentType());	
    	}
    	return false;
    }
}
