package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import entities.Orderer;

public class WellcomeWindow {

	protected Shell shlOrderingFood;
	private Text textPassword;
	private Text textUsername;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			WellcomeWindow window = new WellcomeWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlOrderingFood.open();
		shlOrderingFood.layout();
		while (!shlOrderingFood.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlOrderingFood = new Shell();
		shlOrderingFood.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		shlOrderingFood.setSize(600, 550);
		shlOrderingFood.setText("Ordering Food");
		
		textPassword = new Text(shlOrderingFood, SWT.BORDER | SWT.PASSWORD);
		textPassword.setBounds(186, 423, 181, 26);
		
		textUsername = new Text(shlOrderingFood, SWT.BORDER);
		textUsername.setBounds(186, 365, 181, 26);
		
		formToolkit.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		
		Label lblUsername = new Label(shlOrderingFood, SWT.NONE);
		lblUsername.setFont(SWTResourceManager.getFont("Sitka Heading", 10, SWT.NORMAL));
		lblUsername.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		lblUsername.setBounds(186, 343, 70, 20);
		formToolkit.adapt(lblUsername, true, true);
		lblUsername.setText("Username");
		
		Label lblPassword = new Label(shlOrderingFood, SWT.NONE);
		lblPassword.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		lblPassword.setFont(SWTResourceManager.getFont("Sitka Heading", 10, SWT.NORMAL));
		lblPassword.setText("Password");
		lblPassword.setBounds(186, 397, 70, 20);
		formToolkit.adapt(lblPassword, true, true);
		
	
		/* login */
		
		Button btnLogIn = new Button(shlOrderingFood, SWT.NONE);
		btnLogIn.setFont(SWTResourceManager.getFont("Sitka Heading", 10, SWT.NORMAL));
		btnLogIn.setBounds(226, 463, 90, 30);
		formToolkit.adapt(btnLogIn, true, true);
		btnLogIn.setText("Log In");
		
		
		btnLogIn.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				String username = textUsername.getText();
				String password = textPassword.getText();
				String message = "";
				
				for(Orderer o: DB.DB.orderers) {
					if(o.getUsername().equals(username)) {
						if(o.getPassword().equals(password)) {
							message = "User exists in database";
						}
						else {
							message = "Wrong password!";
						}
					}
				}
				
				if(message.equals(""))
					message = "No user with that username";
				
				if(message.equals("User exists in database")) {
					try {
						OrdererWellcome window = new OrdererWellcome();
						window.open();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else {
					MessageBox dialog = new MessageBox(shlOrderingFood, SWT.ICON_ERROR | SWT.OK);
					dialog.setMessage(message);
					dialog.open();
				}
				
			}
			
		});
		
		Label label = new Label(shlOrderingFood, SWT.NONE);
		label.setImage(SWTResourceManager.getImage("C:\\Users\\stan\\Pictures\\healthy_food_delivery_amsterdam-600x450.jpg"));
		label.setBounds(82, 29, 420, 308);
		formToolkit.adapt(label, true, true);
		
		ToolBar toolBar = new ToolBar(shlOrderingFood, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(0, 0, 582, 28);
		formToolkit.adapt(toolBar);
		formToolkit.paintBordersFor(toolBar);
		
		ToolItem tltmRegister = new ToolItem(toolBar, SWT.NONE);
		tltmRegister.setText("Register");
		
		tltmRegister.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				try {
					Register window = new Register();
					shlOrderingFood.close();
					window.open();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		});
		
		ToolItem tltmRestaurants = new ToolItem(toolBar, SWT.NONE);
		tltmRestaurants.setText("Restaurants");
		
	}
}
