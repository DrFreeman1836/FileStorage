package main.controller;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import main.dto.RsDto;
import main.model.File;
import main.service.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class Controller {

  private final FileService fileService;

  @PostMapping
  public ResponseEntity<RsDto> saveFile(@RequestParam("file") MultipartFile file) throws Exception {
    fileService.saveFile(file);
    return ResponseEntity.ok(RsDto.builder().result(true).data("Файл сохранен").build());
  }

  @GetMapping
  public ResponseEntity<RsDto> getListFiles() {
    List<String> listFileName = fileService.getListFiles();
    return ResponseEntity.ok(RsDto.builder().result(true).data(listFileName).build());
  }

  @GetMapping("/{id}")
  public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) throws Exception {
    File resource = fileService.downloadFile(id);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFileName() + "\"")
        .body(resource.getFileData());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteFile(@PathVariable Long id) throws Exception {
    fileService.deleteFile(id);
    return ResponseEntity.ok(RsDto.builder().result(true).data("Файл удален").build());
  }

}
