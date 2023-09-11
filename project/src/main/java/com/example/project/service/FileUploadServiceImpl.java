package com.example.project.service;

import com.example.project.DTO.UploadResultDTO;
import com.example.project.Entity.Feed;
import com.example.project.Entity.Images;
import com.example.project.Entity.Member;
import com.example.project.repository.FeedRepository;
import com.example.project.repository.ImagesRepository;
import com.example.project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Transactional
@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Value("${com.example.upload.path}")
    private String uploadPath;

    private String imagePath;

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UploadResultDTO> uploadFiles(MultipartFile[] uploadFiles) {
        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles) {

            // 이미지 파일만 업로드 가능->이미지인지 확인하고 아니면 403 Forbidden반환

            if (uploadFile.getContentType().startsWith("image") == false) {
                throw new IllegalArgumentException("Only image files are allowed.");
            } //타입에러나서 변경
//            if(uploadFile.getContentType().startsWith("image") == false){
//                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//            }//getContentType()는 업로드된파일의 MIME타입(확장자)을 확인, startsWith는 그 문자열이 image로 시작하는지 확인(MIME ex. image/jpeg)

            // 실제 파일 이름 IE나 Edge는 전체 경로가 들어오므로
            // 내가 이미지를 올릴때 C드라이브 들어가서 폴더 경유하고 업로드하기 때문에 그 경로까지 이미지에 포함되어있다. 그래서 그 경로를 제거하고 오로지 파일이름만을 얻기위한 처리가 필요하다.
            String originalName = uploadFile.getOriginalFilename();
            //lastIndexOf는 파일이름에서 마지막 디렉토리 구분자의 인덱스를 찾고, 그 다음 인덱스부터 문자열을 자르는 작업을 수행한다.
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            // 날짜 폴더 생성
            String folderPath = makeFolder();

            //UUID
            String uuid = UUID.randomUUID().toString();

            //이미지 저장
            //File.separator는 / 역할을 하는데, window(\)Linux(/)을 사용하기 때문에 separator를 사용해서 플랫폼상관없이 구분자를 넣을 수 있다.
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(saveName);

            try {
                uploadFile.transferTo(savePath);// 실제 이미지 저장(원본 파일)

                //섬네일 생성 -> 섬네일 파일 이름은 중간에 s_로 시작
                String thubmnailSaveName = uploadPath + File.separator + folderPath + File.separator +"s_" + uuid +"_"+ fileName;

                File thumbnailFile = new File(thubmnailSaveName);

                Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,700,700);// 섬네일 생성

                imagePath = thubmnailSaveName;
                resultDTOList.add(new UploadResultDTO(fileName,uuid,folderPath));
            }catch (IOException e){
                e.printStackTrace();
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();

            List<Feed> feedList = feedRepository.findByMemberUserid(username);

            if (!feedList.isEmpty()) {
                Feed feed = feedList.get(0); //이거 때문에 계속 feedid의 1을 가져오는것같애. 수정필요!!!
                Images images = Images.builder()
                        .folderPath(folderPath)
                        .uuid(uuid)
                        .fileName(fileName)
                        .feed(feed)
                        .build();
                imagesRepository.save(images);
            } else {
                //entity에 userid와 일치하는 user가 없을 때 처리

            }
        }

        return resultDTOList;
    }

    public List<UploadResultDTO> getUploadedImagesByUserId(String userId) {
        log.info("getUploadedImagesByUserId: userId=={}", userId);
        List<UploadResultDTO> userImages = new ArrayList<>();

        //userId에 맞는 값 조회
        List<Images> userImagesEntities = imagesRepository.findByUserId(userId);
        log.info("getUploadedImagesByUserId: userImagesEntities=={}", userImagesEntities);


        for (Images images : userImagesEntities) {
            // 각 이미지의 정보를 UploadResultDTO로 변환하여 결과 목록에 추가
            UploadResultDTO dto = new UploadResultDTO(images.getFileName(), images.getUuid(), images.getFolderPath());
            userImages.add(dto);
        }

        log.info("getUploadedImagesByUserId: userImages=={}", userImages);

        return userImages;
    }

    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath = str.replace("/", File.separator);

        // make folder ----
        File uploadPatheFolder = new File(uploadPath,folderPath);

        if(uploadPatheFolder.exists() == false){
            uploadPatheFolder.mkdirs();
        }

        return folderPath;
    }
}

