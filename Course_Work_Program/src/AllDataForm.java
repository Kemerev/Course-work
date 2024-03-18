import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AllDataForm extends JFrame {
	private static final String DATABASE_URL = "jdbc:sqlite:Cursach.db";
	private Connection connection;
	private JTextArea dataTextArea;
	private JComboBox<String> tableComboBox;

	public AllDataForm() {
		setTitle("Просмотр всех данных");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel(new BorderLayout());

		JPanel topPanel = new JPanel(new FlowLayout());

		JLabel tableLabel = new JLabel("Выберите таблицу:");
		tableComboBox = new JComboBox<>();
		tableComboBox.addItem("Classrooms");
		tableComboBox.addItem("Groups");
		tableComboBox.addItem("Subjects");
		tableComboBox.addItem("ElectronicResources");
		tableComboBox.addItem("Schedule");
		tableComboBox.addItem("Teachers");
		tableComboBox.addItem("Exams");
		tableComboBox.addItem("Students");
		tableComboBox.addItem("Grades");
		tableComboBox.addItem("StudyMaterials");
		topPanel.add(tableLabel);
		topPanel.add(tableComboBox);

		JButton showDataButton = new JButton("Показать данные");
		showDataButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showData();
			}
		});
		topPanel.add(showDataButton);

		panel.add(topPanel, BorderLayout.NORTH);

		dataTextArea = new JTextArea();
		dataTextArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(dataTextArea);

		panel.add(scrollPane, BorderLayout.CENTER);

		getContentPane().add(panel);

		connectToDatabase();
	}

	private void connectToDatabase() {
		try {
			connection = DriverManager.getConnection(DATABASE_URL);
			System.out.println("Подключение к базе данных успешно");
		} catch (SQLException e) {
			System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
		}
	}

	private void showData() {
		String selectedTable = (String) tableComboBox.getSelectedItem();
		if (selectedTable != null) {
			try {
				String query = "SELECT * FROM " + selectedTable;
				PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery();

				ResultSetMetaData metaData = resultSet.getMetaData();
				int columnCount = metaData.getColumnCount();

				StringBuilder dataBuilder = new StringBuilder();
				while (resultSet.next()) {
					for (int i = 1; i <= columnCount; i++) {
						dataBuilder.append(metaData.getColumnName(i)).append(": ").append(resultSet.getString(i)).append("\n");
					}
					dataBuilder.append("\n");
				}

				dataTextArea.setText(dataBuilder.toString());
			} catch (SQLException e) {
				System.out.println("Ошибка выполнения SQL-запроса: " + e.getMessage());
			}
		}
	}
}
