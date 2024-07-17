package hellojpa;

import javax.persistence.*;

/**
 * @Inheritance(strategy=InheritanceType.XXX)
 * JOINED: 조인 전략 (각각 테이블로 변환)
 * SINGLE_TABLE: 단일 테이블 전략 (통합 테이블로 변환)
 * TABLE_PER_CLASS: 구현 클래스마다 테이블 전략 - (추천X)
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Item {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private  int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
