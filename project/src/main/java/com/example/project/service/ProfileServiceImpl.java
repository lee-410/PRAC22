package com.example.project.service;

import com.example.project.DTO.ProfileDTO;
import com.example.project.DTO.UploadResultDTO;
import com.example.project.Entity.Feed;
import com.example.project.Entity.Images;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
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
import java.util.UUID;

@Slf4j
@Transactional
@Service
public class ProfileServiceImpl implements ProfileService{

    @Value("${com.example.profile.path}")
    private String uploadPath;

    private String imagePath;

    @Override
    public List<ProfileDTO> uploadProfile(MultipartFile[] uploadFiles) {

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

                String thubmnailSaveName = uploadPath + File.separator + folderPath + File.separator +"s_" + uuid +"_"+ fileName;

                File thumbnailFile = new File(thubmnailSaveName);

                Thumbnailator.createThumbnail(savePath.toFile(),thumbnailFile,300,300);// 섬네일 생성

                imagePath = thubmnailSaveName;
                resultDTOList.add(new ProfileDTO(fileName,uuid,folderPath));
            }catch (IOException e){
                e.printStackTrace();
            }

//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//            String username = authentication.getName();

        }

        return resultDTOList;
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
