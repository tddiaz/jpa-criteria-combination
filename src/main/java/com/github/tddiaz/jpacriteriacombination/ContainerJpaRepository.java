package com.github.tddiaz.jpacriteriacombination;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainerJpaRepository extends JpaRepository<Container, Long>, CustomContainerJpaRepository {
}
