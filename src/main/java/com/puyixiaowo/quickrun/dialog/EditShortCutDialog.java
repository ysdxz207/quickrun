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
import java.io.File;

/**
 * @author huangfeihong
 * @date 2017-03-17
 */
public class EditShortCutDialog extends JDialog {
    private static final int width = 420;
    private static final int height = 322;
    public static ShortCut shortCut;
    private static JPanel panel = new JPanel();


    ///////
    private static final JLabel lebelName = new JLabel("显示名：");
    private static final JLabel lebelLink= new JLabel("快捷方式：");
    private static final JLabel lebelTarget = new JLabel("目标：");
    private static final JLabel lebelCmdArgs = new JLabel("参数：");
    private static final JLabel lebelTextIcon = new JLabel("图标路径：");
    private static final JButton buttonGetIcon = new JButton("+");

    private static JTextField textFieldName = new JTextField();
    private static JTextField textFieldLink = new JTextField();
    private static JTextField textFieldTarget = new JTextField();
    private static JTextField textFieldCmdArgs = new JTextField();
    private static JTextField textFieldTextIcon = new JTextField();
    private static JButton buttonSave = new JButton("保存");

    {
        initUI();
    }

    public static EditShortCutDialog getInstance(){
        return EditShortCutDialogEnum.INSTANCE.singleton;
    }

    private enum EditShortCutDialogEnum {
        INSTANCE(MainDialog.getInstance());

        EditShortCutDialogEnum(JFrame parent) {
            singleton = new EditShortCutDialog(parent, "编辑快捷方式", true);
        }

        private EditShortCutDialog singleton;
    }

    public EditShortCutDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
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

    private void initUI() {
        setSize(width, height);
        setResizable(false);
        setVisible(false);
        setLocationRelativeTo(null);

        panel.setLayout(null);


        lebelName.setBounds(Bounds.edit_shorcut_label_name);
        panel.add(lebelName);


        textFieldName.setBounds(Bounds.edit_shorcut_filed_name);
        panel.add(textFieldName);


        lebelLink.setBounds(Bounds.edit_shorcut_label_link);
        panel.add(lebelLink);


        textFieldLink.setBounds(Bounds.edit_shorcut_filed_link);
        panel.add(textFieldLink);


        lebelTarget.setBounds(Bounds.edit_shorcut_label_target);
        panel.add(lebelTarget);


        textFieldTarget.setBounds(Bounds.edit_shorcut_filed_target);
        panel.add(textFieldTarget);


        lebelCmdArgs.setBounds(Bounds.edit_shorcut_label_cmd_args);
        panel.add(lebelCmdArgs);


        textFieldCmdArgs.setBounds(Bounds.edit_shorcut_filed_cmd_args);
        panel.add(textFieldCmdArgs);


        lebelTextIcon.setBounds(Bounds.edit_shorcut_label_text_icon);
        panel.add(lebelTextIcon);


        textFieldTextIcon.setBounds(Bounds.edit_shorcut_filed_text_icon);
        panel.add(textFieldTextIcon);


        buttonGetIcon.addActionListener(new ExtractIconCutHandler());
        buttonGetIcon.setBounds(Bounds.edit_shorcut_button_get_icon);
        buttonGetIcon.setForeground(ColorUtil.string2Color("#00bb17"));
        buttonGetIcon.setFont(new Font("宋体", 1, 18));
        buttonGetIcon.setToolTipText("选择图片或从其他程序中提取图标");
        panel.add(buttonGetIcon);


        buttonSave.addActionListener(new EditSaveShortCutHandler());
        buttonSave.setBounds(Bounds.edit_shorcut_button_save);
        panel.add(buttonSave);

        add(panel);
    }

    public static void showDialog(ShortCut shortCut) {
        fillData(shortCut);
        getInstance().setVisible(true);
    }

    public static void fillData(ShortCut shortCut){
        textFieldLink.setBorder(UIManager.getBorder("TextField.border"));
        textFieldTarget.setBorder(UIManager.getBorder("TextField.border"));


        EditShortCutDialog.shortCut = shortCut;

        textFieldName.setText(shortCut.getName());
        textFieldLink.setText(shortCut.getLink());
        if (!new File(shortCut.getLink()).exists()) {
            textFieldLink.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        }
        textFieldTarget.setText(shortCut.getTarget());
        if (!new File(shortCut.getTarget()).exists()) {
            textFieldTarget.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        }
        textFieldCmdArgs.setText(shortCut.getCmdArgs());
        textFieldTextIcon.setText(shortCut.getTextIcon());
    }

    public static void hideDialog() {
        getInstance().setVisible(false);
    }


///////////////////////////////

    public static JTextField getTextFieldName() {
        return textFieldName;
    }


    public static JTextField getTextFieldLink() {
        return textFieldLink;
    }

    public static JTextField getTextFieldTarget() {
        return textFieldTarget;
    }


    public static JTextField getTextFieldTextIcon() {
        return textFieldTextIcon;
    }

    public static JTextField getTextFieldCmdArgs() {
        return textFieldCmdArgs;
    }
}
