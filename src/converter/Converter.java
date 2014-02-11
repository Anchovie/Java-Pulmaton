package converter;

import java.util.*;


public class Converter {
    
    //Aakkos-hexa-parit tallennetaan hiukan h�lm�sti HashMappeihin
    private HashMap<Character, String> cryptMap;
    private HashMap<String, Character> decryptMap;
    
    public Converter(){
        this.cryptMap = new HashMap<Character, String>();
        this.decryptMap = new HashMap<String, Character>();
        for (int i=1; i<=26; i++){
            String hex;
            if (i%2==0)
                hex = Integer.toHexString(((32-i)*4)-1);
            else
                hex = Integer.toHexString(i*2);
            if (hex.length()==1)
                hex="0".concat(hex);
            //Luetaan parit yksitellen k�ytt�en kirjaimien numeroarvoja.
            this.cryptMap.put(((char)('A'+i-1)), hex);
            this.decryptMap.put(hex, ((char)('A'+i-1)));
        }
        //Luetaan lis�ksi erikoismerkit yksitellen.
        this.cryptMap.put(',', "01");
        this.cryptMap.put('.', "05");
        this.cryptMap.put('!', "09");
        this.cryptMap.put('?', "13");
        this.cryptMap.put(' ', "00");
        this.decryptMap.put("01",',');
        this.decryptMap.put("05",'.');
        this.decryptMap.put("09",'!');
        this.decryptMap.put("13",'?');
        this.decryptMap.put("00",' ');
//      System.out.println("Kryptaa: ");
//      String text = read.nextLine().toUpperCase();
//      System.out.println("UNCRYPTED = "+text+"\nCRYPTED = " + crypt(text, cryptMap));
        
//      System.out.println("Dekryptaa: ");
//      String text2 = read.nextLine().toLowerCase();
//      System.out.println("CRYPTED = "+text2+"\nDECRYPTED = " + decrypt(text2, decryptMap));
    }
    //kryptaus yksinkertaisesti hakee kirjain kerrallaan vastaavan hexan, ja lis�� sen uuden tekstin per��n.
    protected String crypt(String text){
        String crypted="";
        for (int i=0; i<text.length(); i++){
//          System.out.println("i = "+i+" Crypted: " + crypted);
            if (this.cryptMap.containsKey(text.charAt(i)))  //jos avainta ei l�ydy, ei tehd� mit��n ("Muut merkit j�tet��n huomiotta.")
                crypted+=this.cryptMap.get(text.charAt(i));
//            else System.out.println("SKIPPED '" + text.charAt(i)+"' (NOT FOUND FROM MAP)");
        }
        return crypted;
    }
    //toiseen suuntaan kryptaus toimii samalla tavalla lukuunottamatta sit�, ett� hexat luetaan kaksi kerrallaan
    protected String decrypt(String text){
        String decrypted="";
//        System.out.println("length = " + text.length());
        for (int i=0; i<text.length(); i+=2){   //inkrementoidaan kahdella, ja luetaan aina indeksi ja indeksi+1 avaimeksi
            if (i==text.length()-1){            //jos heksoja ei ole tasam��r� (jotain pieless�), 
                                                //astutaan ulos loopista joka muuten menisi yli rajan.
//                System.out.println("(riitt�m�t�n m��r� kirjaimia heksadesimaaleina)");
                break;
            }
//          System.out.println("i = "+i+" Crypted: " + crypted);
            String hex = Character.toString(text.charAt(i)).concat(Character.toString(text.charAt(i+1)));
            if (this.decryptMap.containsKey(hex))
                decrypted+=this.decryptMap.get(hex);
//            else System.out.println("SKIPPED '" +hex +"' (NOT FOUND FROM MAP)");
        }
        return decrypted;
    }
}
