package org.project2.omwp2.boardFile.service;

import org.project2.omwp2.boardFile.reposistory.BoardFileReposistory;
import org.springframework.stereotype.Service;

@Service
public class BoardFileService {

    private BoardFileReposistory fileReposistory;

    public BoardFileService(BoardFileReposistory fileReposistory){
        this.fileReposistory = fileReposistory;
    }

//    @Transactional
//    public Long saveFile(BoardFileDto fileDto) {
//        return  fileReposistory.save(fileDto.toEntity()).getBfileId();
//    }

//    @Transactional
//    public BoardFileDto getFile(Long bfileId){
//        BoardFileEntity file = fileReposistory.findById(bfileId).get();
//
//        BoardFileDto boardFileDto = BoardFileDto.builder()
//                .bfileId(bfileId)
//                .bfileOldName(file.getBfileOldName())
//                .bfileNewName(file.getBfileNewName())
//                .filePath(file.getFilePath())
//                .build();
//        return  boardFileDto;
//    }




}
