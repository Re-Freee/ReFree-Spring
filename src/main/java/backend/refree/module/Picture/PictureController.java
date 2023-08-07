package backend.refree.module.Picture;

import backend.refree.infra.response.BasicResponse;
import backend.refree.infra.response.GeneralResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Slf4j
@Controller
public class PictureController {
    private final PictureService pictureService;

    @Autowired
    public PictureController(PictureService pictureService){
        this.pictureService=pictureService;
    }


    @RequestMapping(value="/images",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<? extends BasicResponse> uploadImg(@RequestParam("userImg") MultipartFile file) throws IOException{
        String imgUrl=pictureService.uploadImg(file);

        if(imgUrl==null) {
            return ResponseEntity.ok().body(new GeneralResponse<>(null,"FAIL_UPLOAD"));
        }
        return ResponseEntity.ok().body(new GeneralResponse<>(imgUrl,"SUCCESS_UPLOAD"));
    }

    @GetMapping("imageView")
    public ResponseEntity<? extends BasicResponse> view(@RequestParam int id){
        return ResponseEntity.ok().body(new GeneralResponse<>(pictureService.view(id),"VIEW_INGREDIENT"));
    }

    @RequestMapping(value="/check",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<? extends BasicResponse> check(@RequestParam("userImg") MultipartFile file) throws IOException {
        String imgUrl=pictureService.check(file);

        if(imgUrl==null) {
            return ResponseEntity.ok().body(new GeneralResponse<>(null,"FAIL_UPLOAD"));
        }
        return ResponseEntity.ok().body(new GeneralResponse<>(imgUrl,"SUCCESS_UPLOAD"));
    }
}
