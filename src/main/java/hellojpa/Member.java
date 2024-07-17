package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@SequenceGenerator(name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", // 매핑할 데이터베이스 시퀀스 이름
initialValue = 1, allocationSize = 50)
public class Member extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String username;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team team;

    @Embedded
    private Address homeAddress;

    /**
     *  @ElementCollection
     * 값 타입은 엔티티와 다르게 식별자 개념이 없다.
     * 값은 변경하면 추적이 어렵다.
     * 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다\
     * 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본 키를 구성해야 함: null 입력X, 중복 저장X
     */
    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

/*
    @ElementCollection
    @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    private List<Address> addresHistory = new ArrayList<>();
*/

    /**
     * 실무에서는 @ElementCollection 보다는 아래와 같은 형식을 추천
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addresHistory = new ArrayList<>();

    /**
     * 한 엔티티에서 같은 값 타입을 사용하려면,  @AttributeOverrides, @AttributeOverride 사용
     */
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "city", column = @Column(name = "work_city")),
            @AttributeOverride(name = "street", column = @Column(name = "work_street")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "work_zipcode")),
    })
    private Address workAddress;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
      //  team.getMembers().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Address getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(Address workAddress) {
        this.workAddress = workAddress;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<AddressEntity> getAddresHistory() {
        return addresHistory;
    }

    public void setAddresHistory(List<AddressEntity> addresHistory) {
        this.addresHistory = addresHistory;
    }
}
