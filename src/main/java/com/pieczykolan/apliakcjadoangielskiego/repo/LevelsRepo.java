package com.pieczykolan.apliakcjadoangielskiego.repo;

import com.pieczykolan.apliakcjadoangielskiego.model.LevelsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface LevelsRepo extends CrudRepository<LevelsEntity,Integer> {


    @Query("update LevelsEntity u set u = ?1 where u.userId = ?2")
    int updateLevelByUserId(LevelsEntity levelsEntity, int userId);
}
