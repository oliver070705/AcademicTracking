package tests.student;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import server.logic.model.Course;
import server.logic.model.Student;

public class TestCurrentCourse {

	@Test
	public void testCurrentCourse() {
		Student s = new Student(101075401, "tom", true);
		Course c1 = new Course("OO Software Dev", 105104, 30);
		Course c2 = new Course("Computational Geometry", 105008, 20);
		
		List<Course> currentCourses = new ArrayList<Course>();
		currentCourses.add(c1);
		currentCourses.add(c2);
		
		s.setRegisteredCourses(currentCourses);
		assertEquals(currentCourses, s.CurrentCourse());
	}

}
