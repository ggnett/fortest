import java.util.* ;

public class Calculator {
    boolean error = true;
    String menu = "";
    public static void main(String [] args) {
        Calculator calc = new Calculator();
        calc.go();
    }

    public void go() {
        while (menu.equals("end") != true) {
            int key = 0;
            System.out.println("Введите 2 числа для выполнения операции. \nДля выхода введите \' end \'");
            Scanner qscanner = new Scanner(System.in);
            menu = qscanner.nextLine();
            char [] chMas = menu.toCharArray();
            boolean chek = chek(chMas);
            if (error) {
            int n1 = 0;
            int n2 = 0;
            int rez = 0;
            for (char i : chMas) {
                switch (i) {
                    case '+': if (chek) {
                            n1 = Integer.parseInt(menu.substring(0,key));
                            n2 = Integer.parseInt(menu.substring(key+1));
                            if (n1>10 || n2>10 || n1<1 || n2<1) {
                                System.out.println("Одно из чисел не входит в диапазон \n");
                                break;
                              }
                              rez = n1 + n2;
                              key++;
                              System.out.println(menu + " = " + rez+ "\n");
                              break;
                            } else {
                                n1 = repl(menu.substring(0,key));
                                n2 = repl(menu.substring(key+1));
                                if (n1<0 || n2<0) {
                                    System.out.println("Некорректный ввод \n");
                                    break;
                                }
                                rez = n1+n2;
                                String rezs = repl2(rez);
                                System.out.println(menu + " = " + rezs+ "\n");
                                break;
                            }
                    case '-': if (chek) {
                            n1 = Integer.parseInt(menu.substring(0,key));
                            n2 = Integer.parseInt(menu.substring(key+1));
                            if (n1>10 || n2>10 || n1<1 || n2<1) {
                                System.out.println("Одно из чисел не входит в диапазон \n");
                                break;
                              }
                              rez = n1 - n2;
                              key++;
                              System.out.println(menu + " = " + rez+ "\n");
                              break;
                            } else {
                                n1 = repl(menu.substring(0,key));
                                n2 = repl(menu.substring(key+1));
                                if (n1<0 || n2<0) {
                                    System.out.println("Некорректный ввод \n");
                                    break;
                                }
                                rez = n1-n2;
                                if (rez<0) {
                                    System.out.println("Некорректный ввод, римское число не может быть отрицательным");
                                    break;
                                }
                                String rezs = repl2(rez);
                                System.out.println(menu + " = " + rezs+ "\n");
                                break;
                            }
                    case '*': if (chek) {
                            n1 = Integer.parseInt(menu.substring(0,key));
                            n2 = Integer.parseInt(menu.substring(key+1));
                            if (n1>10 || n2>10 || n1<1 || n2<1) {
                                System.out.println("Одно из чисел не входит в диапазон \n");
                                break;
                              }
                              rez = n1 * n2;
                              key++;
                              System.out.println(menu + " = " + rez+ "\n");
                              break;
                            } else {
                                n1 = repl(menu.substring(0,key));
                                n2 = repl(menu.substring(key+1));
                                if (n1<0 || n2<0) {
                                    System.out.println("Некорректный ввод \n");
                                    break;
                                }
                                rez = n1*n2;
                                String rezs = repl2(rez);
                                System.out.println(menu + " = " + rezs+ "\n");
                                break;
                            }
                    case '/': if (chek) {
                            n1 = Integer.parseInt(menu.substring(0,key));
                            n2 = Integer.parseInt(menu.substring(key+1));
                            if (n1>10 || n2>10 || n1<1 || n2<1) {
                                System.out.println("Одно из чисел не входит в диапазон \n");
                                break;
                              }
                              rez = n1 / n2;
                              key++;
                              System.out.println(menu + " = " + rez+ "\n");
                              break;
                            } else {
                                n1 = repl(menu.substring(0,key));
                                n2 = repl(menu.substring(key+1));
                                if (n1<0 || n2<0) {
                                    System.out.println("Некорректный ввод \n");
                                    break;
                                }
                                rez = n1/n2;
                                String rezs = repl2(rez);
                                System.out.println(menu + " = " + rezs+ "\n");
                                break;
                            }
                    default: key++;

                }
            }
            } else { System.out.println("некорректный ввод \n");
                System.out.println("Завершение работы...");
                menu = "end";
                error = true;} // esli est' i nums i words
        }
    }
//proverka tol'ko li chisla
    public boolean chek (char[] ch) {
        boolean otv = true;
        ArrayList<Character> znak =new ArrayList<Character>();
        znak.add('+');
        znak.add('-');
        znak.add('*');
        znak.add('/');
        int key2 = 0;
        int key4 = 0;
        for (char i : ch) {
            if (Character.isLetter(i)) {
                otv = false;
                if (key2 > 1) {
                    error = false;
                }
            } else {
                key2++;
                if (znak.contains(i)) {key4++;}
                if (key4>1) {error = false;
                    System.out.println("Завершение работы...");
                    menu = "end";} // esli bol'she dvuh operaciy  v odnoy stroke
            }
        }
        if (key4==0) {error = false;
            System.out.println("Завершение работы...");
            menu = "end";} //esli ne vvedeno znaka
        return otv;
    }

// perevod iz ar v rim
    Integer repl (String s) {
        String [] bibl = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
        int size = bibl.length;
        int num=-1;
        for (int i = 0; i < size; i++ ) {
            if (s.toUpperCase().equals(bibl[i])) {
                num = i+1;
                
            }
        }
        return num;
    }

//perevod iz rim v ar
    String repl2(Integer n) {
        String [] bibl = {"nulla","I","II","III","IV","V","VI","VII","VIII","IX","X", //1-10
        "XI","XII","XIII","XIV","XV","XVI","XVII","XVIII","XIX","XX",                 //11-20
        "XXI","XXII","XXIII","XXIV","XXV","XXVI","XXVII","XXVIII","XXIX","XXX",       //21-30
        "XXXi","XXXII","XXXIII","XXXIV","XXXV","XXXVI","XXXVII","XXXVIII","XXXIX","XL",//31-40
        "XLI","XLII","XLIII","XLIV","XLV","XLVI","XLVII","XLVIII","XLIX","L",       //41-50
        "LI","LII","LIII","LIV","LV","LVI","LVII","LVIII","LIX","LX",                 //51-60
        "LXI","LXII","LXIII","LXIV","LXV","LXVI","LXVII","LXVIII","LXIX","LXX",       //61-70
        "LXXI","LXXII","LXXIII","LXXIV","LXXV","LXXVI","LXXVII","LXXVIII","LXXIX","LXXX",//71-80
        "LXXXI","LXXXII","LXXXIII","LXXXIV","LXXXV","LXXXVI","LXXXVII","LXXXVIII","LXXXIX","XC",//81-90
        "XCI","XCII","XCIII","XCIV","XCV","XCVI","XCVII","XCVIII","XCIX","C"}; //91-100
        String otv = bibl[n];
        return otv;
    };
}
