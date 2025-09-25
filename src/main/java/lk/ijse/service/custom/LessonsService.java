package lk.ijse.service.custom;


import lk.ijse.dto.LessonsDTO;
import lk.ijse.service.SuperService;

import java.util.List;
import java.util.Optional;

public interface LessonsService extends SuperService {
    List<LessonsDTO> getAllLessons() throws Exception;

    String getLastLessonId() throws Exception;

    boolean saveLessons(LessonsDTO t) throws Exception;

    boolean updateLessons(LessonsDTO t) throws Exception;

    boolean deleteLessons(String id) throws Exception;

    List<String> getAllLessonIds() throws Exception;

    Optional<LessonsDTO> findByLessonId(String id) throws Exception;
}
