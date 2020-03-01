package com.github.tddiaz.jpacriteriacombination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class ContainerJpaRepositoryTest {

    @Autowired
    private ContainerJpaRepository containerJpaRepository;

    @BeforeEach
    void setup() {
        containerJpaRepository.saveAll(Arrays.asList(
                Container.create("123", "export", "active"),
                Container.create("1234", "import", "inactive"),
                Container.create("12345", "export", "active"),
                Container.create("123456", "import", "active")
        ));
    }

    @Test
    void testQuery() {
        {

            var c1 = Container.create("123", "export", "active");

            List<Container> containers = containerJpaRepository.findAllByCombinationOfNumberAndMoveTypeAndStatus(Collections.singletonList(c1));
            assertThat(containers).hasSize(1);
        }
        {

            var c1 = Container.create("123", "export", "active");
            var c2 = Container.create("1234", "import", "inactive");

            List<Container> containers = containerJpaRepository.findAllByCombinationOfNumberAndMoveTypeAndStatus(Arrays.asList(c1, c2));
            assertThat(containers).hasSize(2);
        }
        {

            var c1 = Container.create("123", "export", "active");
            var c2 = Container.create("1234", "import", "inactive");
            var c3 = Container.create("12345", "export", "active");

            List<Container> containers = containerJpaRepository.findAllByCombinationOfNumberAndMoveTypeAndStatus(Arrays.asList(c1, c2, c3));
            assertThat(containers).hasSize(3);
        }
        {

            var c1 = Container.create("123", "export", "active");
            var c2 = Container.create("1234", "import", "inactive");
            var c3 = Container.create("12345", "export", "active");
            var c4 = Container.create("123456", "import", "active");

            List<Container> containers = containerJpaRepository.findAllByCombinationOfNumberAndMoveTypeAndStatus(Arrays.asList(c1, c2, c3, c4));
            assertThat(containers).hasSize(4);
        }
        {

            var c1 = Container.create("123", "export", "active");
            var c2 = Container.create("1234", "import", "active");
            var c3 = Container.create("12345", "export", "inactive");
            var c4 = Container.create("123456", "import", "inactive");

            List<Container> containers = containerJpaRepository.findAllByCombinationOfNumberAndMoveTypeAndStatus(Arrays.asList(c1, c2, c3, c4));
            assertThat(containers).hasSize(1);
        }
    }
}