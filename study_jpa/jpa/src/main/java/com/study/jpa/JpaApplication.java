package com.study.jpa;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

@SpringBootApplication
public class JpaApplication {

	public static void main(String[] args) {
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		try {
	
			Team team = new Team();
			team.setName("TeamA");
			em.persist(team);
			
			Member member = new Member();
			member.setUsername("회원 1");
			member.setTeamId(team.getId()); //회원 1을 TeamA에 소속시키고자 함
			em.persist(member);
			
			
			tx.commit();
			
		} catch (Exception e) {
		    e.printStackTrace();//추가
			tx.rollback();
		}finally {
			em.close();
		}
		
		emf.close();

		}
		
	}
