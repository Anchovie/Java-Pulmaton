package converter;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/*Author Panajis Rantala 11.2.14
 * Ohjelma salaa halutun viestin annetulla yksinkertaisella algoritmilla. 
 * Ylemp‰‰n kentt‰‰n kirjoitetaan normaali teksti, joka voidaan k‰‰nt‰‰ 'kryptaa'-
 * nappia painamalla. Alaosaan voi kirjoittaa jo kryptattua teksti‰, joka selvitet‰‰n
 * 'dekryptaa'-napilla. 'Tyhjenn‰'-napilla voi tyhjent‰‰ molemmat kent‰t. 'Lis‰‰'-
 * laatikolla, automaattisen tyhjennyksen voi lopettaa, ja muutettavat tekstit vain
 * lis‰t‰‰n vanhojen per‰‰n. GUI:ssa on paljon ylim‰‰r‰ist‰ jota ei vaadittu, mutta
 * teht‰v‰ oli mukavaa puuhastelua, ja sain verestetty‰ muistia joistain asioista, ja
 * opin jotain uuttakin. Suurin osa debug-kommenteista on j‰tetty koodiin, vaikka se
 * rumentaakin koodia melkoisen.
 */
public class Gui extends JPanel implements ActionListener, ComponentListener{ 
    
    private static final long serialVersionUID = 1L;
    private static final int MIN_WIDTH = 450;
    private static final int MIN_HEIGHT = 250;
    private static JFrame frame;
    private static final Font nameFont = new Font("Monospaced",Font.PLAIN,10);
    private JLabel quoteLabel, nameLabel;
    private JButton decryptButton, cryptButton, clearButton;
    private JCheckBox appendButton;
    private JTextArea upperField, lowerField;
    private int currentWidth, currentHeight;
    Converter converter;
    
