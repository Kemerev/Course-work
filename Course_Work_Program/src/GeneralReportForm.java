import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class GeneralReportForm extends JFrame {
	private static final String DATABASE_URL = "jdbc:sqlite:Cursach.db";
	private Connection connection;
	private JTextArea reportTextArea;

	public GeneralReportForm() {
		setTitle("Общий отчет по успеваемости студентов в целом");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel(new BorderLayout());

		reportTextArea = new JTextArea();
		reportTextArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(reportTextArea);

		panel.add(scrollPane, BorderLayout.CENTER);

		getContentPane().add(panel);

		connectToDatabase();
		generateGeneralReport();
	}

	private void connectToDatabase() {
		try {
			connection = DriverManager.getConnection(DATABASE_URL);
			System.out.println("Подключение к базе данных успешно");
		} catch (SQLException e) {
			System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
		}
	}

	private void generateGeneralReport() {
		try {
			String query = "SELECT AVG(Grades.Grade) AS AverageGrade, " +
					"COUNT(Grades.Grade) AS TotalGrades, " +
					"COUNT(CASE WHEN Grades.Grade >= 3 THEN 1 END) AS PassedGrades " +
					"FROM Grades";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			StringBuilder reportBuilder = new StringBuilder();
			if (resultSet.next()) {
				double averageGrade = resultSet.getDouble("AverageGrade");
				int totalGrades = resultSet.getInt("TotalGrades");
				int passedGrades = resultSet.getInt("PassedGrades");

				reportBuilder.append("Средний балл: ").append(averageGrade).append("\n");
				reportBuilder.append("Общее количество оценок: ").append(totalGrades).append("\n");
				reportBuilder.append("Процент успешно сданных экзаменов: ")
						.append((double) (passedGrades * 100) / totalGrades).append("%").append("\n");
			}

			reportTextArea.setText(reportBuilder.toString());
		} catch (SQLException e) {
			System.out.println("Ошибка выполнения SQL-запроса: " + e.getMessage());
		}
	}
}
