package com.kir;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("com.kir");

        SessionFactory sessionFactory = context.getBean(SessionFactory.class);
        Session session = sessionFactory.openSession();

        Student student1 = new Student("misha", 22);
        Student student2 = new Student("pashsa", 20);

        session.beginTransaction();
        session.persist(student1);
        session.persist(student2);
        session.getTransaction().commit();

        Student studentById1 = session.get(Student.class, 1L);
        System.out.println(studentById1);

        Student studentById2 = session.createQuery(
                        "SELECT s from Student s  where s.id = :id", Student.class
                ).setParameter("id", 2)
                .getSingleResult();
        System.out.println(studentById2);

        // Т.к. сессия следит за объектом, то при коммите будет обращение к бд для обновления
        session.beginTransaction();
        Student studentForUpdate = session.get(Student.class, 1);
        studentForUpdate.setAge(30);
        studentForUpdate.setName("dima");

        session.getTransaction().commit();

        session.beginTransaction();
//        Student studentForDelete = session.get(Student.class, 2);
//        session.remove(studentForDelete);

//        session.createQuery(
//                "DELETE FROM Student s where s.id = 2")
//                        .executeUpdate();

//        session.createNativeQuery("""
//                            delete from students s
//                            where s.id = 2;
//                """).executeUpdate();
        session.getTransaction().commit();

        List<Student> allStudents = session.createQuery("SELECT s FROM Student s", Student.class)
                        .list();

        Student studentByName = session.createQuery(
                        "SELECT s from Student s  where s.name = :name", Student.class
                ).setParameter("name", "pashsa")
                .getSingleResult();
        System.out.println("stud by nmae"+studentByName);
        session.close();
    }
}