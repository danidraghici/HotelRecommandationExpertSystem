import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ExpertSystem {
    List<Hotel> hotels = new ArrayList<>();
    // Define the interface for a rule
    interface Rule extends Predicate<Hotel> {
    }
    public ExpertSystem() {
        loadHotels();
    }
    private void loadHotels() {
        try (BufferedReader br = new BufferedReader(new FileReader("./knowledge_base.txt"))) {
            String line;
            boolean isFirstLine = true; // Adăugați o variabilă pentru a verifica dacă este prima linie
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Săriți peste prima linie
                    continue;
                }
                String[] data = line.split(", "); // Presupunând valori separate de virgule
                if (data.length == 11) { // Verificați dacă linia conține numărul corect de elemente
                    try {
                        hotels.add(new Hotel(data));
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Hotel> findHotelsBasedOnCriteria(Map<String, String> criteria) {
        List<Rule> rules = new ArrayList<>();
        // Example rules
        if (criteria.containsKey("city")) {
            String city = criteria.get("city");
            rules.add(h -> h.city.equals(city));
        }
        if (criteria.containsKey("maxPrice")) {
            int maxPrice = Integer.parseInt(criteria.get("maxPrice"));
            rules.add(h -> h.pricePerNight <= maxPrice);
        }
        if (criteria.containsKey("roomType")) {
            String roomType = criteria.get("roomType");
            rules.add(h -> h.roomType.equals(roomType));
        }
        // Apply rules sequentially (Forward Chaining)
        List<Hotel> currentList = new ArrayList<>(hotels);
        for (Rule rule : rules) {
            currentList = currentList.stream().filter(rule).collect(Collectors.toList());
        }
        return currentList;
    }
    public static void main(String[] args) {
        ExpertSystem system = new ExpertSystem();
        Map<String, String> criteria = new HashMap<>();
        criteria.put("city", "Amsterdam");
        criteria.put("maxPrice", "140");
        criteria.put("roomType", "Double");
        List<Hotel> results = system.findHotelsBasedOnCriteria(criteria);
        if (results.isEmpty()) {
            System.out.println("No matching hotels found.");
        } else {
            results.forEach(System.out::println);
        }
    }
}