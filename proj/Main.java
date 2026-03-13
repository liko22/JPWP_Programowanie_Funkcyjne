package proj;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Collectors;

public class Main {
    //Record - Specjalny modyfikator - pola obiektów są final więc idealnie do FP
    public static record Product(String name, double price) {};
    public static record Order(Map<Product,Double> products, LocalDate date) {};
    public static record Client(String name, int age, Order order) {};
    //Filtry
    public static Predicate<Client> olderThan(int minAge){
        return c -> c.age() >minAge;
    }
    public static Predicate<Client> hasProduct(String product){
        return c -> c.order().products.keySet().stream().anyMatch(p -> p.name().equals(product));
    }

    //Funkcje
    public static Function<Client,String> clientName(){
        return c -> c.name();
    }
    public static Function<List<Client>,List<Client>> getFilteredClients(Predicate<Client> filter){
        return list -> list.stream().filter(filter).toList();
    }
    public static Function<Client,Double> moneySpent(){
        return c -> c.order().products().entrySet().stream().collect(Collectors.summingDouble(p -> p.getKey().price() * p.getValue()));
    }
    public static Function<List<Client>,Map<String,Double>> moneySpentList(){
        return mapa -> mapa.stream().collect(Collectors.toMap(clientName(), moneySpent()));
    }

    public static void main(String[] args){

        //Inicjalizacja niemutowalnej listy produktów
        List<Product> products = List.of(
            new Product("Orange", 9.3),
            new Product("Apple", 7.5),
            new Product("Banana", 11.4)
        );

        //Inicjalizacja niemutowalnej listy zakupów
        List<Order> orders = List.of(
            new Order(
                Map.of(products.get(0), 2.5, products.get(1), 1.2),
                LocalDate.now().minusDays(5)),
            new Order(
                Map.of(products.get(1), 3.2),
                LocalDate.now().minusDays(20)),
            new Order(
                Map.of(products.get(2), 1.6),
                LocalDate.now().minusDays(35))                
        );

        //Inicjalizacja niemutowalnej listy klientów
        List<Client> clients = List.of(
            new Client("Adam",30, orders.get(0)),
            new Client("Kacper",50, orders.get(1)),
            new Client("Ola",20, orders.get(2))
        );

        //Test niemutowalności listy
        //products.add(new Product("test", 20.1));
        //products.get(2).price=30;
        //products.set(0, new Product("Test", 10));

        Map<String,Double> allClientsCosts = moneySpentList().apply(clients);
        List<Client> clientsOlderThan = getFilteredClients(olderThan(30)).apply(clients);
        List<Client> clientsWithApples = getFilteredClients(hasProduct("Apple")).apply(clientsOlderThan);
        Map<String,Double> clientsOlderThanCosts = moneySpentList().apply(clientsWithApples);
    }
}
