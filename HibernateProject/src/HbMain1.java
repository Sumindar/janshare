import java.util.List;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import hib.example.*;


public class HbMain1 {

	private static  SessionFactory sessionFactory;
	
	public static void main(String[] args) {
		
		Configuration cfg = new Configuration().configure();
		sessionFactory = cfg.buildSessionFactory();
		Long courseId1 = saveCourse("Physics");
		Long courseId2 = saveCourse("Chemistry");
		Long courseId3 = saveCourse("Maths");
		listCourse();
		updateCourse(courseId3, "Mathematics");
		deleteCourse(courseId2);
		listCourse();
		sessionFactory.close();
		
	}
	
	 static Long saveCourse(String courseName)
	{
				
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		Long courseId = null;
		try {
			transaction = session.beginTransaction();
			Course course = new Course();
			course.setCourseName(courseName);
			courseId = (Long) session.save(course);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return courseId;
	}
	
	static void listCourse()
	{
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			List<Course>  courses = session.createQuery("from Course").list();
			for (Course c: courses)
			{
				System.out.println(c.getCourseName());
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	static void updateCourse(Long courseId, String courseName)
	{
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Course course = (Course) session.get(Course.class, courseId);
			course.setCourseName(courseName);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	
	static void deleteCourse(Long courseId)
	{
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			Course course = (Course) session.get(Course.class, courseId);
			session.delete(course);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
}
