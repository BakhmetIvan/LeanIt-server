package com.leanitserver.repository;

import com.leanitserver.model.Video;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    @Query("SELECT v FROM Video v WHERE v.title LIKE %:title%")
    List<Video> findAllByTitle(String title);

    List<Video> findAllByIdIn(List<Long> id);
}
