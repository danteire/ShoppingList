import java.io.*;
import java.util.*;


public class Main {

    private static final String plikZProduktami = "produkty.txt";
    private static final String plikListaZakupow = "listaZakupow.txt";
    private static final Map<String, List<String>> produkty = new HashMap<>();
    private static final Map<String, List<String>> listaZakupow = new HashMap<>();

    public static void main(String[] args) {

        loadListeProduktow();
        loadListeZakupow();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            menuPrint();
            System.out.println("Enter an option: ");
            String option = scanner.nextLine();
            switch (option) {
                case "0":
                    //zakończ program

                    break;
                case "1":

                    //dodanie produktu do listy zakupów zgodnie z poniższym scenariuszem:
                    //program wyświetla dostępne kategorie
                    //użytkownik wybiera kategorię
                    //program wyświetla dostępne produkty z danej kategorii
                    //użytkownik wybiera produkt z danej kategorii
                    dodajProduktDoListyZakupow(scanner);
                    break;
                case "2":
                    //wyświetlenie wszystkich produktów z listy zakupów
                    listaZakupowPrint();
                    break;
                case "3":
                    //wyświetlenie wszystkich produktów z listy zakupów z danej kategorii (użytkownik wybiera kategorię)
                    allProduktyZKategoriaPrint(scanner);
                    break;
                case "4":
                    //usunięcie wszystkich produktów z listy zakupów
                    usunAllProduktyZListy();
                    break;
                case "5":
                    //usunięcie wszystkich produktów z listy zakupów z danej kategorii (użytkownik wybiera kategorię)
                    usunAllProduktyZKategorii(scanner);
                    break;
                case "6":
                    //usunięcie produktu z listy zakupów (użytkownik wybiera kategorię, następnie produkt)
                    usunProduktZKategorii(scanner);
                    break;
                case "7":
                    saveListeZakupow();
                    //zapis listy zakupów na dysku
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
            if(option.equals("0")){
                break;
            }
        }

        scanner.close();
    }

    private static void menuPrint(){
        System.out.println("\nProgram Menu:\n");
        System.out.println("0. Zakończ Program");
        System.out.println("1. Dodaj produkt do listy zakupów");
        System.out.println("2. Wyświetl wszystkie produkty z listy zakupów");
        System.out.println("3. Wyświetl produkty z listy zakupów z danej kategorii");
        System.out.println("4. Usuń wszystkie produkty z listy zakupów");
        System.out.println("5. Usuń wszystkie produkty z listy zakupów z danej kategorii");
        System.out.println("6. Usuń produkt z listy zakupów");
        System.out.println("7. Zapisz listę zakupów");
    }
    private static void kategoriePrint(){
        for(String kategoria : produkty.keySet()){
            System.out.println(kategoria);
        }
    }

    private static void loadListeProduktow(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(plikZProduktami));
            String line;
            String obecnaKategoria = null;

