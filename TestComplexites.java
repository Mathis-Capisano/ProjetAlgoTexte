import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;

class testComplexites {


    static String  Text= "";
    static int nbOcc = 0;

    public static void main(String argv[]) throws InterruptedException, FileNotFoundException, IOException {

        BufferedReader in = new BufferedReader(new FileReader("text.txt"));
        String line;
        while ((line = in.readLine()) != null) {
            Text = Text + line +'\n';
        }
        in.close();
        System.out.println("Taille : " + Text.length() + "\n");




        for(int i = 1; i <= Text.length()/10000; i++){
            String theText = Text.substring(0, (10000*i)) ;
            String motif = "m";
            nbOcc = 0;

            long millisDEB=System.currentTimeMillis();
            KMP(theText, motif, "x");
            long millisFIN=System.currentTimeMillis();

            long millisDEB2=System.currentTimeMillis();
            theText.replace(motif, "x");
            long millisFIN2=System.currentTimeMillis();

            System.out.println("Taille : " + theText.length());
            System.out.println("Motif : " + motif + "  x" + nbOcc);
            System.out.println("Temps (KMP): " + (millisFIN - millisDEB));
            System.out.println("Temps (JAVA): " + (millisFIN2 - millisDEB2) + "\n");



            Thread.sleep(1000);

        }
    }


    // Chaine = le texte; occurence = cible; remplace = nouveau
    public static String KMP(String chaine , String occurence , String remplace){
        String dest = chaine;
        int j = 0;
        int nbOccurences = 0;
        int differenceTaille = remplace.length() - occurence.length();
        boolean fini ;
        for(int i =0 ; i < dest.length() ; ++i) {
            fini = true;
            while(fini && (i+j) < dest.length()){
                if(dest.charAt(i+j) == occurence.charAt(j)) {
                    if(j == occurence.length()-1) {
                        int index = i;
                        String cible = dest.substring(index, i+(occurence.length()));
                        if ( differenceTaille > 0 ) {
                            int[] tab1 = {index-(differenceTaille*nbOccurences),index-(differenceTaille*nbOccurences) + cible.length()};
                        } else if ( differenceTaille < 0 ) {
                            int[] tab1 = {index+Math.abs(differenceTaille*nbOccurences),index+Math.abs(differenceTaille*nbOccurences) + cible.length()};
                        } else {
                            int[] tab1 = {index,index + cible.length()};
                        }
                        replaceAndHighlight(chaine,index,remplace);
                        nbOccurences++;
                        i = i + remplace.length()-1;
                        j=0;
                        fini = false;
                    }
                    else {
                        ++j;
                    }
                }
                else{
                    j = 0;
                    fini = false;
                }
            }
        }
        return dest;
    }

    public static void replaceAndHighlight(String cible, int index, String motif) {
        nbOcc++;
        String dest = cible; 

        String partA = dest.substring(0, index) ;
        String partB = motif;
        int borne = index + cible.length() ;

        if (borne > dest.length()) {
            borne = dest.length();
        }
        String partC = dest.substring(borne, dest.length()) ;
        int[] tab2 = {index,index +motif.length()};
        //liste2.add(tab2);
        dest =  partA + partB + partC;
    }

}
