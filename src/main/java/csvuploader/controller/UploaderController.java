package csvuploader.controller;

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

import csvuploader.util.UploaderUtil;

@RestController
@RequestMapping("/api/csv")
public class UploaderController {

	@PostMapping(path = "/upload")
	public ResponseEntity<String> uploadTheData(@NonNull @RequestParam("file") MultipartFile file) {
		if(UploaderUtil.validateCSVFormat(file)) {
			return ResponseEntity.status(HttpStatus.OK).body("Successfully uploaded");	
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("Invalid uploaded");
		}
	}
	
	@GetMapping(path = "/fetchalldata")
	public ResponseEntity<String> fetchAllData() {
		return ResponseEntity.status(HttpStatus.OK).body("Successful All Data");
	}
	
	@GetMapping(path = "/fetchbycode")
	public ResponseEntity<String> fetchByCode(@RequestParam("code") String code) {
		return ResponseEntity.status(HttpStatus.OK).body("Successful");
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteAllData() {
		return ResponseEntity.status(HttpStatus.OK).body("All data deleted");
	}
}
