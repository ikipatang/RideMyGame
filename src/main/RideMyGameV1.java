//package main;
//
//import helper.HelperLog;
//
//import java.awt.BorderLayout;
//import java.awt.Frame;
//import java.awt.GraphicsEnvironment;
//import java.awt.Point;
//import java.awt.Rectangle;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.RMG_KeyEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.util.Vector;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JList;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.border.EtchedBorder;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//
//import model.ModelInstruction;
//import model.ModelMouse;
//import model.ModelPosition;
//import model.ModelTactic;
//
//import controller.ControllerReadTactic;
//import controller.ControllerSaveTactic;
//import controller.ControllerTactic;
//
//public class RideMyGameV1 extends JFrame implements HotkeyListener, IntellitypeListener
//{
//    private static final long serialVersionUID = 1L;
//
//    // Général
//    private static RideMyGameV1 mainFrame;
//
//    // Button and hotkey
//    private int     F6;
//    private int     F7;
//    private JButton btnAction;
//    private JButton btnLoadClicks;
//    private JButton btnSaveClicks;
//    private JButton btnExit;
//
//    // Nom
//    private String strStart = "Start";
//    private String strStop  = "Stop";
//
//    // Listener
//    private WindowAdapter         windowListener;
//    private ActionListener        btnListener;
//    private ListSelectionListener listInstructionListener;
//
//    // Panel
//    private JPanel centerPanel;
//    private JPanel mainPanel;
//    private JPanel topPanel;
//
//    // Gestion de la tactique
//    private ModelTactic      oCurrentTactic;
//    private ControllerTactic oControllerTactique;
//
//    /**
//     * @param args
//     */
//    public static void main(String[] args)
//    {
//        // Initialisation des logs
//        boolean out = true;
//        boolean err = true;
//        for(int i = 0; i < args.length; i++){
//            if(args[i].toUpperCase().compareTo("-NOERROR") == 0){
//                err = false;
//            }else if(args[i].toUpperCase().compareTo("-NOINFO") == 0){
//                out = false;
//            }
//        }
//        HelperLog.getLog(out, err);
//
//        // Check to make sure JIntellitype DLL can be found and we are on a Windows operating System
//        if(!JIntellitype.isJIntellitypeSupported()){
//            HelperLog.getLog().logErr("JIntellitype not supported");
//            mainFrame.exit(1);
//        }
//
//        // TODO à revoir
//        mainFrame = new RideMyGameV1();
//        mainFrame.setTitle("RideMyGameV1");
//        center(mainFrame);
//        mainFrame.setVisible(true);
//        mainFrame.initJIntellitype();
//
//        // Check to make sure JIntellitype DLL can be found and we are on a Windows operating System
//        if(!JIntellitype.isJIntellitypeSupported()){
//            HelperLog.getLog().logErr("JIntellitype not supported");
//            mainFrame.exit(1);
//        }
//        // mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(mainFrame.getClass().getResource("RideMyGameV1.png")));
//    }
//
//    /**
//     * Création d'un formulaire
//     */
//    public RideMyGameV1()
//    {
//        initListener();
//        initButtonsAndHotkeys();
//        initPanels();
//
//        this.getContentPane().add(mainPanel);
//        this.pack();
//        this.setSize(800, 600);
//
//        initListener();
//        this.addWindowListener(windowListener);
//    }
//
//    /**
//     * Initialise les panneaux
//     */
//    private void initPanels()
//    {
//        topPanel = getTopPanel();
//        centerPanel = getCenterPanel();
//
//        mainPanel = new JPanel();
//        mainPanel.setLayout(new BorderLayout());
//        mainPanel.add(topPanel, BorderLayout.NORTH);
//        mainPanel.add(centerPanel, BorderLayout.CENTER);
//    }
//
//    /**
//     * @return
//     */
//    private JPanel getTopPanel()
//    {
//        JPanel jPanel = new JPanel();
//        jPanel.setBorder(new EtchedBorder(1));
//        jPanel.add(btnAction);
//        jPanel.add(btnLoadClicks);
//        jPanel.add(btnSaveClicks);
//        jPanel.add(btnExit);
//
//        return jPanel;
//    }
//
//    private JPanel getCenterPanel()
//    {
//        JPanel jPanel = new JPanel();
//        jPanel.setLayout(new BorderLayout());
//        jPanel.setBorder(new EtchedBorder(1));
//
//        JScrollPane scrollPane = new JScrollPane();
//        // scrollPane.getViewport().add(textArea);
//        jPanel.add(scrollPane, BorderLayout.CENTER);
//
//        JList<Vector<ModelInstruction>> oListInstruction = new JList<Vector<ModelInstruction>>();
//        oListInstruction.addListSelectionListener(listInstructionListener);
//
//        return jPanel;
//    }
//
//    /**
//     * Initialise les boutons
//     */
//    private void initButtonsAndHotkeys()
//    {
//        // Initialisation des boutons
//        btnAction = new JButton();
//        btnExit = new JButton();
//        btnLoadClicks = new JButton();
//        btnSaveClicks = new JButton();
//
//        // Ajout des listeners aux boutons
//        btnAction.addActionListener(btnListener);
//        btnLoadClicks.addActionListener(btnListener);
//        btnSaveClicks.addActionListener(btnListener);
//        btnExit.addActionListener(btnListener);
//
//        // Définition du texte
//        btnAction.setText(strStart);
//        btnLoadClicks.setText("Load Clicks");
//        btnSaveClicks.setText("Save Clicks");
//        btnExit.setText("Exit");
//
//        // Initialisation des touches
//        F6 = RMG_KeyEvent.VK_F6;
//        F7 = RMG_KeyEvent.VK_F7;
//
//        // Enregistrement des touches pour leurs interceptions
//        JIntellitype.getInstance().registerHotKey(F6, 0, F6);
//        JIntellitype.getInstance().registerHotKey(F7, 0, F7);
//    }
//
//    /**
//     * Initialize the JInitellitype library making sure the DLL is located.
//     */
//    private void initJIntellitype()
//    {
//        try{
//            // initialize JIntellitype with the frame so all windows commands can
//            // be attached to this window
//            JIntellitype.getInstance().addHotKeyListener(this);
//            JIntellitype.getInstance().addIntellitypeListener(this);
//            HelperLog.getLog().logInfo("JIntellitype initialized");
//        }catch(RuntimeException ex){
//            HelperLog.getLog().logErr("Either you are not on Windows, or there is a problem with the JIntellitype library!");
//        }
//    }
//
//    /**
//     * Centers window on desktop.
//     * <p/>
//     * @param aFrame the Frame to center
//     */
//    private static void center(JFrame aFrame)
//    {
//        final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        final Point centerPoint = ge.getCenterPoint();
//        final Rectangle bounds = ge.getMaximumWindowBounds();
//        final int w = Math.min(aFrame.getWidth(), bounds.width);
//        final int h = Math.min(aFrame.getHeight(), bounds.height);
//        final int x = centerPoint.x - (w / 2);
//        final int y = centerPoint.y - (h / 2);
//        aFrame.setBounds(x, y, w, h);
//        if((w == bounds.width) && (h == bounds.height)){
//            aFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
//        }
//        aFrame.validate();
//    }
//
//    /**
//     *
//     */
//    protected void loadClicks()
//    {
//        ControllerReadTactic oTacticReader = new ControllerReadTactic("", "test");
//        oTacticReader.readTactique();
//        oCurrentTactic = oTacticReader.getTactic();
//        if(oCurrentTactic != null){
//            HelperLog.getLog().logInfo("Tactic loaded !");
//
//        }
//    }
//
//    /**
//     * @param oTactic
//     */
//    protected void saveClicks(ModelTactic oTactic)
//    {
//        ControllerSaveTactic oTacticSaver = new ControllerSaveTactic("", oTactic.getName() + "_bis");
//        if(oTacticSaver.saveTactic(oTactic)){
//            // Traitement d'erreur
//        }
//    }
//
//    /**
//     * Exit de l'application en oubliant de gérer le listener JIntellitype
//     * @param iExitStatus
//     */
//    private void exit(int iExitStatus)
//    {
//        // don't forget to clean up any resources before close
//        if(JIntellitype.isJIntellitypeSupported()){
//            JIntellitype.getInstance().cleanUp();
//        }
//        System.exit(iExitStatus);
//    }
//
//    /**
//     * ======================== Listener ========================
//     */
//
//    /**
//     * Active les listeners
//     */
//    private void initListener()
//    {
//
//        listInstructionListener = new ListSelectionListener()
//        {
//            public void valueChanged(ListSelectionEvent e)
//            {
//                // TODO récupérer l'instruction actuel
//            }
//        };
//
//        btnListener = new ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//                JButton btnPressed = (JButton) e.getSource();
//                if(btnPressed == btnAction){
//                    if(isStartAction(btnPressed)){
//                        startTactic();
//                    }else if(isStopAction(btnPressed)){
//                        stopTactic();
//                    }
//                }else if(btnPressed == btnExit){
//                    exit(0);
//                }else if(btnPressed == btnLoadClicks){
//                    loadClicks();
//                }else if(btnPressed == btnSaveClicks){
//                    saveClicks(oCurrentTactic);
//                }
//            }
//        };
//
//        windowListener = new WindowAdapter()
//        {
//            public void windowClosing(WindowEvent evt)
//            {
//                exit(0);
//            }
//        };
//    }
//
//    /**
//     *
//     */
//    protected void stopTactic()
//    {
//        HelperLog.getLog().logInfo("Stop");
//        btnAction.setText(strStart);
//        oControllerTactique.tactiqueStop();
//    }
//
//    /**
//     *
//     */
//    protected void startTactic()
//    {
//        if(oCurrentTactic != null){
//            btnAction.setText(strStop);
//            oControllerTactique = new ControllerTactic(oCurrentTactic.getInstructions());
//            oControllerTactique.start();
//        }
//    }
//
//    /**
//     * (non-Javadoc)
//     * @see com.melloware.jintellitype.HotkeyListener#onHotKey(int)
//     */
//    public void onHotKey(int iIdentifier)
//    {
//        if(iIdentifier == F6){
//            ModelPosition oCurrentPos = ModelMouse.getMousePosition();
//            HelperLog.getLog().logInfo("Récupération de la position => " + oCurrentPos.getPosX() + ":" + oCurrentPos.getPoxY());
//        }
//        if(iIdentifier == F7){
//            if(isStartAction(btnAction)){
//                startTactic();
//            }else if(isStopAction(btnAction)){
//                stopTactic();
//            }else{
//                HelperLog.getLog().logInfo("Not understood");
//            }
//        }
//    }
//
//    /**
//     * Détermine si il s'agit d'un bouton start
//     * @param btnPressed
//     * @return
//     */
//    private boolean isStartAction(JButton btnPressed)
//    {
//        return btnPressed.getText() == strStart;
//    }
//
//    /**
//     * Détermine si il s'agti d'un bouton stop
//     * @param btnPressed
//     * @return
//     */
//    private boolean isStopAction(JButton btnPressed)
//    {
//        return btnPressed.getText() == strStop;
//    }
//
//    /**
//     * @param iCommand
//     */
//    public void onIntellitype(int iCommand)
//    {
//
//        String sCommand = Integer.toString(iCommand);
//        String sMessage = "";
//        switch(iCommand){
//            case JIntellitype.APPCOMMAND_BROWSER_BACKWARD:
//                sMessage = "BROWSER_BACKWARD message received ";
//                break;
//            case JIntellitype.APPCOMMAND_VOLUME_MUTE:
//                sMessage = "VOLUME_MUTE message received ";
//                break;
//            default:
//                sMessage = "Undefined INTELLITYPE message caught ";
//                break;
//        }
//
//        // output(sMessage + sCommand);
//    }
//}
