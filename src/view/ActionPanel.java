package view;

import helper.HelperLog;
import model.ModelMouse;
import model.ModelPosition;
import object.RMG_BaseModel;
import object.RMG_KeyEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 29/03/14
 * Time: 11:44
 */
public class ActionPanel extends JFrame implements NativeKeyListener, NativeMouseInputListener
{
    // Panel i wanna test
    private JPanel rootPanel;
    private JPanel coordsPanel;
    private JPanel eventPanel;
    private JPanel timerPanel;
    private JPanel actionPanel;
    private JPanel topPanel;
    private JPanel midPanel;
    private JPanel botPanel;

    // TextField
    private JTextField tfPosX;
    private JTextField tfPosY;
    private JTextField tfCoolDown;
    private JTextField tfSleepTime;

    // ComboBox
    private JComboBox<RMG_BaseModel> cbDevice;
    private JComboBox<RMG_BaseModel> cbInput;

    // Button
    private JButton btnAdd;
    private JButton btnRemove;
    private JButton btnSave;
    private JButton btnCancel;

    // List
    private JList listAction;

    // Listeners
    private ActionListener        btnListener;
    private ActionListener        cbDeviceListener;
    private ListSelectionListener listActionListener;

    /**
     *
     */
    public ActionPanel()
    {
        // Définition du panel pour l'affichage
        setContentPane(rootPanel);

        // Inistialisation des listners
        initListener();
        initComponents();

        // Uniformisation de la présentation et lancement de l'affichage
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Rempli le comboBox passé en paramètre avec le vecteur de basemodel fourni
     * @param cbToFill
     * @param vDeviceList
     * @return
     */
    private JComboBox<RMG_BaseModel> fillComboBox(JComboBox<RMG_BaseModel> cbToFill, Vector<RMG_BaseModel>vDeviceList)
    {
        cbToFill.removeAllItems();
        for(RMG_BaseModel bmDevice : vDeviceList){
            cbToFill.addItem(bmDevice);
        }

        cbToFill.setRenderer(RMG_BaseModel.getListCellRenderer());

        return cbToFill;
    }

    /**
     * Initialise les boutons
     */
    private void initComponents()
    {
        // Ajout des listeners aux boutons
        btnAdd.addActionListener(btnListener);
        btnRemove.addActionListener(btnListener);
        btnSave.addActionListener(btnListener);
        btnCancel.addActionListener(btnListener);

        // Action list
        listAction.addListSelectionListener(listActionListener);

        // ComboBoX
        fillComboBox(cbDevice, RMG_KeyEvent.getDeviceList());
        cbDevice.addActionListener(cbDeviceListener);

        fillComboBox(cbInput, RMG_KeyEvent.getKeyList(getCBBSelectedItemId(cbDevice)));
    }

    private int getCBBSelectedItemId(JComboBox<RMG_BaseModel> cbb){
        RMG_BaseModel baseModel = (RMG_BaseModel) cbb.getSelectedItem();

        return Integer.parseInt(baseModel.get("id"));
    }

    /**
     * Active les listeners
     */
    private void initListener()
    {

        // List listener
        listActionListener = new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                // TODO récupérer l'instruction actuel
                HelperLog.getLog().logInfo("Value changed");
            }
        };

        // Bouton listener
        btnListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JButton btnPressed = (JButton) e.getSource();
                if(btnPressed == btnSave){
                    // TODO enregistrement des actions
                    HelperLog.getLog().logInfo("Save");
                }else if(btnPressed == btnCancel){
                    dispose();
                    System.exit(0); // TODO supprimer après avoir fini le dev
                }else if(btnPressed == btnAdd){
                    // TODO appeler fonction add action
                    HelperLog.getLog().logInfo("Add");
                }else if(btnPressed == btnRemove){
                    // TODO appeler fonction remove action
                    HelperLog.getLog().logInfo("Remove");
                }
            }
        };

        // ComboBoxListener
        cbDeviceListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                fillComboBox(cbInput, RMG_KeyEvent.getKeyList(getCBBSelectedItemId(cbDevice)));
            }
        };
    }

    /**
     * @param nativeKeyEvent
     */
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent)
    {
        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
        switch(nativeKeyEvent.getKeyCode()){
            case RMG_KeyEvent.EVENT_F6:
                ModelPosition oCurrentPos = ModelMouse.getMousePosition();
                HelperLog.getLog().logInfo("Récupération de la position => " + oCurrentPos.getPosX() + ":" + oCurrentPos.getPoxY());
                break;
            default:
                break;
        }
    }

    /**
     * @param nativeKeyEvent
     */
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent)
    {
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent)
    {
        // Do nothing with KeyTyped
    }

    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent)
    {
        // Metohd generated automatically
    }

    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent)
    {
        // Metohd generated automatically
    }

    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent)
    {
        // Metohd generated automatically
    }

    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent)
    {
        // Metohd generated automatically
    }

    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent)
    {
        // Metohd generated automatically
    }
}
