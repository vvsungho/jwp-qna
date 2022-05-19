package subway.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LineRepositoryTest {
    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    void saveWithLine() {
        final Station station = new Station("잠실역");

        // create문에 line_id가 있음
        station.setLine(lineRepository.save(new Line("8호선")));

        // create문에 line_id가 null
        station.setLine(new Line("8호선"));

        stationRepository.save(station);
        stationRepository.flush();
    }

    @Test
    @DisplayName("station이 line과 함께 조회되는지 안되는지")
    void findByNameWithLine() {
        final Station station = stationRepository.findByName("교대역").get();
        assertThat(station).isNotNull();
        assertThat(station.getLine().getName()).isEqualTo("3호선");
    }

    @Test
    @DisplayName("라인이 변경되는지, 변경감지가 잘 되는지")
    void updateWithLine() {
        final Station station = stationRepository.findByName("교대역").get();
        station.setLine(lineRepository.save(new Line("2호선")));
        stationRepository.flush();
    }

    @Test
    @DisplayName("연관관계 제거")
    void removeWithLine() {
        final Station station = stationRepository.findByName("교대역").get();
        station.setLine(null);
        stationRepository.flush();
    }

    @Test
    @DisplayName("양방향 검증")
    void 양방향_검증() {
        Line line = lineRepository.findByName("3호선");
        assertThat(line.getStationList()).hasSize(1);
    }

    @Test
    @DisplayName("line 입장에서 저장")
    void save() {
        final Line line = new Line("2호선");

        // 영속화 안되어있음. line_id가 null로 들어감
        //        line.addStation(new Station("잠실역"));

        // station을 영속화 상태로 설정해야함
        line.addStation(stationRepository.save(new Station("잠실역")));

        lineRepository.save(line);
        lineRepository.flush();
    }
}
