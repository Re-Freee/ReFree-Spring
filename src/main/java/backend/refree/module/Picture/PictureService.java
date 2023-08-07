package backend.refree.module.Picture;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PictureService {
    Picture view(int id);
    String uploadImg(MultipartFile file) throws IOException;
    String check(MultipartFile file) throws IOException;
}
