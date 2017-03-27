package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import Interface.Directory.FileTreeNode;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;

public class loadFile extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private String file;

	/**
	 * Create the frame.
	 */
	public loadFile() {
		setResizable(false);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(336, 243, 93, 29);
		contentPane.add(btnCancel);
		
		JLabel lblLoad = new JLabel("Load your File:");
		lblLoad.setBounds(6, 21, 438, 16);
		contentPane.add(lblLoad);
		
		Directory dir = new Directory();
		JTree dirTree = dir.getDirectoryTree();
		JScrollPane scrollPane = new JScrollPane(dirTree);
		scrollPane.setBounds(16, 49, 409, 148);
		contentPane.add(scrollPane);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileTreeNode node = (FileTreeNode) dirTree.getLastSelectedPathComponent();
				file = node.getFilePath();
				//TODO
				loading frame = new loading();
				frame.setVisible(true);
				dispose();
			}
		});
		btnOk.setBounds(242, 243, 93, 29);
		contentPane.add(btnOk);
		
		
	}
}

