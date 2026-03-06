package proj;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {
    //Record - Specjalny modyfikator - pola obiektów są final więc idealnie do FP
    public record Product(String name, double price) {};
    public record Order(Map<Product,Double> products, LocalDate date) {};
    public record Client(String name, int age, Order order) {};

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
                Map.of(products.get(0), 2.5),
                LocalDate.now().minusDays(5)),
            new Order(
                Map.of(products.get(1), 3.2),
                LocalDate.now().minusDays(20)),
            new Order(
                Map.of(products.get(2), 1.6),
                LocalDate.now().minusDays(35))                
        );

        //Inicjalizacja niemutowalnej listy klientów
        List<Client> Clients = List.of(
            new Client("Adam",30, orders.get(2)),
            new Client("Kacper",50, orders.get(1)),
            new Client("Ola",20, orders.get(0))
        );
    }
}
