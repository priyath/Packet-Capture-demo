package gui;

import capture.PacketCapture;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by User on 7/9/2016.
 */
public class HomeFrame extends JFrame {

    PacketCapture packetCapture;
    private volatile static boolean capturing;

    public static void setCapturing(boolean b){
        capturing = b;
    }

    public static boolean isCapturing(){
        return capturing;
    }

    public HomeFrame(PacketCapture packetCapture){

        this.packetCapture = packetCapture;
        initComponents();

    }

    private void initComponents() {

        jPanel = new JPanel();
        jScrollPane = new JScrollPane();
        jTable = new JTable();
        tableModel = new DefaultTableModel(columnNames,0);
        start = new JButton();
        stop = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable.setModel(tableModel);
        jScrollPane.setViewportView(jTable);

        start.setText("START");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCapture();
            }
        });

        stop.setText("STOP");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopCapture();
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(114, 114, 114)
                                .addComponent(start)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(stop)
                                .addContainerGap(130, Short.MAX_VALUE))
                        .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(start)
                                        .addComponent(stop))
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );


        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    //listener methods
    private void stopCapture() {
        if (isCapturing())
            setCapturing(false);
    }

    private void startCapture() {

        //new thread to prevent GUI freeze
        new Thread(){
            public void run(){

                if (!capturing) {
                    setCapturing(true);
                    packetCapture.startCapture(tableModel);
                }
            }
        }.start();
    }

    JPanel jPanel;
    JScrollPane jScrollPane;
    JTable jTable;
    JButton start;
    JButton stop;
    DefaultTableModel tableModel;
    String[] columnNames = {"Src IP", "Dst IP", "Src Port", "Dst Port"};
}
