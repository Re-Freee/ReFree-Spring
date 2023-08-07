package backend.refree.module.Picture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectAclRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.annotation.PostConstruct;
import javax.persistence.Id;
import java.io.IOException;
import java.util.Optional;


public class PictureServiceImpl implements PictureService {
    @Value("${aws.s3.accessKey}")
    private String accessKey;
    @Value("${aws.s3.secretKey}")
    private String secretKey;
    @Value("${aws.s3.bucket}")
    private String bucket;

    private S3Client s3Client;

    private final PictureRepository pictureRepository;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository){
        this.pictureRepository=pictureRepository;
    }

    @PostConstruct
    public void setS3Client(){
        AwsCredentials credentials=new AwsCredentials() {
            @Override
            public String accessKeyId() {
                return accessKey;
            }

            @Override
            public String secretAccessKey() {
                return secretKey;
            }
        };
        AwsCredentialsProvider credentialsProvider = () -> credentials;
        this.s3Client= S3Client.builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.AP_NORTHEAST_2)
                .build();
    }
    @Override
    public String check(MultipartFile file) throws IOException {
        String inputs=file.getOriginalFilename();
        Picture picture=new Picture();
        picture.setOriginal_picture_name(inputs);
        pictureRepository.save(picture);

        return inputs;
    }
    @Override
    public String uploadImg(MultipartFile file) throws IOException{
        Picture image=new Picture();
        //String fileName=file.getOriginalFilename();
        PutObjectRequest request= PutObjectRequest.builder()
                .bucket(bucket)
                .key(secretKey)
                .build();

        //s3Client.putObject(new PutObjectRequest(bucket,fileName,file.getInputStream(),null)
        //.withCanndedAcl(CannedAccessControlList.PUBLIC_READ));
        s3Client.putObject(request,
                RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        //s3Client.putObjectAcl(request.builder().acl("public-read").build());
        PutObjectAclRequest aclRequest = PutObjectAclRequest.builder()
                .bucket(bucket)
                .key(secretKey)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        // 객체의 ACL 설정
        s3Client.putObjectAcl(aclRequest);

        // 이미지 URL 생성
        String imageUrl = "https://" + bucket + ".s3.amazonaws.com/" + secretKey;
        image.setOriginal_picture_name(file.getOriginalFilename());
        image.setPicture_url(imageUrl);
        image.setStorage_picture_name(bucket);
        pictureRepository.save(image);

        return imageUrl;
    }
    @Override
    public Picture view(int id){
        Optional<Picture> ingredient2= pictureRepository.findById(id);
        Picture ingredient1=ingredient2.get();
        return ingredient1;
    }

}
