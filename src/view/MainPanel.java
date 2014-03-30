package view;

import controller.ControllerReadTactic;
import controller.ControllerSaveTactic;
import controller.ControllerTactic;
import helper.HelperLog;
import model.ModelMouse;
import model.ModelPosition;
import model.ModelTactic;
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

/**
 * Created with IntelliJ IDEA.
 * User: dev
 * Date: 09/02/14
 * Time: 03:06
 */
public class MainPanel extends JFrame implements NativeKeyListener, NativeMouseInputListener
{
    // Panel
    private JPanel   rootPanel;
    private JPanel   panelTop;
    private JPanel   panelMidle;
    private JPanel   panelBot;
    private JPanel   panelCoords;
    private JPanel   eventPanel;
    private JPanel   actionButtonPanel;
    private JPanel   instructionPanel;
    private JPanel   instructionButtonPanel;
    private JToolBar menuToolbar;

    // Label
    private JLabel coordsLabel;

    // TextField
    private JTextField tfPosX;
    private JTextField tfPosY;
    private JTextField tfTime;

    // Nom
    private String strStart = "Start (F7)";
    private String strStop  = "Stop (F7)";

    // Button
    private JButton btnLoadTactic;
    private JButton btnSaveTactic;
    private JButton btnActionTactic;
    private JButton btnMoveUp;
    private JButton btnMoveDown;
    private JButton btnDelete;
    private JButton btnDeleteAll;
    private JButton btnAdd;
    private JButton btnUpdate;

    // ComboBox
    private JComboBox cbDevice;
    private JComboBox cbKeyEvent;
    private JList     actionsList;

    // TextArea
    private JTextArea taExecutedAction;
    private JButton   btnExit;

    // Listener
    private ActionListener        btnListener;
    private ListSelectionListener listInstructionListener;

    // Gestion de la tactique
    private ModelTactic      oCurrentTactic;
    private ControllerTactic oControllerTactique;

    public MainPanel()
    {
        // Définition du panel pour l'affichage
        setContentPane(rootPanel);

        // Inistialisation des listners
        initListener();
        initButtonsAndHotkeys();

        // Uniformisation de la présentation et lancement de l'affichage
        setLocationRelativeTo(null);
        setSize(1200, 800);
        setVisible(true);
    }


    /**
     * Détermine si il s'agit d'un bouton start
     * @param btnPressed
     * @return
     */
    private boolean isStartAction(JButton btnPressed)
    {
        return btnPressed.getText().equals(strStart);
    }

    /**
     * Détermine si il s'agti d'un bouton stop
     * @param btnPressed
     * @return
     */
    private boolean isStopAction(JButton btnPressed)
    {
        return btnPressed.getText().equals(strStop);
    }


    /**
     *
     */
    private void loadTactic()
    {
        // Construction du lecteur de tactic
        ControllerReadTactic oTacticReader = new ControllerReadTactic("", "test");
        // Lecture de la tactique et mise en mémoire
        oTacticReader.readTactique();

        // Récupération de la tactic lu
        oCurrentTactic = oTacticReader.getTactic();
        if(oCurrentTactic != null){
            HelperLog.getLog().logInfo("Tactic loaded !");
        }else{
            // Traitement d'erreur
            HelperLog.getLog().logErr("Erreur lors du chargement de la tactic");
        }
    }

    /**
     * @param oTactic
     */
    private void saveTactic(ModelTactic oTactic)
    {
        ControllerSaveTactic oTacticSaver = new ControllerSaveTactic("", oTactic.getName() + "_bis");
        if(oTacticSaver.saveTactic(oTactic)){
            // Traitement d'erreur
            HelperLog.getLog().logErr("Erreur lors de l'enregistrement de la tactic");
        }
    }

    /**
     *
     */
    private void stopTactic()
    {
        HelperLog.getLog().logInfo("Stop");
        btnActionTactic.setText(strStart);
        oControllerTactique.tactiqueStop();
    }

    /**
     *
     */
    private void startTactic()
    {
        if(oCurrentTactic != null){
            btnActionTactic.setText(strStop);
            oControllerTactique = new ControllerTactic(oCurrentTactic.getInstructions());
            oControllerTactique.start();
        }else{
            HelperLog.getLog().logInfo("No tactic loaded");
        }
    }

    /**
     * ======================== Listener ========================
     */

    /**
     * Active les listeners
     */
    private void initListener()
    {
        listInstructionListener = new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                // TODO récupérer l'instruction actuel
            }
        };

        btnListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JButton btnPressed = (JButton) e.getSource();
                if(btnPressed == btnActionTactic){
                    if(isStartAction(btnPressed)){
                        startTactic();
                    }else if(isStopAction(btnPressed)){
                        stopTactic();
                    }
                }else if(btnPressed == btnExit){
                    System.exit(0);
                }else if(btnPressed == btnLoadTactic){
                    loadTactic();
                }else if(btnPressed == btnSaveTactic){
                    saveTactic(oCurrentTactic);
                }
            }
        };
    }

    /**
     * Initialise les boutons
     */
    private void initButtonsAndHotkeys()
    {
        // Ajout des listeners aux boutons
        btnActionTactic.addActionListener(btnListener);
        btnLoadTactic.addActionListener(btnListener);
        btnSaveTactic.addActionListener(btnListener);
        btnExit.addActionListener(btnListener);

        // Définition du texte
        btnActionTactic.setText(strStart);
        //        btnLoadTactic.setText("Load Tactic");
        //        btnSaveTactic.setText("Save Tactic");
        //        btnExit.setText("Exit");

        // Initialisation des touches
        //        F6 = RMG_KeyEvent.VK_F6;
        //        F7 = RMG_KeyEvent.VK_F7;
        //
        //        // Enregistrement des touches pour leurs interceptions
        //        JIntellitype.getInstance().registerHotKey(F6, 0, F6);
        //        JIntellitype.getInstance().registerHotKey(F7, 0, F7);
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
            case RMG_KeyEvent.EVENT_F7:
                if(isStartAction(btnActionTactic)){
                    startTactic();
                }else{
                    stopTactic();
                }
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
