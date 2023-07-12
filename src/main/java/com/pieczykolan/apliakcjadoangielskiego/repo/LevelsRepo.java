package com.pieczykolan.apliakcjadoangielskiego.repo;

import com.pieczykolan.apliakcjadoangielskiego.Entity.LevelsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface LevelsRepo extends CrudRepository<LevelsEntity,Integer> {


    @Query("update LevelsEntity u set u = ?1 where u.userId = ?2")
    int updateLevelByUserId(LevelsEntity levelsEntity, int userId);
    @Query("select l from LevelsEntity l where l.userId = ?1")
    LevelsEntity findByUserId(int userId);
}
