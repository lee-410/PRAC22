package com.example.project.service;

import com.example.project.DTO.ProfileDTO;
import com.example.project.DTO.UploadResultDTO;
import com.example.project.Entity.*;
import com.example.project.repository.ProfileImageRepository;
import com.example.project.repository.ProfileRepository;
import com.example.project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class ProfileServiceImpl implements ProfileService{

    @Value("${com.example.profile.path}")
    private String uploadPath;

    private String imagePath;

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProfileImageRepository profileImageRepository;

    private String introduction ;

    //text
    @Override
    public String uploadIntro(String introductionText,Authentication authentication) {

        introduction = introductionText;

        String username = authentication.getName();

        Optional<Member> memberList = userRepository.findByUserid(username);

        if (memberList.isPresent()) {
            Member member = memberList.get();
            profileRepository.deleteByMember(member);
            Profile profile = Profile.builder()
                    .introduction(introduction)
                    .member(member)
                    .build();
            profileRepository.save(profile);

        } else {

        }
        return introduction;
    }

    @Override
    public List<ProfileDTO> uploadProfile(MultipartFile[] uploadFiles, Authentication authentication) {

        List<ProfileDTO> resultDTOList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles) {


            if (uploadFile.getContentType().startsWith("image") == false) {
                throw new IllegalArgumentException("Only image files are allowed.");
            }

            String originalName = uploadFile.getOriginalFilename();

            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            // 날짜 폴더 생성
            String folderPath = makeFolder();

            //UUID
            String uuid = UUID.randomUUID().toString();

            //이미지 저장
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(saveName);

            try {
                uploadFile.transferTo(savePath);

                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator +"s_" + uuid +"_"+ fileName;

                File thumbnailFile = new File(thumbnailSaveName);

                Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,300,300);// 섬네일 생성

                imagePath = thumbnailSaveName;
                resultDTOList.add(new ProfileDTO(fileName,uuid,folderPath));
            }catch (IOException e){
                e.printStackTrace();
            }

            String username = authentication.getName();


            Optional<Member> memberList = userRepository.findByUserid(username);

            if (!memberList.isEmpty()) {
                Member member = memberList.get();
                ProfileImage profileImage = ProfileImage.builder()
                        .folderPath(folderPath)
                        .uuid(uuid)
                        .fileName(fileName)
                        .member(member)
                        .build();
                profileImageRepository.save(profileImage);
            } else {
                //entity에 userid와 일치하는 user가 없을 때 처리

            }
        }

        return resultDTOList;
    }

    public List<ProfileDTO> getUploadedImagesByUserId(String userId) {
        log.info("getUploadedImagesByUserId: userId=={}", userId);

        List<ProfileDTO> userImages = new ArrayList<>();

        List<ProfileImage> userprofile = profileImageRepository.findByMemberUserid(userId);

        for (ProfileImage profileImage : userprofile) {
            // 각 이미지의 정보를 UploadResultDTO로 변환하여 결과 목록에 추가
            ProfileDTO dto = new ProfileDTO(profileImage.getFileName(), profileImage.getUuid(), profileImage.getFolderPath());

            userImages.add(dto);
        }

        log.info("getUploadedImagesByUserId: userImages=={}", userImages);
        // profileimage 엔티티에 빌더한다

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
