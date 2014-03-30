package view;

import helper.HelperLog;
import model.ModelAction;
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
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private JTable tableAction;

    // Listeners
    private ActionListener        btnListener;
    private ActionListener        cbDeviceListener;
    private ListSelectionListener listActionListener;

    private ArrayList<ModelAction> vAction;

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
    private JComboBox<RMG_BaseModel> fillComboBox(JComboBox<RMG_BaseModel> cbToFill, Vector<RMG_BaseModel> vDeviceList)
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


        tableAction.getSelectionModel().addListSelectionListener(listActionListener);

        // ComboBoX
        fillComboBox(cbDevice, RMG_KeyEvent.getDeviceList());
        cbDevice.addActionListener(cbDeviceListener);

        fillComboBox(cbInput, RMG_KeyEvent.getKeyList(getCBBSelectedItemId(cbDevice)));
    }

    /**
     * @param cbb
     * @return
     */
    private int getCBBSelectedItemId(JComboBox<RMG_BaseModel> cbb)
    {
        RMG_BaseModel baseModel = (RMG_BaseModel) cbb.getSelectedItem();

        return Integer.parseInt(baseModel.get("id"));
    }

    /**
     * Put forms data into action format
     * @return
     */
    private ModelAction getFormActionData()
    {
        // Récupération de la position de l'action
        int iPosX = 0;
        int iPosY = 0;
        try{
            iPosX = Integer.parseInt(tfPosX.getText());
            iPosY = Integer.parseInt(tfPosY.getText());
        }catch(NumberFormatException ignored){
        }
        ModelPosition oPosition = new ModelPosition(iPosX, iPosY);

        // Récupération des informations de l'input
        int iDeviceId = getCBBSelectedItemId(cbDevice);
        int iInputId = getCBBSelectedItemId(cbInput);

        // Construction du nom à partir des informations de l'action
        String sName = "";

        ModelAction oAction = new ModelAction(sName, oPosition, iDeviceId, iInputId);


        HelperLog.getLog().logInfo("Action : " + oAction.getText());

        return oAction;
    }

    /**
     * Active les listeners
     */
    private void initListener()
    {

        // List
        listActionListener = new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                // TODO récupérer l'instruction actuel
                HelperLog.getLog().logInfo("Value changed" + e);
            }
        };

        // Bouton
        btnListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JButton btnPressed = (JButton) e.getSource();
                if(btnPressed == btnAdd){
                    ModelAction oAction = getFormActionData();
                    addAction(oAction);
                }else if(btnPressed == btnRemove){
                    removeAction(tableAction.getSelectedRow());
                }else if(btnPressed == btnSave){
                    // TODO enregistrement des actions
                    HelperLog.getLog().logInfo("Save");
                }else if(btnPressed == btnCancel){
                    dispose();
                    System.exit(0); // TODO supprimer après avoir fini le dev
                }
            }
        };

        // ComboBox
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
                HelperLog.getLog().logInfo("Récupération de la position => " + oCurrentPos.getPosX() + ":" + oCurrentPos.getPosY());
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

    /**
     *
     */
    private void createUIComponents()
    {
        // Initialisation of table action
        String[] columnNames = {
                "Action",
                "Cooldown",
        };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tableAction = new JTable(model);
        loadActionsList();
    }

    /**
     * Charge la liste des actions à partir de la sauvegarde
     */
    private void loadActionsList()
    {
        // Chargement des actions déjà connues
        vAction = new ArrayList<ModelAction>();

        // Valeur par défaut
        if(vAction.size() == 0){
            ModelAction oTmpAction;
            oTmpAction = new ModelAction("Boost", new ModelPosition(1633, 1000), RMG_KeyEvent.DEVICE_MOUSE, RMG_KeyEvent.EVENT_LEFTCLICK);
            vAction.add(oTmpAction);
            oTmpAction = new ModelAction("Glissade", new ModelPosition(1673, 1023), RMG_KeyEvent.DEVICE_MOUSE, RMG_KeyEvent.EVENT_LEFTCLICK);
            vAction.add(oTmpAction);
            oTmpAction = new ModelAction("Target missile", new ModelPosition(0, 0), RMG_KeyEvent.DEVICE_KEYBOARD, RMG_KeyEvent.EVENT_BACKSPACE);
            vAction.add(oTmpAction);
        }

        // Remplissage de la liste par les actions chargées
        for(ModelAction oAction : vAction){
            getTableActionModel().addRow(new Object[]{
                    oAction.getText(),
                    "0",
            });
        }
    }

    /**
     * Ajoute une action au tableau
     * @param oAction
     */
    private void addAction(ModelAction oAction)
    {
        vAction.add(oAction);

        getTableActionModel().addRow(new Object[]{
                oAction.getText(),
                "0",
        });
    }

    /**
     * Supprime l'élément à la position donnée
     * @param iPos
     */
    private void removeAction(int iPos)
    {
        if(iPos > 0){
            vAction.remove(iPos);
            getTableActionModel().removeRow(iPos);
        }
    }

    /**
     * Renvoi le model nécessaire pour traiter la ligne
     * @return
     */
    private DefaultTableModel getTableActionModel()
    {
        return (DefaultTableModel) tableAction.getModel();
    }
}
