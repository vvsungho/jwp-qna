package subway.domain;

import javax.persistence.*;

@Entity // (1)
@Table(name = "station") // (2)
//public class Station implements Persistable {
public class Station {
    @Id // (3)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // (4)
    private Long id;

    @Column(name = "name", nullable = false) // (5)
    private String name;

    @ManyToOne
//    @JoinColumn(name = "line_id") // default : table 명 +  "_" + table id
    private Line line;

    // 빈생성자 필수, public지양, protected 선호
    protected Station(String name) { // (6)
        this.name = name;
    }

    public Station() {

    }

    public Station(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Line getLine() {
        return line;
    }

    public void chanageName(String name) {
        this.name = name;
    }

    /**
     * 무조건 새로운 entity임을 알 때 select query를 사용하고 싶지 않을 때 사용할 수 있다.
     * @return
     */
//    @Override
//    public boolean isNew() {
//        return true;
//    }

}

