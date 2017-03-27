package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Interface.Directory.FileTreeNode;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class newFile extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField fileName;
	private String filePath;

	/**
	 * Create the frame.
	 */
	public newFile() {
		setResizable(false);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1.setBounds(336, 243, 93, 29);
		contentPane.add(btnNewButton_1);
		
		fileName = new JTextField();
		fileName.setBounds(74, 209, 355, 26);
		contentPane.add(fileName);
		fileName.setColumns(10);
		
		JLabel lblWhereWouldYou = new JLabel("Where would you like to put your file?");
		lblWhereWouldYou.setBounds(6, 21, 438, 16);
		contentPane.add(lblWhereWouldYou);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(16, 214, 61, 16);
		contentPane.add(lblName);
		
		Directory dir = new Directory();
		JTree dirTree = dir.getDirectoryTree();
		JScrollPane scrollPane = new JScrollPane(dir.getDirectoryTree());
		scrollPane.setBounds(16, 49, 409, 148);
		contentPane.add(scrollPane);
		
		JButton btnNewButton = new JButton("OK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileTreeNode node = (FileTreeNode) dirTree.getLastSelectedPathComponent();
				filePath = node.getFilePath();
				//TODO
				MkNewFile frame = new MkNewFile();
				frame.setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(242, 243, 93, 29);
		contentPane.add(btnNewButton);
	}
}
