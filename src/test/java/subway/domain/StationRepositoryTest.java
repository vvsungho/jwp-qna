package subway.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import qna.NotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StationRepositoryTest {
    @Autowired
    private StationRepository stationRepository;

    @Test
    @DisplayName("save test")
    void save() {
        final Station expected = new Station("잠실역");
        final Station actual = stationRepository.save(expected);
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    @DisplayName("select test")
    void findByName() {
        stationRepository.save(new Station("잠실역"));
        final Station actual = stationRepository.findByName("잠실역").orElseThrow(NotFoundException::new);
        assertThat(actual).isNotNull();
    }

    @Test
    void identity() {
        final Station station1 = stationRepository.save(new Station("잠실역"));

//        final Station station2 = stationRepository.findByName("잠실역").get();
        // id로 조회하면 1차캐시에 있는 내용으로 조회하므로, select query를 날리지 않음, 1차캐시에 없으면 그 때서야 select를 조회하고 1차캐시에 저장한다. 그 다음 누군가가 조회하면 캐시내용을 반환한다.
        final Station station2 = stationRepository.findById(station1.getId()).get();

        // isSameAs : ==
        assertThat(station1).isSameAs(station2);
    }

    @Test
    void save2() {
        final Station station1 = stationRepository.save(new Station(1L, "잠실역"));
        assertThat(station1.getId()).isNotNull();
        assertThat(station1.getName()).isEqualTo("잠실역");
//        stationRepository.flush();
    }

    @Test
    @DisplayName("변경감지 확인, 더티체킹")
    void update() {
        final Station station1 = stationRepository.save(new Station("잠실역"));
        station1.chanageName("몽촌토성역");

        final Station station2 = stationRepository.findByName("몽촌토성역").get();

        assertThat(station2).isNotNull();
    }
}
