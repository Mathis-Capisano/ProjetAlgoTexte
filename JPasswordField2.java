import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

class JPasswordField2 implements ActionListener {
    static String dest;
    JTextArea passwordField1 = null;
    JTextArea passwordField2 = null;
    JTextPane grandeZone1 = new JTextPane();
    JTextPane grandeZone2 = new JTextPane();
    static ArrayList<int[]> liste1 = new ArrayList();
    static ArrayList<int[]> liste2 = new ArrayList();
     Highlighter.HighlightPainter cyanPainter;
     Highlighter.HighlightPainter redPainter;
    JButton bouton1 = new JButton("choisir fichier");
    JButton bouton2 = new JButton("transformer");
    JButton bouton3 = new JButton("Enregistrer");
    String emplacement;
    String  Text= "";
    public static void main(String argv[]) {
        JPasswordField2 jpf2 = new JPasswordField2();
        jpf2.init();
    }

    public void init() {
        JFrame f = new JFrame("ma fenetre");
        f.setSize(800, 1600);
        JLabel l1,l2;
        JScrollPane texteAsc1;
        JScrollPane texteAsc2;
        JPanel panneauTaille = new JPanel();
        JPanel pannel = new JPanel();
        JPanel panel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        f.setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        // Put constraints on different buttons
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Font fonte;
        passwordField1 = new JTextArea("");
        passwordField1.setPreferredSize(new Dimension(100, 25));
        passwordField2 = new JTextArea("");
        passwordField2.setPreferredSize(new Dimension(100, 25));

        grandeZone1.setPreferredSize(new Dimension(100, 700));
        grandeZone1.setMaximumSize(new Dimension(100, 700));
        cyanPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.cyan);
        redPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.red);
        grandeZone2.setPreferredSize(new Dimension(100, 700));
        grandeZone2.setMaximumSize(new Dimension(100, 700));
        l1=new JLabel("Occurence");
        l2=new JLabel("transformer en ");
        gbc.gridx=0;
        gbc.gridy=0;
        f.add(l1,gbc);
        gbc.gridx=2;
        gbc.gridy=0;
        f.add(l2,gbc);
        gbc.gridx=0;
        gbc.gridy=1;
        f.add(passwordField1,gbc);
        gbc.gridx=2;
        f.add(passwordField2,gbc);
        bouton1.addActionListener(this);
        gbc.gridx=4;
        gbc.gridy=0;
        f.add(bouton1,gbc);
        bouton2.addActionListener(this);
        gbc.gridy=1;
        f.add(bouton2,gbc);

       // fonte = new Font("TimesRoman", Font.PLAIN, 10);
        //grandeZone1.setFont(fonte);
        //grandeZone2.setFont(fonte);
        texteAsc1 = new JScrollPane(grandeZone1);
        texteAsc2 = new JScrollPane(grandeZone2);
        texteAsc1.setMinimumSize(new Dimension(100, 700));
        texteAsc2.setMinimumSize(new Dimension(100, 700));
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        // on attache la "fiche technique" au sous-composant
        f.add(texteAsc1,gbc);
        gbc.gridx=2;
        f.add(texteAsc2,gbc);
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        bouton3.addActionListener(this);
        gbc.gridx=4;
        f.add(bouton3,gbc);


       // f.getContentPane().add(pannel);
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == bouton1) {
            File repertoireCourant = null;
            try {
                // obtention d'un objet File qui désigne le répertoire courant. Le
                // "getCanonicalFile" n'est pas absolument nécessaire mais permet
                // d'éviter les /Truc/./Chose/ ...
                repertoireCourant = new File(".").getCanonicalFile();
                System.out.println("Répertoire courant : " + repertoireCourant);
            } catch (IOException e1) {
            }

            // création de la boîte de dialogue dans ce répertoire courant
            // (ou dans "home" s'il y a eu une erreur d'entrée/sortie, auquel
            // cas repertoireCourant vaut null)
            JFileChooser dialogue = new JFileChooser(repertoireCourant);

            // affichage
            dialogue.showOpenDialog(null);

            // récupération du fichier sélectionné
            emplacement = dialogue.getSelectedFile() + "\\";
            try {
                lecture(emplacement);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            grandeZone1.setText("");
            grandeZone1.setText(Text);
            Text = "";


        }
        if (source == bouton2) {
            System.out.println( "C'est moi " + KMP(grandeZone1.getText(), passwordField1.getText(), passwordField2.getText()));

            System.out.println( "C'est moi 2" + dest);
            grandeZone2.setText(KMP(grandeZone1.getText(), passwordField1.getText(), passwordField2.getText()));

            for (int i = 0 ; i < liste1.size();++i) {
                try {
                    grandeZone1.getHighlighter().addHighlight(liste1.get(i)[0], liste1.get(i)[1], redPainter);
                    grandeZone2.getHighlighter().addHighlight(liste2.get(i)[0], liste2.get(i)[1], redPainter);
                } catch (BadLocationException ble) {
                }
            }
            liste1.clear();
            liste2.clear();

        }

        if (source == bouton3) {
            String enregistrer ="";

            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int ret = jfc.showOpenDialog(null); // ne te rend la main que si tu ferme
            if (ret == JFileChooser.APPROVE_OPTION) { // validation
                enregistrer = jfc.getSelectedFile()+ "\\";


            }
            enregistrer = enregistrer +"\\"+ JOptionPane.showInputDialog(null, "nom du fichier :");
            try {
                enregistrer(enregistrer);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


        }
    }

    public  void lecture(String emplacement) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(emplacement));
        String line;
        while ((line = in.readLine()) != null) {
            // Afficher le contenu du fichier
            Text = Text +'\n' + line;
        }
        System.out.println(Text);
        in.close();

    }
    public static String KMP(String chaine , String occurence , String remplace){
        dest = chaine;
        boolean dejavu = false;
        String mots = "";
        int j=0;
        for(int i =0 ; i < chaine.length() ; ++i){
            if(chaine.charAt(i) == occurence.charAt(j)){
                if(j == occurence.length()-1){
                    System.out.println("i-j = "+(i-j));
                    System.out.println(chaine.substring(i-j, i+1));
                    replace_CIBLE_par_MOTIF_dans_DEST(chaine.substring(i-j, i+1),i-j,remplace);
                    j = 0 ;

                }
                else
                    ++j;



            }
            else{
                j = 0 ;

            }

        }
        return dest;


    }
    public static void replace_CIBLE_par_MOTIF_dans_DEST(String cible, int index,     String motif) {

        String partA = dest.substring(0, index) ;
        //	System.out.println(partA);
        String partB = motif;
        //System.out.println(partB);

        int borne = index + cible.length() ;
        int[] tab1 = {index,index + cible.length()};
        liste1.add(tab1);

        if (borne > dest.length()) {             borne = dest.length();         }

        String partC = dest.substring(borne, dest.length()) ;
        //System.out.println(partC);
        int[] tab2 = {index,index +motif.length()};
        liste2.add(tab2);
        dest =  partA +partB   +partC ;
    }
    public  void enregistrer(String emplacement) throws IOException {
        try {

            // Recevoir le fichier
            File f = new File(emplacement+".txt");

            // Créer un nouveau fichier
            // Vérifier s'il n'existe pas
            if (f.createNewFile())
                System.out.println("File created");
            else
                System.out.println("File already exists");

            FileWriter fw = new FileWriter(f,true);
            fw.write(grandeZone2.getText());
            fw.close();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }




}