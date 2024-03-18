import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp {
	private static boolean isAuthenticated = false;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				showLoginDialog();
			}
		});
	}

	private static void showLoginDialog() {
		JFrame loginFrame = new JFrame("Аутентификация");
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setSize(300, 150);
		loginFrame.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));

		JLabel loginLabel = new JLabel("Логин:");
		JTextField loginField = new JTextField();
		panel.add(loginLabel);
		panel.add(loginField);

		JLabel passwordLabel = new JLabel("Пароль:");
		JPasswordField passwordField = new JPasswordField();
		panel.add(passwordLabel);
		panel.add(passwordField);

		JButton loginButton = new JButton("Войти");
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = loginField.getText();
				String password = new String(passwordField.getPassword());

				if (checkCredentials(username, password)) {
					isAuthenticated = true;
					loginFrame.dispose();
					showMainApp();
				} else {
					JOptionPane.showMessageDialog(loginFrame, "Неправильный логин или пароль", "Ошибка", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.add(loginButton);

		loginFrame.getContentPane().add(panel);
		loginFrame.setVisible(true);
	}

	private static boolean checkCredentials(String username, String password) {
		return (username.equals("Prepod") && password.equals("admin")) ||
				(username.equals("Student") && password.equals("1234"));
	}

	private static void showMainApp() {
		if (isAuthenticated) {
			JFrame frame = new JFrame("Успеваемость студентов");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(400, 200);

			JPanel panel = new JPanel();

			JButton inputGradesButton = new JButton("Форма для ввода данных об оценках студентов");
			inputGradesButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					InputGradesForm inputGradesForm = new InputGradesForm();
					inputGradesForm.setVisible(true);
				}
			});
			panel.add(inputGradesButton);

			JButton viewStudentInfoButton = new JButton("Форма для просмотра информации о студентах и их успеваемости");
			viewStudentInfoButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					ViewStudentInfoForm viewStudentInfoForm = new ViewStudentInfoForm();
					viewStudentInfoForm.setVisible(true);
				}
			});
			panel.add(viewStudentInfoButton);

			JButton subjectReportButton = new JButton("Отчет по успеваемости студентов по каждому предмету");
			subjectReportButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					SubjectReportForm subjectReportForm = new SubjectReportForm();
					subjectReportForm.setVisible(true);
				}
			});
			panel.add(subjectReportButton);

			JButton generalReportButton = new JButton("Общий отчет по успеваемости студентов в целом");
			generalReportButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					GeneralReportForm generalReportForm = new GeneralReportForm();
					generalReportForm.setVisible(true);
				}
			});
			panel.add(generalReportButton);

			JButton allDataButton = new JButton("Просмотр всех данных");
			allDataButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					AllDataForm allDataForm = new AllDataForm();
					allDataForm.setVisible(true);
				}
			});
			panel.add(allDataButton);

			frame.getContentPane().add(panel);
			frame.setVisible(true);
		}
	}
}
