package com.github.tddiaz.jpacriteriacombination;

import java.util.List;

public interface CustomContainerJpaRepository {
    List<Container> findAllByCombinationOfNumberAndMoveTypeAndStatus(List<Container> containers);
}
