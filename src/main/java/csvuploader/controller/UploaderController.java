package csvuploader.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import csvuploader.exception.UploaderException;
import csvuploader.model.DataModel;
import csvuploader.service.UploaderService;
import csvuploader.util.UploaderUtil;

@RestController
@RequestMapping("/api/csv")
public class UploaderController {
	
	@Autowired
	UploaderService service;

	@PostMapping(path = "/upload")
	public ResponseEntity<String> uploadTheData(@NonNull @RequestParam("file") MultipartFile file) {
		if(UploaderUtil.validateCSVFile(file)) {
			try {
				service.uploadData(file);
				return ResponseEntity.status(HttpStatus.OK).body("Successfully uploaded");	
			} catch (UploaderException ex) {
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ex.getMessage());
			}
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("Invalid File uploaded");
		}
	}
	
	@GetMapping(path = "/fetchalldata")
	public ResponseEntity<String> fetchAllData() {
		try {

			List<DataModel> list = service.getAllData();
			if (list.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body("Empty Data in Database");
			}
			return ResponseEntity.status(HttpStatus.OK).body(list.toString());
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/fetchbycode")
	public ResponseEntity<String> fetchByCode(@RequestParam("code") String code) {
		Optional<DataModel> data = service.fetchByCode(code);
        if(data.isEmpty())
        {
        	return ResponseEntity.status(HttpStatus.OK).body("Empty Data in Database");
        }
        return ResponseEntity.status(HttpStatus.OK).body(data.toString());
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteAllData() {
		service.deleteAllData();
		return ResponseEntity.status(HttpStatus.OK).body("All data deleted");
	}
}
