package cn.butterfly.ui;

import javax.swing.*;

/**
 * UI 界面
 *
 * @author zjw
 * @date 2023-12-06
 */
public class UIDemo {

    /**
     * 用户名
     */
    private JTextField username;
    
    /**
     * 密码
     */
    private JTextField password;
    
    private JPanel mainPanel;

    public JTextField getUsername() {
        return username;
    }

    public JTextField getPassword() {
        return password;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
