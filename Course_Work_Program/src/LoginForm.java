import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
	private JTextField usernameField;
	private JPasswordField passwordField;
	private boolean authenticated = false;

	public LoginForm() {
		setTitle("Авторизация");
		setSize(300, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));

		JLabel usernameLabel = new JLabel("Логин:");
		usernameField = new JTextField();
		panel.add(usernameLabel);
		panel.add(usernameField);

		JLabel passwordLabel = new JLabel("Пароль:");
		passwordField = new JPasswordField();
		panel.add(passwordLabel);
		panel.add(passwordField);

		JButton loginButton = new JButton("Войти");
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = new String(passwordField.getPassword());

				// Проверка на соответствие логина и пароля
				if (username.equals("Prepod") && password.equals("admin")) {
					authenticated = true;
					dispose(); // Закрыть окно авторизации после успешной аутентификации
				} else if (username.equals("Student") && password.equals("1234")) {
					authenticated = true;
					dispose(); // Закрыть окно авторизации после успешной аутентификации
				} else {
					JOptionPane.showMessageDialog(null, "Неправильный логин или пароль. Попробуйте снова.", "Ошибка аутентификации", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel.add(loginButton);

		getContentPane().add(panel);
	}

	public boolean isAuthenticated() {
		return authenticated;
	}
}
