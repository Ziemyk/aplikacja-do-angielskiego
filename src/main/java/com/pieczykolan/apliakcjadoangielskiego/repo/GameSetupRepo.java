package com.pieczykolan.apliakcjadoangielskiego.repo;

import com.pieczykolan.apliakcjadoangielskiego.model.GameSetup;
import com.pieczykolan.apliakcjadoangielskiego.model.LevelOfWord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameSetupRepo extends CrudRepository<GameSetup, Integer> {
    GameSetup getBytypeOfWord(String type);
    GameSetup getFirstBytypeOfWord(String type);
    List<GameSetup> findAllByLevelOfWordAndTypeOfWord(LevelOfWord level, String type);
    List<GameSetup> findAllByLevelOfWordLessThanEqualAndTypeOfWord(LevelOfWord level, String type);

    //List<GameSetup> GetRandomByLevelOfWordLessThanEqualAndTypeOfWord(LevelOfWord level, String type);
}
