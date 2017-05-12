package com.puyixiaowo.quickrun.dialog;

import com.puyixiaowo.quickrun.constants.Bounds;
import com.puyixiaowo.quickrun.entity.ShortCut;
import com.puyixiaowo.quickrun.handler.menu.right.EditSaveShortCutHandler;
import com.puyixiaowo.quickrun.handler.menu.right.ExtractIconCutHandler;
import com.puyixiaowo.quickrun.utils.ColorUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * @author huangfeihong
 * @date 2017-03-17
 */
public class EditShortCutDialog extends JDialog {
    private static final int width = 420;
    private static final int height = 290;
    public static EditShortCutDialog self;
    private static JTextField textFieldName;
    private static JTextField textFieldLink;
    private static JTextField textFieldCmdArgs;
    private static JTextField textFieldTextIcon;
    private static JButton buttonSave;
    public static ShortCut shortCut;
    private static JPanel panel;


    public EditShortCutDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        self = this;
    }

    /**
     * 覆盖父类的方法。实现自己的添加了ESCAPE键监听
     */
    @Override
    protected JRootPane createRootPane(){
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        JRootPane rootPane = new JRootPane();
        rootPane.registerKeyboardAction((ActionEvent e) -> buttonSave.doClick(),
                stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        KeyStroke strokeEsc = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        rootPane.registerKeyboardAction((ActionEvent e) -> setVisible(false),
                strokeEsc, JComponent.WHEN_IN_FOCUSED_WINDOW);

        return rootPane;
    }

    private void initUI(ShortCut shortCut) {
        setSize(width, height);
        setResizable(false);
        setVisible(false);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(null);

        JLabel lebelName = new JLabel("显示名：");
        lebelName.setBounds(Bounds.edit_shorcut_label_name);
        panel.add(lebelName);

        textFieldName = new JTextField(shortCut.getName());
        textFieldName.setBounds(Bounds.edit_shorcut_filed_name);
        panel.add(textFieldName);

        JLabel lebelLink = new JLabel("目标：");
        lebelLink.setBounds(Bounds.edit_shorcut_label_link);
        panel.add(lebelLink);

        textFieldLink = new JTextField(shortCut.getLink());
        textFieldLink.setBounds(Bounds.edit_shorcut_filed_link);
        panel.add(textFieldLink);

        JLabel lebelCmdArgs = new JLabel("参数：");
        lebelCmdArgs.setBounds(Bounds.edit_shorcut_label_cmd_args);
        panel.add(lebelCmdArgs);

        textFieldCmdArgs = new JTextField(shortCut.getCmdArgs());
        textFieldCmdArgs.setBounds(Bounds.edit_shorcut_filed_cmd_args);
        panel.add(textFieldCmdArgs);

        JLabel lebelTextIcon = new JLabel("图标路径：");
        lebelTextIcon.setBounds(Bounds.edit_shorcut_label_text_icon);
        panel.add(lebelTextIcon);

        textFieldTextIcon = new JTextField(shortCut.getTextIcon());
        textFieldTextIcon.setBounds(Bounds.edit_shorcut_filed_text_icon);
        panel.add(textFieldTextIcon);

        JButton buttonGetIcon = new JButton("+");
        buttonGetIcon.addActionListener(new ExtractIconCutHandler());
        buttonGetIcon.setBounds(Bounds.edit_shorcut_button_get_icon);
        buttonGetIcon.setForeground(ColorUtil.string2Color("#00bb17"));
        buttonGetIcon.setFont(new Font("宋体", 1, 18));
        buttonGetIcon.setToolTipText("选择图片或从其他程序中提取图标");
        panel.add(buttonGetIcon);

        buttonSave = new JButton("确定");
        buttonSave.addActionListener(new EditSaveShortCutHandler());
        buttonSave.setBounds(Bounds.edit_shorcut_button_save);
        panel.add(buttonSave);

        add(panel);
    }

    public static void showDialog(ShortCut shortCut) {

        self.shortCut = shortCut;
        self.initUI(shortCut);
        self.setVisible(true);
    }

    /**
     * 填充数据
     *
     * @param shortCut
     */
    public static void fillData(ShortCut shortCut) {
        Component component = panel.getComponentAt(Bounds.edit_shorcut_filed_text_icon.getLocation());
        if (component != null && component instanceof JTextField) {
            ((JTextField) component).setText(shortCut.getTextIcon());
            component.repaint();
        }
    }

    public static void hideDialog() {
        self.setVisible(false);
    }


///////////////////////////////

    public static JTextField getTextFieldName() {
        return textFieldName;
    }


    public static JTextField getTextFieldLink() {
        return textFieldLink;
    }


    public static JTextField getTextFieldTextIcon() {
        return textFieldTextIcon;
    }

    public static JTextField getTextFieldCmdArgs() {
        return textFieldCmdArgs;
    }
}
