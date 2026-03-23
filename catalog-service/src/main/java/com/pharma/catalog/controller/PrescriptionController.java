package com.pharma.catalog.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pharma.catalog.dto.PrescriptionDTO;
import com.pharma.catalog.entity.Prescription.PrescriptionStatus;
import com.pharma.catalog.service.PrescriptionService;

@RestController
@RequestMapping("/api/catalog/prescriptions")
public class PrescriptionController {
	@Autowired
	PrescriptionService ps;
	
	// Directory where uploaded prescription files are stored
    // In production this would be an S3 bucket path or configurable property
    private static final String UPLOAD_DIR = "uploads/prescriptions/";
    
    @PostMapping("/upload")
    public ResponseEntity<PrescriptionDTO> uploadPrescription(
            @RequestParam("file") MultipartFile file,
            @RequestParam("customerId") Long customerId,
            @RequestParam("medicineIds") List<Long> medicineIds) throws IOException {

        // Validate file type
        String originalFilename = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(originalFilename);
        if (extension == null || !List.of("pdf", "jpg", "jpeg", "png").contains(extension.toLowerCase())) {
            return ResponseEntity.badRequest().build();
        }

        // Save file with a unique name to avoid collisions
        String savedFilename = UUID.randomUUID() + "." + extension;
        Path uploadPath = Paths.get(UPLOAD_DIR);
        Files.createDirectories(uploadPath);
        Files.copy(file.getInputStream(), uploadPath.resolve(savedFilename), StandardCopyOption.REPLACE_EXISTING);

//        String fileUrl = UPLOAD_DIR + savedFilename;

        PrescriptionDTO dto = ps.createPrescription(customerId, medicineIds);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionDTO>getPrescriptionById(@PathVariable Long id){
    	return ResponseEntity.ok(ps.getPrescriptionById(id));
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PrescriptionDTO>> getByCustomer(@PathVariable Long customerId){
    	return ResponseEntity.ok(ps.getPrescriptionsByCustomer(customerId));
    }
    
    @GetMapping("/pending")
    public ResponseEntity<List<PrescriptionDTO>>getPending(){
    	return ResponseEntity.ok(ps.getPrescriptionsByStatus(PrescriptionStatus.PENDING));
    }
    
   
    
}
