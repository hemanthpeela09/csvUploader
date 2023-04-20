package csvuploader.util;

import org.springframework.web.multipart.MultipartFile;

public class UploaderUtil {

	public static String TYPE = "text/csv";

    public static boolean validateCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }
}
