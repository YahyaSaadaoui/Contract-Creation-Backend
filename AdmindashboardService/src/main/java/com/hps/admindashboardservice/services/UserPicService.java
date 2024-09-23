package com.hps.admindashboardservice.services;

import com.hps.admindashboardservice.entities.user;
import com.hps.admindashboardservice.repos.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPicService {
    @Autowired
    private userRepo userRepository;

    public void updateUserProfileImage(Long userId, String imageUrl) {
        user user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setImageUrl(imageUrl);
        userRepository.save(user);
    }


//    @Autowired
//    private userRepo userRepository;
//
//    @Autowired
//    private settingsRepo settingsRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private S3Client s3Client;
//
//    @Value("${cloud.aws.s3.bucket-name}")
//    private String bucketName;
//
//    public String uploadUserImage(long userId, MultipartFile imageFile) {
//        user user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        String fileName = "users/" + userId + "/" + imageFile.getOriginalFilename();
//
//        try (InputStream inputStream = imageFile.getInputStream()) {
//            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(fileName)
//                    .build();
//            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, imageFile.getSize()));
//        } catch (IOException e) {
//            throw new RuntimeException("Error uploading file to S3", e);
//        }
//
//        String imageUrl = s3Client.utilities().getUrl(GetUrlRequest.builder()
//                .bucket(bucketName)
//                .key(fileName)
//                .build()).toString();
//        user.setImageUrl(imageUrl);
//        userRepository.save(user);
//
//        return imageUrl;
//    }
}
