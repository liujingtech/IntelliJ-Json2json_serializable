// 
// Decompiled by Procyon v0.5.36
// 

package org.liujing.plugin.json_serializable_format.view.writablepannel;

import java.awt.AWTEvent;
import java.awt.Window;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import com.intellij.ui.components.JBScrollPane;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JFrame;

public class WritablePannel extends JFrame
{
    private JTextArea editTextView;
    private OnClickListener onClickListener;
    private JPanel jPanel;
    private WritablePannel jFrame;
    
    @Override
    public String getTitle() {
        return "format json to dart model";
    }
    
    public WritablePannel builder(final OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        (this.jFrame = new WritablePannel()).setSize(700, 400);
        this.jFrame.setDefaultCloseOperation(2);
        this.jFrame.add(this.initView());
        this.jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(final WindowEvent e) {
                WritablePannel.this.editTextView.requestFocus();
            }
        });
        return this.jFrame;
    }
    
    private JPanel initView() {
        (this.jPanel = new JPanel()).setLayout(null);
        this.jPanel.setBounds(0, 0, 700, 400);
        final JLabel jLabel = new JLabel("input json and (click ok button or Control + Enter).");
        jLabel.setBounds(10, 0, 500, 40);
        (this.editTextView = new JTextArea()).setBounds(0, 200, 690, 390);
        this.editTextView.requestFocusInWindow();
        final JBScrollPane jbScrollPane = new JBScrollPane((Component)this.editTextView);
        jbScrollPane.setVerticalScrollBarPolicy(20);
        jbScrollPane.setHorizontalScrollBarPolicy(30);
        jbScrollPane.setBounds(0, 50, 700, 400);
        final JButton jButton = new JButton("ok");
        jButton.setBounds(600, 10, 80, 30);
        jButton.addActionListener(e -> this.onOk());
        this.jPanel.add(jLabel);
        this.jPanel.add((Component)jbScrollPane);
        this.jPanel.add(jButton);
        this.jPanel.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                WritablePannel.this.onCancel();
            }
        }, KeyStroke.getKeyStroke(27, 0), 1);
        this.jPanel.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                WritablePannel.this.onOk();
            }
        }, KeyStroke.getKeyStroke(10, 128), 1);
        return this.jPanel;
    }
    
    private void onOk() {
        this.onClickListener.onViewClick(this.editTextView.getText());
    }
    
    private void onCancel() {
        this.dispose();
        this.jFrame.dispatchEvent(new WindowEvent(this.jFrame, 201));
    }
}
