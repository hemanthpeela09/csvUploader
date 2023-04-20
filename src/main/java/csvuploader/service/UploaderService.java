package csvuploader.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import csvuploader.exception.UploaderException;
import csvuploader.model.DataModel;

public interface UploaderService {
	void uploadData(MultipartFile file) throws UploaderException;

    List<DataModel> getAllData();

    Optional<DataModel> fetchByCode(String code);

    void deleteAllData();
}
