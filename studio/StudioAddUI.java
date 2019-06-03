package xupt.se.ttms.view.studio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import xupt.se.ttms.model.Studio;
import xupt.se.ttms.service.StudioSrv;
import xupt.se.ttms.view.tmpl.*;

public class StudioAddUI extends PopUITmpl implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JButton btnCancel, btnSave; 	//取消，保存按鈕

	protected boolean rst=false; 				//操作结果
	private JLabel lblName, lblRow, lblColumn, lblIntro;
	protected JTextField txtName, txtRow, txtColumn;
	protected JTextArea txtIntro;
	

	@Override
	protected void initContent(){
		
		this.setTitle("添加演出厅");
		lblName = new JLabel("演出厅名称:");
		lblName.setBounds(60, 30, 80, 30);
		contPan.add(lblName);
		txtName = new JTextField();
		txtName.setBounds(150, 30, 400, 30);
		contPan.add(txtName);

		lblRow = new JLabel("座位  行数:");
		lblRow.setBounds(60, 80, 80, 30);
		contPan.add(lblRow);
		txtRow = new JTextField();
		txtRow.setBounds(150, 80, 120, 30);
		contPan.add(txtRow);

		lblColumn = new JLabel("座位  列数:");
		lblColumn.setBounds(340, 80, 80, 30);
		contPan.add(lblColumn);
		txtColumn = new JTextField();
		txtColumn.setBounds(430, 80, 120, 30);
		contPan.add(txtColumn);
		
		lblIntro = new JLabel("演出厅简介:");
		lblIntro.setBounds(60, 130, 80, 30);
		contPan.add(lblIntro);
		txtIntro = new JTextArea();
		txtIntro.setBounds(150, 130, 400, 100);
		contPan.add(txtIntro);

		btnSave = new JButton("保存");
		btnSave.addActionListener(this);
		btnSave.setBounds(60, 280, 60, 30);
		contPan.add(btnSave);

		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(180, 280, 60, 30);
		contPan.add(btnCancel);
	}
	
	
	public boolean getReturnStatus(){
		   return rst;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			rst=false;
			this.setVisible(false);
		} else if (e.getSource() == btnSave) {
			btnSaveClicked();
		}
	}
	
	protected void btnSaveClicked(){
		if (txtName.getText() != null && txtRow.getText() != null
				&& txtColumn.getText() != null) {
			StudioSrv stuSrv = new StudioSrv();
			Studio stu=new Studio();
			stu.setName(txtName.getText());
			stu.setRowCount(Integer.parseInt(txtRow.getText()));
			stu.setColCount(Integer.parseInt(txtColumn.getText()));
			stu.setIntroduction(txtIntro.getText());

			stuSrv.add(stu);
			this.setVisible(false);
			rst=true;
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
}