            while ((line = bufferedReader.readLine())!=null){
                if(!line.startsWith(" ")){
                    obecnaKategoria = line;
                    produkty.put(obecnaKategoria,new ArrayList<>());
                }else if(obecnaKategoria!=null){
                    produkty.get(obecnaKategoria).add(line.trim());
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Błąd podczas wczytywania pliku z produktami: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void loadListeZakupow(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(plikListaZakupow));
            String line;
            String obecnaKategoria = null;
            while ((line = bufferedReader.readLine())!=null){
                if(!line.startsWith(" ")){
                    obecnaKategoria = line;
                    listaZakupow.put(obecnaKategoria,new ArrayList<>());
                }else if(obecnaKategoria!=null){
                    listaZakupow.get(obecnaKategoria).add(line.trim());
                }
            }
        }catch (IOException e){
            System.out.println("Nie znaleziono wcześniejszej listy zakupów.");
        }
    }
    private static void saveListeZakupow(){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(plikListaZakupow,true));
            for(String kategoria : listaZakupow.keySet()){
                bufferedWriter.append(kategoria + "\n");
                for(String produkt : listaZakupow.get(kategoria)){
                    bufferedWriter.append(" " + produkt + "\n");
                }
            }
            bufferedWriter.close();
        }catch (IOException e){
            System.out.println("Błąd podczas zapisywania listy zakupów: " + e.getMessage());
        }
    }

    //1. //dodanie produktu do listy zakupów zgodnie z poniższym scenariuszem:
    //                    //program wyświetla dostępne kategorie
    //                    //użytkownik wybiera kategorię
    //                    //program wyświetla dostępne produkty z danej kategorii
    //                    //użytkownik wybiera produkt z danej kategorii

    private static void dodajProduktDoListyZakupow(Scanner scanner){

        System.out.println("Dostępne Kategorie:");
        kategoriePrint();

        System.out.println("Wybierz Kategorie:");
        String wybranaKategoria = scanner.nextLine();
        if(!produkty.containsKey(wybranaKategoria)){
            System.out.println("Nieprawidlowy Wybor!");
            return;
        }

        System.out.println("Dostępne Produkty");
        for(int i = 0; i< produkty.get(wybranaKategoria).size();i++){
            System.out.println((i+1) + ". " + produkty.get(wybranaKategoria).get(i));
        }

        System.out.println("Wybierz Produkt po numerze indeksu:");
        int indexProduktu = Integer.parseInt(scanner.nextLine()) - 1;
        if(indexProduktu <0 || indexProduktu >= produkty.get(wybranaKategoria).size()){
            System.out.println("Nieprawidłowy wybór produktu.");
            return;
        }

        String produktDoDodania = produkty.get(wybranaKategoria).get(indexProduktu);
        listaZakupow.putIfAbsent(wybranaKategoria, new ArrayList<>());
        listaZakupow.get(wybranaKategoria).add(produktDoDodania);
        System.out.println("Dodano " + produktDoDodania + " do listy zakupow!");
    }

    //2. wyświetlenie wszystkich produktów z listy zakupów

    private static void listaZakupowPrint(){
        if(listaZakupow.isEmpty()){
            System.out.println("Lista Zakupow jest pusta!");
            return;
        }else{
            for(String kategoria : listaZakupow.keySet()){
                System.out.println(kategoria + ":");
                for (String produkt : listaZakupow.get(kategoria)){
                    System.out.println(" " + produkt);
                }
            }
        }
    }

    //3. wyświetlenie wszystkich produktów z listy zakupów z danej kategorii (użytkownik wybiera kategorię)
    private static void allProduktyZKategoriaPrint(Scanner scanner){
        System.out.println("Dostępne Kategorie:");
        kategoriePrint();

        System.out.println("Wybierz Kategorie:");
        String wybranaKategoria = scanner.nextLine();
        if(!produkty.containsKey(wybranaKategoria)){
            System.out.println("Nieprawidlowy Wybor!");
            return;
        }
        System.out.println(wybranaKategoria + ": ");
        for(String produkt : produkty.get(wybranaKategoria)){
            System.out.println(" " + produkt);
        }
    }

    //4. usunięcie wszystkich produktów z listy zakupów

    private static void usunAllProduktyZListy(){
        listaZakupow.clear();
        System.out.println("Lista zakupow zostala wyczyszczona z zakupow");
    }

    //5. usunięcie wszystkich produktów z listy zakupów z danej kategorii (użytkownik wybiera kategorię)

    private static void usunAllProduktyZKategorii(Scanner scanner){
        System.out.println("Dostępne Kategorie:");
        kategoriePrint();

        System.out.println("Wybierz Kategorie:");
        String wybranaKategoria = scanner.nextLine();
        if(!produkty.containsKey(wybranaKategoria)){
            System.out.println("Nieprawidlowy Wybor!");
            return;
        }
        listaZakupow.remove(wybranaKategoria);
        System.out.println("Usunieto wszystkie produkty z kategorii: " + wybranaKategoria);
    }

    //6. usunięcie produktu z listy zakupów (użytkownik wybiera kategorię, następnie produkt)

    private static void usunProduktZKategorii(Scanner scanner){
        System.out.println("Dostępne Kategorie:");
        kategoriePrint();

        System.out.println("Wybierz Kategorie:");
        String wybranaKategoria = scanner.nextLine();
        if(!produkty.containsKey(wybranaKategoria)){
            System.out.println("Nieprawidlowy Wybor!");
            return;
        }

        System.out.println("Dostępne Produkty");
        for(int i = 0; i< produkty.get(wybranaKategoria).size();i++){
            System.out.println((i+1) + ". " + produkty.get(wybranaKategoria).get(i));
        }

        System.out.println("Wybierz Produkt po numerze indeksu:");
        int indexProduktu = Integer.parseInt(scanner.nextLine()) - 1;
        if(indexProduktu <0 || indexProduktu >= produkty.get(wybranaKategoria).size()){
            System.out.println("Nieprawidłowy wybór produktu.");
            return;
        }
        listaZakupow.get(wybranaKategoria).remove(indexProduktu);
        System.out.println("Produkt usunięty z listy zakupów.");
    }

}