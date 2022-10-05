import java.util.ArrayList;

final class Viginer {

    private final ArrayList<Character> alphabet;
    private final ArrayList<ArrayList<Character>> table;
    private final int alphabetSize;

    public Viginer(ArrayList<Character> alphabet) {
        this.alphabet = alphabet;
        alphabetSize = alphabet.size();

        table = new ArrayList<>();
        int n = alphabetSize;
        for(int i = 0; i <= n; i++){
            table.add(shiftArrayList(alphabet, i));
        }

        System.out.println("    " + alphabet.toString());
        System.out.println();
        for(int i = 0; i <= n; i++){
            System.out.print(i + "   ");
            System.out.println(table.get(i).toString());
        }
    }

    private static ArrayList<Character> shiftArrayList(final ArrayList<Character> alphabet, int n){
        ArrayList<Character> result = new ArrayList<>();
        for(int i = n; i < alphabet.size() + n; i++){
            result.add(alphabet.get(i % alphabet.size()));
        }
        return result;
    }
//    public String encrypt(final String text, final String key) {
//        String encrypt = "";
//        int keyLength = key.length();
//        int skip = 0;
//        String[] numsKeyArray = key.split("");
//        for(int i = 0; i < text.length(); i++){
//            int code = Integer.parseInt(numsKeyArray[(i - skip) % keyLength]);
//            char textChar = text.charAt(i);
//            if (textChar == ' '){
//                skip++;
//                encrypt += ' ';
//                continue;
//            }
//            int index = alphabet.indexOf(textChar);
//            ArrayList<Character> alphabetByCode = table.get(code);
//            encrypt += alphabetByCode.get(index);
//        }
//        return encrypt;
//    }
//
//    public String decrypt(final String text, final String key) {
//        String decrypt = "";
//        int keyLength = key.length();
//        int skip = 0;
//        String[] numsKeyArray = key.split("");
//        for(int i = 0; i < text.length(); i++){
//            int code = Integer.parseInt(numsKeyArray[(i - skip) % keyLength]);
//            char textChar = text.charAt(i);
//            if (textChar == ' '){
//                skip++;
//                decrypt += textChar;
//                continue;
//            }
//            int index = alphabet.indexOf(textChar);
//            ArrayList<Character> alphabetByCode = table.get(code);
//            decrypt += alphabet.get(alphabetByCode.indexOf(textChar));
//        }
//        return decrypt;
//    }

    public String encrypt(final String text, final String key) {
        String encrypt = "";
        int keyLength = key.length();
        int skip = 0;
        for(int i = 0; i < text.length(); i++){
            int code = alphabet.indexOf(key.charAt((i - skip) % keyLength));
            char textChar = text.charAt(i);
            if (textChar == ' '){
                skip++;
                encrypt += ' ';
                continue;
            }
            int index = alphabet.indexOf(textChar);
            ArrayList<Character> alphabetByCode = table.get(code);
            encrypt += alphabetByCode.get(index);
        }
        return encrypt;
    }

    public String decrypt(final String text, final String key) {
        String decrypt = "";
        int keyLength = key.length();
        int skip = 0;
        for(int i = 0; i < text.length(); i++){
            int code = alphabet.indexOf(key.charAt((i - skip) % keyLength));
            char textChar = text.charAt(i);
            if (textChar == ' '){
                skip++;
                decrypt += textChar;
                continue;
            }
            int index = alphabet.indexOf(textChar);
            ArrayList<Character> alphabetByCode = table.get(code);
            decrypt += alphabet.get(alphabetByCode.indexOf(textChar));
        }
        return decrypt;
    }
}