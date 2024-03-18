import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewStudentInfoForm extends JFrame {
	private static final String DATABASE_URL = "jdbc:sqlite:Cursach.db";
	private Connection connection;
	private JTextArea studentInfoTextArea;

	public ViewStudentInfoForm() {
		setTitle("Форма для просмотра информации о студентах и их успеваемости");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel(new BorderLayout());

		studentInfoTextArea = new JTextArea();
		studentInfoTextArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(studentInfoTextArea);

		panel.add(scrollPane, BorderLayout.CENTER);

		getContentPane().add(panel);

		connectToDatabase();
		showStudentInfo();
	}

	private void connectToDatabase() {
		try {
			connection = DriverManager.getConnection(DATABASE_URL);
			System.out.println("Подключение к базе данных успешно");
		} catch (SQLException e) {
			System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
		}
	}

	private void showStudentInfo() {
		try {
			String query = "SELECT Students.FullName, Students.ContactInfo, Subjects.SubjectName, Grades.Grade, Exams.Description " +
					"FROM Students " +
					"LEFT JOIN Grades ON Students.StudentID = Grades.StudentID " +
					"LEFT JOIN Subjects ON Grades.SubjectID = Subjects.SubjectID " +
					"LEFT JOIN Exams ON Subjects.SubjectName = Exams.Subject";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			StringBuilder infoBuilder = new StringBuilder();
			while (resultSet.next()) {
				String fullName = resultSet.getString("FullName");
				String contactInfo = resultSet.getString("ContactInfo");
				String subjectName = resultSet.getString("SubjectName");
				int grade = resultSet.getInt("Grade");
				String examDescription = resultSet.getString("Description");

				infoBuilder.append("Студент: ").append(fullName).append("\n");
				infoBuilder.append("Контактная информация: ").append(contactInfo).append("\n");
				infoBuilder.append("Предмет: ").append(subjectName).append("\n");
				infoBuilder.append("Оценка: ").append(grade).append("\n");
				infoBuilder.append("Описание экзамена: ").append(examDescription).append("\n\n");
			}

			studentInfoTextArea.setText(infoBuilder.toString());
		} catch (SQLException e) {
			System.out.println("Ошибка выполнения SQL-запроса: " + e.getMessage());
		}
	}
}
