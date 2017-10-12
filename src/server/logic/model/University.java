package server.logic.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import utilities.Config;
import utilities.Trace;

public class University implements UniversityInt {
	
	private Logger logger = Trace.getInstance().getLogger("opreation_file");

	static int universityCourses;
	static int maxCourseForFTStudent = 4;
	static int maxCourseForPTStudent = 2;
	static int passRate = 70;
	
	List<Course> courses = new ArrayList<Course>();
	List<Student> students = new ArrayList<Student>();
	
	final Timer timer = new Timer();
	
	static int currentstudent;
	
	private static class UniversityHolder {
		private static final University INSTANCE = new University();
	}
	
	public University() {
		super();
		InitializeCourses();
		InitializeStudents();
		universityCourses = courses.size();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				TermEnds();
			}
			
		}, 84 * Config.STIMULATED_DAY);
		logger.info(String.format("Operation:Initialize University;courses: %s", courses));
		logger.info(String.format("Operation:Initialize University;courses: %s", students));
	}
	
	private void TermEnds() {
		// TODO Auto-generated method stub
		
	}

	public static final University getInstance() {
		return UniversityHolder.INSTANCE;
	}
	
	private void InitializeCourses() {
		// TODO Auto-generated method stub
		ProjectCourse c1 = new ProjectCourse("OO Software Dev", 105104, 30, false, 0, 3, false);
		ProjectCourse c2 = new ProjectCourse("Computational Geometry", 105008, 20, false, 0, 3, false);
		Course c3 = new Course("Principles of Distributed Computing", 105003, 20, false, 1, 2, true);
		Course c4 = new Course("Advanced Database Systems", 105305, 30, false, 2, 2, true);
		Course c5 = new Course("Foundations of Programming Languages", 105001, 30, false, 1, 3, true);
		courses.add(c1);
		courses.add(c2);
		courses.add(c3);
		courses.add(c4);
		courses.add(c5);
		logger.info(String.format("Operation: Initialize CourseList; Students: %s", courses));
	}

	private void InitializeStudents() {
		// TODO Auto-generated method stub
		int[] studentNumberList = new int[]{101075401, 101075402};
		String[] studentNameList = new String[]{"tom","jack"};
		boolean[] isFullTimeList = new boolean[]{true,false};
		for(int i=0;i<studentNumberList.length;i++) {
			Student s = new Student(studentNumberList[i], studentNameList[i], isFullTimeList[i]);
			students.add(s);
		}
		logger.info(String.format("Operation: Initialize StudentList; Students: %s", students));
	}

	public int getUniversityCourses() {
		return universityCourses;
	}

	public void setUniversityCourses(int universityCourses) {
		University.universityCourses = universityCourses;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public int getCurrentstudent() {
		return currentstudent;
	}

	public void setCurrentstudent(int currentstudent) {
		University.currentstudent = currentstudent;
	}

	@Override
	public List<Course> Courses() {
		// TODO Auto-generated method stub
		return getCourses();
	}

	@Override
	public List<Student> Students() {
		// TODO Auto-generated method stub
		return getStudents();
	}
	
	@Override
	public Student GetStudent(int number) {
		// TODO Auto-generated method stub
		Student student = null;
		for (int i=0; i<students.size(); i++) {
			if (students.get(i).StudentNumber() == number) {
				student = students.get(i);
			}
		}
		return student;
	}

	@Override
	public Course GetCourse(int mycode) {
		// TODO Auto-generated method stub
		Course course = null;
		for (int i=0; i<courses.size(); i++) {
			if (courses.get(i).Code() == mycode) {
				course = courses.get(i);
			}
		}
		return course;
	}
	
	@Override
	public boolean CheckCourse(int mycode) {
		// TODO Auto-generated method stub
		int flag = 0;
		boolean result = true;
		for (int i=0; i<courses.size(); i++) {
			if (courses.get(i).Code() == mycode) {
				flag = flag + 1;
			}
		}
		if (flag!=0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}
	
	@Override
	public boolean CheckStudent(int number) {
		// TODO Auto-generated method stub
		int flag = 0;
		boolean result = true;
		for (int i=0; i<students.size(); i++) {
			if (students.get(i).StudentNumber() == number) {
				flag = flag + 1;
			}
		}
		if (flag!=0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public boolean LookupStudent(int number, String name) {
		// TODO Auto-generated method stub
		boolean result = true;
		int flag = 0;
		for (int i=0; i<students.size(); i++) {
			int studentnumber = students.get(i).StudentNumber();
			String studentname = students.get(i).Name();
			if (studentnumber==number && studentname.equalsIgnoreCase(studentname)) {
				flag = flag + 1;
			}
		}
		if (flag!=0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public boolean CreateCourse(String title, int mycode, int cap, 
			boolean enforcePrereqs, int numberofmidterms, 
			int numberofassignments, boolean hasafinal, 
			boolean isprojectcourse) {
		// TODO Auto-generated method stub
		boolean result = true;
		/*
		int flag = 0;
		for (int i=0; i<courses.size(); i++) {
			String coursetitle = courses.get(i).Title();
			int coursemycode = courses.get(i).Code();
			if (coursetitle.equalsIgnoreCase(title)||coursemycode == mycode) {
				flag = flag + 1;
			}
		}
		*/
		if (!CheckCourse(mycode)) {
			if (!isprojectcourse) {
				Course c = new Course(title, mycode, cap, enforcePrereqs, numberofmidterms, numberofassignments, hasafinal);
				result = courses.add(c);
				logger.info(String.format("Operation: Create New Course; Course Info:[%s,%d]; State: Success", title, mycode));
			} else {
				ProjectCourse c = new ProjectCourse(title, mycode, cap,enforcePrereqs, numberofmidterms, numberofassignments,hasafinal);
				result = courses.add(c);
				logger.info(String.format("Operation: Create New Project Course; Course Info:[%s,%d]; State: Success", title, mycode));
			}
		} else {
			result = false;
			logger.info(String.format("Operation: Create New Course; Course Info:[%s,%d]; State: Fail; Reason: The course already existed.", title, mycode));
		}
		return result;
	}

	@Override
	public boolean CreateStudent(int number, String name,
			boolean isfulltime) {
		// TODO Auto-generated method stub
		boolean result = true;
		/*
		int flag = 0;
		for (int i=0; i<students.size(); i++) {
			int studentnumber = students.get(i).StudentNumber();
			if (studentnumber==number) {
				flag = flag + 1;
			}
		}
		*/
		if (!CheckStudent(number)) {
			Student s = new Student(number, name, isfulltime);
			result = students.add(s);
			logger.info(String.format("Operation: Create New Student; Student Info:[%d,%s]; State: Success", number, name));
		} else {
			result = false;
			logger.info(String.format("Operation: Create New Student; Student Info:[%d,%s]; State: Fail; Reason: The student already existed.", number, name));
		}
		return result;
	}

	@Override
	public boolean RegisterStudentForCourse(Student student, Course course) {
		// TODO Auto-generated method stub
		boolean result = true;
		/*
		int flag1 = 0;
		int flag2 = 0;
		for (int i=0; i<courses.size(); i++) {
			if (courses.get(i).Code()==course.Code()) {
				flag1 = flag1 + 1;
			}
		}
		for (int i=0; i<students.size(); i++) {
			if (students.get(i).StudentNumber()==student.StudentNumber()) {
				flag2 = flag2 + 1;
			}
		}
		*/
		if (CheckCourse(course.Code()) && CheckStudent(student.StudentNumber()) && student.IsSelected(course)) {
			result = student.RegisterCourse(course) && course.AddStudent(student);
			logger.info(String.format("Operation: student %d register course %d; State: Success", student.StudentNumber(), course.Code()));
		} else {
			result = false;
			logger.info(String.format("Operation: student %d register course %d; State: Fail; Reason: The student or course doesn't exist or the student hasn't selected the course.", student.StudentNumber(), course.Code()));
		}
		return result;
	}
	
	@Override
	public boolean DeRegisterStudentFromCourse(Student student, Course course) {
		// TODO Auto-generated method stub
		boolean result = true;
		if (CheckCourse(course.Code()) && CheckStudent(student.StudentNumber()) && student.IsRegistered(course)) {
			result = student.DeRegisterCourse(course) && course.RemoveStudent(student);
			logger.info(String.format("Operation: student %d deregister course %d; State: Success", student.StudentNumber(), course.Code()));
		} else {
			result = false;
			logger.info(String.format("Operation: student %d deregister course %d; State: Fail; Reason: The student or course doesn't exist or the student hasn't registered the course.", student.StudentNumber(), course.Code()));
		}
		return result;
	}

	@Override
	public boolean CancelCourse(Course course) {
		// TODO Auto-generated method stub
		boolean result = true;
		/*
		int flag = 0;
		for (int i=0; i<courses.size(); i++) {
			if (courses.get(i).Code()==course.Code()) {
				flag = flag + 1;
			}
		}
		*/
		if (CheckCourse(course.Code())) {
			List<Student> s = new ArrayList<Student>();
			for (int i=0; i<s.size(); i++){
				course.RemoveStudent(s.get(i));
				s.get(i).DeRegisterCourse(course);
			}
			result = true;
			logger.info(String.format("Operation: cancel course %d; State: Success", course.Code()));
		} else {
			result = false;
			logger.info(String.format("Operation: cancel course %d; State: Fail; Reason: The course doesn't exist.", course.Code()));
		}
		return result;
	}

	@Override
	public boolean DestroyCourse(Course course) {
		// TODO Auto-generated method stub
		boolean result = true;
		/*
		int flag = 0;
		for (int i=0; i<courses.size(); i++) {
			if (courses.get(i).Code()==course.Code()) {
				flag = flag + 1;
			}
		}
		*/
		if (CheckCourse(course.Code())) {
			List<Student> s = new ArrayList<Student>();
			for (int i=0; i<s.size(); i++){
				course.RemoveStudent(s.get(i));
				s.get(i).DeRegisterCourse(course);
			}
			result = courses.remove(course);
			logger.info(String.format("Operation: delete course %d; State: Success", course.Code()));
		} else {
			result = false;
			logger.info(String.format("Operation: delete course %d; State: Fail; Reason: The course doesn't exist.", course.Code()));
		}
		return result;
	}

	@Override
	public boolean DestroyStudent(Student student) {
		// TODO Auto-generated method stub
		boolean result = true;
		/*
		int flag = 0;
		for (int i=0; i<students.size(); i++) {
			if (students.get(i).StudentNumber()==student.StudentNumber()) {
				flag = flag + 1;
			}
		}
		*/
		if (CheckStudent(student.StudentNumber())) {
			List<Course> c = new ArrayList<Course>();
			for (int i=0; i<c.size(); i++){
				student.DeRegisterCourse(c.get(i));
				c.get(i).RemoveStudent(student);
			}
			result = students.remove(student);
			logger.info(String.format("Operation: delete student %d; State: Success", student.StudentNumber()));
		} else {
			result = false;
			logger.info(String.format("Operation: delete student %d; State: Fail; Reason: The student doesn't exist.", student.StudentNumber()));
		}
		return result;
	}

}
