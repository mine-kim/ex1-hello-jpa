package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args){
        //하나 생성하여 애플리케이션 전체에서 공유
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 쓰레드간에 공유X 요청이 올떄마다 생성
        EntityManager em = emf.createEntityManager();

        //JPA의 모든 데이터 변경은 트렌젝션 안에서 실행
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member");
            member.setHomeAddress(new Address("city","street","zipcode"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddresHistory().add(new AddressEntity("old_city1","old_street1","old_zipcode1"));
            member.getAddresHistory().add(new AddressEntity("old_city2","old_street2","old_zipcode2"));

            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            findMember.setHomeAddress(new Address("new_city",member.getHomeAddress().getStreet(),member.getHomeAddress().getZipcode()));

            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("햄버거");

            // 실제 동작시, id에 해당하는 모든 데이터를 테이블에서 삭제 후, 다시 insert
            findMember.getAddresHistory().remove(new AddressEntity("old_city1","old_street1","old_zipcode1"));
            findMember.getAddresHistory().add(new AddressEntity("new_city1","old_street1","old_zipcode1"));

            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
