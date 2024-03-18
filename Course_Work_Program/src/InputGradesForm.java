import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;


public class InputGradesForm extends JFrame {
	private static final String DATABASE_URL = "jdbc:sqlite:Cursach.db";
	private Connection connection;

	private JComboBox<String> studentComboBox;
	private JComboBox<String> subjectComboBox;
	private JTextField examDateField;
	private JTextField gradeField;

	public InputGradesForm() {
		setTitle("Форма для ввода данных об оценках студентов");
		setSize(400, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));

		JLabel studentLabel = new JLabel("Студент:");
		studentComboBox = new JComboBox<>();
		panel.add(studentLabel);
		panel.add(studentComboBox);

		JLabel subjectLabel = new JLabel("Предмет:");
		subjectComboBox = new JComboBox<>();
		panel.add(subjectLabel);
		panel.add(subjectComboBox);

		JLabel examDateLabel = new JLabel("Дата экзамена:");
		examDateField = new JTextField();
		panel.add(examDateLabel);
		panel.add(examDateField);

		JLabel gradeLabel = new JLabel("Оценка:");
		gradeField = new JTextField();
		panel.add(gradeLabel);
		panel.add(gradeField);

		JButton saveButton = new JButton("Сохранить");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveGradeData();
				clearFields();
				fillStudentComboBox();
				fillSubjectComboBox();
			}
		});
		panel.add(saveButton);

		getContentPane().add(panel);

		connectToDatabase();
		fillStudentComboBox();
		fillSubjectComboBox();
	}

	private void connectToDatabase() {
		try {
			connection = DriverManager.getConnection(DATABASE_URL);
			System.out.println("Подключение к базе данных успешно");
		} catch (SQLException e) {
			System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
		}
	}

	private void fillStudentComboBox() {
		try {
			String query = "SELECT FullName FROM Students";
			PreparedStatement statement = connection.prepareStatement(query);
			List<String> students = new ArrayList<>();
			students.add(""); // Добавляем пустой элемент для возможности сброса выбора
			studentComboBox.removeAllItems();
			studentComboBox.addItem(""); // Добавляем пустой элемент для возможности сброса выбора
			studentComboBox.setSelectedItem("");
			studentComboBox.addItem(""); // Добавляем пустой элемент для возможности сброса выбора
			studentComboBox.setSelectedItem("");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				students.add(resultSet.getString("FullName"));
			}
			for (String student : students) {
				studentComboBox.addItem(student);
			}
		} catch (SQLException e) {
			System.out.println("Ошибка выполнения SQL-запроса: " + e.getMessage());
		}
	}

	private void fillSubjectComboBox() {
		try {
			String query = "SELECT SubjectName FROM Subjects";
			PreparedStatement statement = connection.prepareStatement(query);
			List<String> subjects = new ArrayList<>();
			subjects.add(""); // Добавляем пустой элемент для возможности сброса выбора
			subjectComboBox.removeAllItems();
			subjectComboBox.addItem(""); // Добавляем пустой элемент для возможности сброса выбора
			subjectComboBox.setSelectedItem("");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				subjects.add(resultSet.getString("SubjectName"));
			}
			for (String subject : subjects) {
				subjectComboBox.addItem(subject);
			}
		} catch (SQLException e) {
			System.out.println("Ошибка выполнения SQL-запроса: " + e.getMessage());
		}
	}

	private void saveGradeData() {
		try {
			String student = (String) studentComboBox.getSelectedItem();
			String subject = (String) subjectComboBox.getSelectedItem();
			String examDate = examDateField.getText();
			int grade = Integer.parseInt(gradeField.getText());

			String query = "INSERT INTO Grades (StudentID, SubjectID, Grade, DateOfGrade) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, getStudentID(student));
			statement.setInt(2, getSubjectID(subject));
			statement.setInt(3, grade);
			statement.setString(4, examDate);
			statement.executeUpdate();
			JOptionPane.showMessageDialog(this, "Данные сохранены успешно");
		} catch (SQLException | NumberFormatException e) {
			System.out.println("Ошибка выполнения SQL-запроса: " + e.getMessage());
			JOptionPane.showMessageDialog(this, "Ошибка сохранения данных", "Ошибка", JOptionPane.ERROR_MESSAGE);
		}
	}

	private int getStudentID(String studentName) {
		try {
			String query = "SELECT StudentID FROM Students WHERE FullName = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, studentName);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("StudentID");
			}
		} catch (SQLException e) {
			System.out.println("Ошибка выполнения SQL-запроса: " + e.getMessage());
		}
		return -1;
	}

	private int getSubjectID(String subjectName) {
		try {
			String query = "SELECT SubjectID FROM Subjects WHERE SubjectName = ?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, subjectName);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("SubjectID");
			}
		} catch (SQLException e) {
			System.out.println("Ошибка выполнения SQL-запроса: " + e.getMessage());
		}
		return -1;
	}

	private void clearFields() {
		examDateField.setText("");
		gradeField.setText("");
	}
}
