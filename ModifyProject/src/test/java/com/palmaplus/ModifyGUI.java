package com.palmaplus;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import palmap.ModifyUdidUtil;

import javax.swing.JLabel;

import com.esri.arcgis.system.AoInitialize;
import com.esri.arcgis.system.EngineInitializer;

public class ModifyGUI {

	private JFrame frame;
	private String srcpath;
	private ModifyUdidUtil mum;
	

	/**new--other下选择window application，在mvn项目下新建“窗口”
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EngineInitializer.initializeEngine();
	    initializeArcGISLicenses();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModifyGUI window = new ModifyGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ModifyGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		final JFileChooser jfc = new JFileChooser();
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.BLACK);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(274, 223, 83, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("CovertToPalmaplus");
		btnNewButton_1.setBounds(253, 157, 113, 55);
		frame.getContentPane().add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {//异常处理？
					excuteModify();
					lblNewLabel.setText(mum.ds.count+"转换完成!");
					srcpath = null;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		final JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(94, 222, 92, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("CovertToNav");
		btnNewButton.setBounds(84, 157, 113, 55);
		frame.getContentPane().add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			//可以只写一个actionPerformed，结合逻辑判断
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					mum = new ModifyUdidUtil(srcpath);
					ModifyGUI.this.mum.ds.convertMode = 1;
					mum.modifyMdb();
					lblNewLabel_1.setText(ModifyGUI.this.mum.ds.count+"转换完成!");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnNewButton_2 = new JButton("DirChoose");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//只选择文件夹
				jfc.setCurrentDirectory(new File("D:\\"));
				int state = jfc.showOpenDialog(null);//打开文件对话框
				if (state == JFileChooser.APPROVE_OPTION) {
					srcpath = jfc.getSelectedFile().getAbsolutePath();//获得选择的路径
				}
				
			}
		});
		btnNewButton_2.setForeground(Color.BLACK);
		btnNewButton_2.setFont(new Font("SimSun-ExtB", Font.PLAIN, 12));
		btnNewButton_2.setBounds(161, 53, 120, 55);
		frame.getContentPane().add(btnNewButton_2);
		
		JLabel lblNewLabel_2 = new JLabel("执行转换操作之前，请选择目录");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 10));
		lblNewLabel_2.setBounds(145, 106, 183, 29);
		frame.getContentPane().add(lblNewLabel_2);
	}
	
	private void excuteModify () throws IOException {
		mum = new ModifyUdidUtil(srcpath);
		mum.modifyMdb();
	}
	
	private static void initializeArcGISLicenses() {
	    try {
	      AoInitialize ao = 
	        new AoInitialize();
	      if (ao.isProductCodeAvailable(60) == 
	        10)
	      {
	        ao.initialize(60);
	      }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	
}
