package com.ejemplo.springboor.firebase.springbootfirebase.services;

import com.ejemplo.springboor.firebase.springbootfirebase.dtos.UrlImageDto;
import com.ejemplo.springboor.firebase.springbootfirebase.exceptions.MyBadRequestException;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static  com.ejemplo.springboor.firebase.springbootfirebase.utils.MainConstants.*;

//https://firebasestorage.googleapis.com/v0/b/<bucker>/o/JPimagenes%2F5604406e-5aca-46a2-8d9d-3e2fa1eb407b.png?alt=media&token=11dfaca5-1d6a-42e7-b265-2979e903f2ae

@Service
public class FirebaseService {
    private final String folderName = "ejemplospringtypescript/";

    private Storage getStorage(){
        return StorageClient.getInstance().bucket().getStorage();
    }
    public UrlImageDto getFile(String name){
        String fileName = folderName+name;
        Storage storage = getStorage();
        Blob blob = storage.get(BlobId.of(BUCKET, fileName));

        if(blob == null)
            throw new MyBadRequestException("Image name does not exist");

        String stringBuilder = "https://firebasestorage.googleapis.com/v0/b/" +
                BUCKET + "/o/" +
                fileName.replace("/", "%2F") + "?alt=media";
        UrlImageDto urlImageDto = new UrlImageDto();
        urlImageDto.setUrlImage(stringBuilder);
        return urlImageDto;
    }

    public UrlImageDto createImage(MultipartFile multipartFile) {
        String imageName = multipartFile.getOriginalFilename();
        String fileName = folderName+UUID.randomUUID()+imageName;
        Storage storage = getStorage();
        BlobId blobId = BlobId.of(BUCKET, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(multipartFile.getContentType())
                .build();
        try {
            storage.create(blobInfo, multipartFile.getBytes());
            String stringBuilder = "https://firebasestorage.googleapis.com/v0/b/" +
                    BUCKET + "/o/" +
                    fileName.replace("/", "%2F") +"?alt=media";
            UrlImageDto urlImageDto = new UrlImageDto();
            urlImageDto.setUrlImage(stringBuilder);
            return urlImageDto;
        } catch (IOException e) {
            throw new MyBadRequestException(e.getMessage());
        }
    }

    public void deleteImage(String name){
        String fileName = folderName+name;
        Storage storage = getStorage();
        Blob blob = storage.get(BlobId.of(BUCKET, fileName));
        if(blob == null){
            throw new MyBadRequestException("Image name invalid");
        }

        blob.delete();
    }
}
