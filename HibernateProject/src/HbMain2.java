import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import hib.example.Employee;

public class HbMain2 {
	static SessionFactory sessionFactory;

	public static void main(String[] args) {

		Configuration cfg = new Configuration().configure();
		sessionFactory = cfg.buildSessionFactory();

		Employee em1 = new Employee("Mary Smith", 25);
		Employee em2 = new Employee("John Aces", 32);
		Employee em3 = new Employee("Ian Young", 29);

		System.out.println(" =======CREATE =======");
		create(em1);
		create(em2);
		create(em3);
		System.out.println(" =======READ =======");
		List<Employee> ems1 = read();
		for (Employee e : ems1) {
			System.out.println(e.toString());
		}
		System.out.println(" =======UPDATE =======");
		em1.setAge(44);
		em1.setName("Mary Rose");
		update(em1);
		System.out.println(" =======READ =======");
		List<Employee> ems2 = read();
		for (Employee e : ems2) {
			System.out.println(e);
		}
		System.out.println(" =======DELETE ======= ");
		delete(em2.getId());
		System.out.println(" =======READ =======");
		List<Employee> ems3 = read();
		for (Employee e : ems3) {
			System.out.println(e);
		}
		System.out.println(" =======DELETE ALL ======= ");
		deleteAll();
		System.out.println(" =======READ =======");
		List<Employee> ems4 = read();
		for (Employee e : ems4) {
			System.out.println(e);
		}
		sessionFactory.close();
	}

	public static Integer create(Employee e) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(e);
		session.getTransaction().commit();
		session.close();
		System.out.println("Successfully created " + e.toString());
		return e.getId();
	}

	public static List<Employee> read() {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<Employee> employees = session.createQuery("FROM Employee").list();
		session.close();
		System.out.println("Found " + employees.size() + " Employees");
		return employees;
	}

	public static void update(Employee e) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Employee em = (Employee) session.get(Employee.class, e.getId());
		em.setName(e.getName());
		em.setAge(e.getAge());
		session.getTransaction().commit();
		session.close();
		System.out.println("Successfully updated " + e);
	}

	public static void delete(Integer id) {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Employee e = (Employee) session.load(Employee.class, id);
		session.delete(e);
		session.getTransaction().commit();
		session.close();
		System.out.println("Successfully deleted " + e.toString());
	}

	public static void deleteAll() {
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("DELETE FROM Employee ");
		query.executeUpdate();
		session.getTransaction().commit();
		session.close();
		System.out.println("Successfully deleted all employees.");
	}

}