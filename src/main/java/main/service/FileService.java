package main.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import main.exception.UserException;
import main.model.File;
import main.repository.FileRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

  private final FileRepo fileRepo;

  public void saveFile(MultipartFile file) throws UserException {
    try {
      File newFile = new File();
      newFile.setFileName(file.getOriginalFilename());
      newFile.setFileData(file.getBytes());
      fileRepo.save(newFile);
    } catch (Exception e) {
      throw new UserException("Ошибка передачи файла");
    }
  }

  public List<String> getListFiles() {
    return fileRepo.findAll().stream().map(File::getFileName).toList();
  }

  public File downloadFile(Long id) throws UserException {
    return findFileById(id);
  }

  public void deleteFile(long id) throws UserException {
    File file = findFileById(id);
    fileRepo.delete(file);
  }

  private File findFileById(Long id) throws UserException {
    return fileRepo.findById(id).orElseThrow(() -> new UserException("Файл не найден"));
  }

}
