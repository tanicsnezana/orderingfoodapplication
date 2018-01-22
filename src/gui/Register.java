package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import entities.Orderer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

public class Register {

	protected Shell shell;
	private Text txtFirstName;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text txtLastName;
	private Text txtPhoneNumber;
	private Text txtAddress;
	private Text txtUsername;
	private Text txtPassword;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Register window = new Register();
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
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		shell.setSize(600, 550);
		shell.setText("Register");
		
		formToolkit.setBackground(SWTResourceManager.getColor(SWT.COLOR_INFO_BACKGROUND));
		
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.RIGHT);
		toolBar.setBounds(0, 0, 582, 28);
		
		ToolItem tltmBack = new ToolItem(toolBar, SWT.NONE);
		tltmBack.setText("Back");
		
		tltmBack.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				try {
					WellcomeWindow window = new WellcomeWindow();
					shell.close();
					window.open();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		txtFirstName = new Text(shell, SWT.BORDER);
		txtFirstName.setBounds(187, 90, 206, 28);
		
		txtLastName = new Text(shell, SWT.BORDER);
		txtLastName.setBounds(187, 148, 206, 28);
		
		txtPhoneNumber = new Text(shell, SWT.BORDER);
		txtPhoneNumber.setBounds(187, 208, 206, 28);
		
		txtAddress = new Text(shell, SWT.BORDER);
		txtAddress.setBounds(187, 268, 206, 28);
		
		txtUsername = new Text(shell, SWT.BORDER);
		txtUsername.setBounds(187, 328, 206, 28);
		
		txtPassword = new Text(shell, SWT.BORDER);
		txtPassword.setBounds(187, 386, 206, 28);
		
		Label lblFirstName = formToolkit.createLabel(shell, "First Name", SWT.NONE);
		lblFirstName.setFont(SWTResourceManager.getFont("Sitka Heading", 10, SWT.NORMAL));
		lblFirstName.setBounds(187, 62, 110, 22);
				
		Label lblLastName = formToolkit.createLabel(shell, "Last Name", SWT.NONE);
		lblLastName.setFont(SWTResourceManager.getFont("Sitka Heading", 10, SWT.NORMAL));
		lblLastName.setBounds(187, 124, 89, 20);
		
		Label lblPhoneNumber = formToolkit.createLabel(shell, "Phone Number", SWT.NONE);
		lblPhoneNumber.setFont(SWTResourceManager.getFont("Sitka Heading", 10, SWT.NORMAL));
		lblPhoneNumber.setBounds(187, 182, 110, 20);
		
		Label lblAddress = formToolkit.createLabel(shell, "Address", SWT.NONE);
		lblAddress.setFont(SWTResourceManager.getFont("Sitka Heading", 10, SWT.NORMAL));
		lblAddress.setBounds(187, 242, 70, 20);
		
		Label lblUsername = formToolkit.createLabel(shell, "Username", SWT.NONE);
		lblUsername.setFont(SWTResourceManager.getFont("Sitka Heading", 10, SWT.NORMAL));
		lblUsername.setBounds(187, 302, 89, 20);
		
		Label lblPassword = formToolkit.createLabel(shell, "Password", SWT.NONE);
		lblPassword.setFont(SWTResourceManager.getFont("Sitka Heading", 10, SWT.NORMAL));
		lblPassword.setBounds(187, 362, 89, 20);
		
		Button btnConfirm = formToolkit.createButton(shell, "Confirm", SWT.NONE);
		btnConfirm.setFont(SWTResourceManager.getFont("Sitka Heading", 10, SWT.NORMAL));
		btnConfirm.setBounds(246, 420, 90, 30);
		
		btnConfirm.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				String firstName = txtFirstName.getText();
				String lastName = txtLastName.getText();
				String phoneNumber = txtPhoneNumber.getText();
				String address = txtAddress.getText();
				String username = txtUsername.getText();
				String password = txtPassword.getText();
				String message = "";
				
				for(Orderer orderer: DB.DB.orderers) {
					if(orderer.getUsername().equals(username)) {
						message = "User with that username already exists.";
						break;
					}
				}
				
				if(!message.equals("")) {
					MessageBox dialog = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
					dialog.setMessage(message);
					dialog.open();
				}
				else {
					Orderer orderer = new Orderer();
					orderer.setFirstName(firstName);
					orderer.setLastName(lastName);
					orderer.setPhoneNumber(phoneNumber);
					orderer.setAddress(address);
					orderer.setUsername(username);
					orderer.setPassword(password);
					
					DB.DB.orderers.add(orderer);
					
					message = "Successfully registered!";
					MessageBox dialog = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
					dialog.setMessage(message);
					dialog.open();
				}
			}
			
		});
	}
}
