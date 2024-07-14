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
           /* create*/
            Member member1 = new Member();
            member1.setName("A");
            Member member2 = new Member();
            member2.setName("B");
            Member member3 = new Member();
            member3.setName("C");
            System.out.println("==============");
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            System.out.println("==============");
            //select
            //Member findMember = em.find(Member.class, 1L);
            //System.out.println("findMember = " + findMember.getName());

            //update
            //findMember.setName("HelloJPA");
            
            //delete
            // em.remove(findMember);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