     public Gui(){
        //"Suojaus", alempana lis‰‰ siit‰.
        if (!showValidationDialog()){
//            System.out.println("ƒh‰kutti");
            System.exit(ABORT);
        }
        //alustetaan komponentit, sek‰ asetetaan mm. pikan‰pp‰imet, kuuntelijat, vinkit sek‰ muuta.
        this.converter = new Converter();   //luokka joka hoitaa tekstin muuttamisen.
        setLayout(new BorderLayout());
        
        this.quoteLabel = new JLabel("DON'T PANIC!");
        this.quoteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.nameLabel = new JLabel("Panajis Rantala 2014");
        this.nameLabel.setFont(nameFont);
        this.nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        this.cryptButton = new JButton("Kryptaa");
        this.cryptButton.addActionListener(this);
        this.cryptButton.setMnemonic(KeyEvent.VK_K);
        this.cryptButton.setToolTipText("Kryptaa ylemm‰n kent‰n tekstin algoritmilla");
        
        this.decryptButton = new JButton("Dekryptaa");
        this.decryptButton.addActionListener(this);
        this.decryptButton.setMnemonic(KeyEvent.VK_D);
        this.decryptButton.setToolTipText("Dekryptaa alemman kent‰n tekstin algoritmilla");
        
        this.clearButton = new JButton("Tyhjenn‰");
        this.clearButton.addActionListener(this);
        this.clearButton.setMnemonic(KeyEvent.VK_T);
        this.clearButton.setToolTipText("Tyhjenn‰ molemmat kent‰t");
        
        this.appendButton = new JCheckBox("Lis‰‰");
        this.appendButton.addActionListener(this);
        this.appendButton.setMnemonic(KeyEvent.VK_L); 
        this.appendButton.setSelected(false);
        this.appendButton.setToolTipText("ƒl‰ tyhjenn‰ kentti‰ ennen uutta k‰‰nnˆst‰");

//        debugButton = new JCheckBox("Debug");
//        debugButton.addActionListener(this);
//        debugButton.setMnemonic(KeyEvent.VK_D); 
//        debugButton.setSelected(false);
//        debugButton.setToolTipText("Salli \"debug\"-viestit terminaalissa.");
        
        this.upperField = new JTextArea(15, 4);
        this.upperField.setText("Kryptattava teksti t‰h‰n, selvitett‰v‰ teksti alle. " +
        		"'Kryptaa'-nappi muuttaa yl‰kent‰n tekstin kryptatuksi alakentt‰‰n, " +
        		"ja 'dekryptaa' tekee p‰invastoin.");
        this.upperField.setEditable(true);
        this.upperField.setLineWrap(true);
        this.upperField.setWrapStyleWord(true);
        this.upperField.setBorder(BorderFactory.createEtchedBorder());
        JScrollPane upperScroll = new JScrollPane(this.upperField);
        upperScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        this.lowerField = new JTextArea(15, 4);
        this.lowerField.setEditable(true);
        this.lowerField.setLineWrap(true);
        this.lowerField.setWrapStyleWord(true);
        this.lowerField.setBorder(BorderFactory.createEtchedBorder());
        JScrollPane lowerScroll = new JScrollPane(this.lowerField);
        lowerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        //Asetetaan komponentit paneeleille, jotka j‰rjestell‰‰n kivan n‰kˆisiksi.
        JPanel xPanel = new JPanel();
        xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.LINE_AXIS));
        xPanel.add(Box.createGlue());
        xPanel.add(this.cryptButton);
        xPanel.add(Box.createHorizontalStrut(15));
        xPanel.add(this.clearButton);
        xPanel.add(Box.createHorizontalStrut(15));
        xPanel.add(this.decryptButton);
        xPanel.add(Box.createHorizontalStrut(30));
        xPanel.add(this.appendButton);
        xPanel.add(Box.createGlue());
        xPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel,BoxLayout.PAGE_AXIS));
        topPanel.add(Box.createGlue());
        topPanel.add(this.quoteLabel,BorderLayout.CENTER);
        topPanel.add(Box.createGlue());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        JPanel yPanel = new JPanel();
        yPanel.setLayout(new BoxLayout(yPanel, BoxLayout.PAGE_AXIS));
        yPanel.add(topPanel);
        yPanel.add(Box.createVerticalStrut(5));
        yPanel.add(upperScroll);
        yPanel.add(Box.createGlue());
        yPanel.add(xPanel);
        yPanel.add(Box.createGlue());
        yPanel.add(lowerScroll);
        yPanel.add(Box.createGlue());
        yPanel.add(this.nameLabel);
        yPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        //lis‰t‰‰n varsinaiseen JFrameen koko hoito
        add(yPanel);
     }
    
    //Kuuntelijat, muuttavat tekstin kentist‰ tai tyhjent‰v‰t ne.
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.upperField || e.getSource() == this.cryptButton){ 
            if (this.appendButton.isSelected())
                this.lowerField.append(this.converter.crypt(this.upperField.getText().toUpperCase()));
            else
                this.lowerField.setText(this.converter.crypt(this.upperField.getText().toUpperCase()));
            //System.out.println("upperField activated, text there is: \n"+ upperField.getText());
        }
        if (e.getSource() == this.lowerField || e.getSource() == this.decryptButton){    
            if (this.appendButton.isSelected())
                this.upperField.append(this.converter.decrypt(this.lowerField.getText().toLowerCase()));
            else
                this.upperField.setText(this.converter.decrypt(this.lowerField.getText().toLowerCase()));
            //System.out.println("lowerField activated, text there is: \n"+ lowerField.getText());
        }
        if (e.getSource() == this.clearButton) {
            this.upperField.setText("");
            this.lowerField.setText("");
        }
    }
    
    public void componentHidden(ComponentEvent arg0) {}
    public void componentMoved(ComponentEvent arg0) {}
    public void componentShown(ComponentEvent arg0) {}
    
    //turhake, mutta est‰‰ komponenttien katoamisen jos ikkunaa pienennet‰‰n liikaa
    public void componentResized(ComponentEvent e) {  
        boolean resize = false;
        if (getWidth() < MIN_WIDTH) {
            resize = true;
            this.currentWidth = MIN_WIDTH;
        }
        if (getHeight() < MIN_HEIGHT) {
            resize = true;
            this.currentHeight = MIN_HEIGHT;
        }
        if (resize) 
            setSize(this.currentWidth, this.currentHeight);
    }
    //Alustetaan JFrame ja lis‰t‰‰n perustietoja.
    private static void createAndShowGUI() {
        frame = new JFrame("Herlokki Solmusen Salausalgoritmi");
        frame.setMinimumSize(new Dimension(MIN_WIDTH,MIN_HEIGHT));
        frame.setPreferredSize(new Dimension(550,400));
        frame.setLocation(new Point(500,300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Gui newContentPane = new Gui();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.pack();
        frame.setVisible(true);
    }
    
    //Alun varmennus, funktio kutsuu itse‰‰n rekursiivisesti, kunnes oikea vastaus saadaan.
    //Ikkunaa ei voi sulkea tarkoituksellisesti (supersalainen ohjelma!) Mutta q,Q tai quit 
    //lopettaa ohjelman. Jos oikea vastaus annetaan, siirtyy suoritus eteenp‰in.
    private boolean showValidationDialog(){
        String answer = JOptionPane.showInputDialog(null,"Mik‰ on vastaus suureen kysymykseen el‰m‰st‰,\n" +
        		"maailmankaikkeudesta ja muusta sellaisesta?","Varmennus",JOptionPane.QUESTION_MESSAGE);
        if(answer != null && answer != "") {
            if (answer.equalsIgnoreCase("q") || answer.equalsIgnoreCase("quit") || answer.equalsIgnoreCase("exit"))
                return false;
            try { 
                Integer.parseInt(answer); 
            } catch(NumberFormatException e) { 
                return showValidationDialog(); 
            }
            if(Integer.parseInt(answer)==42)
                return true;
            else 
                return showValidationDialog();
        }else
            return showValidationDialog();
    }
    //Aloituspiste, josta "varmuuden vuoksi" l‰hdet‰‰n invokeLaterilla
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}