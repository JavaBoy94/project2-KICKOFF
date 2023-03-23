package org.project2.omwp2.boardFile.reposistory;

import org.project2.omwp2.entity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardFileReposistory extends JpaRepository<BoardFileEntity, Long> {
}
