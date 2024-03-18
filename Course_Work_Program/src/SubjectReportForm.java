import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectReportForm extends JFrame {
	private static final String DATABASE_URL = "jdbc:sqlite:Cursach.db";
	private Connection connection;
	private JTextArea reportTextArea;

	public SubjectReportForm() {
		setTitle("Отчет по успеваемости студентов по каждому предмету");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel(new BorderLayout());

		reportTextArea = new JTextArea();
		reportTextArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(reportTextArea);

		panel.add(scrollPane, BorderLayout.CENTER);

		getContentPane().add(panel);

		connectToDatabase();
		generateSubjectReport();
	}

	private void connectToDatabase() {
		try {
			connection = DriverManager.getConnection(DATABASE_URL);
			System.out.println("Подключение к базе данных успешно");
		} catch (SQLException e) {
			System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
		}
	}

	private void generateSubjectReport() {
		try {
			String query = "SELECT Students.FullName, Subjects.SubjectName, Grades.Grade " +
					"FROM Students " +
					"JOIN Grades ON Students.StudentID = Grades.StudentID " +
					"JOIN Subjects ON Grades.SubjectID = Subjects.SubjectID";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			StringBuilder reportBuilder = new StringBuilder();
			while (resultSet.next()) {
				String fullName = resultSet.getString("FullName");
				String subjectName = resultSet.getString("SubjectName");
				int grade = resultSet.getInt("Grade");

				reportBuilder.append("Студент: ").append(fullName).append("\n");
				reportBuilder.append("Предмет: ").append(subjectName).append("\n");
				reportBuilder.append("Оценка: ").append(grade).append("\n\n");
			}

			double passPercentage = calculatePassPercentage();
			reportBuilder.append("Процент успешно сданных экзаменов: ").append(passPercentage).append("%").append("\n");

			reportTextArea.setText(reportBuilder.toString());
		} catch (SQLException e) {
			System.out.println("Ошибка выполнения SQL-запроса: " + e.getMessage());
		}
	}

	private double calculatePassPercentage() {
		try {
			String query = "SELECT COUNT(Grade) AS TotalGrades, " +
					"COUNT(CASE WHEN Grade >= 3 THEN 1 END) AS PassedGrades " +
					"FROM Grades";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				int totalGrades = resultSet.getInt("TotalGrades");
				int passedGrades = resultSet.getInt("PassedGrades");

				if (totalGrades > 0) {
					return (double) (passedGrades * 100) / totalGrades;
				}
			}
		} catch (SQLException e) {
			System.out.println("Ошибка выполнения SQL-запроса: " + e.getMessage());
		}
		// Если нет данных оценок, возвращаем 100%
		return 100.0;
	}

}

