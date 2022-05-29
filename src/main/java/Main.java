
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static int sunbeds;
    static Scanner input = new Scanner(System.in);
    static Map<Integer, Integer> freeSlots = new HashMap<Integer, Integer>();
    static Map<Integer, Integer> occupiedSlots = new HashMap<Integer, Integer>();


    public static void coming() {

        System.out.print("Enter the number of coming guests: ");
        int guests = input.nextInt();


        while (guests != -1) {
            int bestIndx = -1;
            for (Map.Entry<Integer, Integer> sunbed : freeSlots.entrySet()) {
                int start = sunbed.getKey();
                int end = sunbed.getValue();

                //  Wenn der Startindex der Sonnenbänke höher ist als der Endindex  (e.g. [0, 1, 2, 3, -4, -5, -6, 7, 8, 9]) (7, 3)
                if ((start > end) && (end + sunbeds - start >= guests)) {
                    if (bestIndx == -1) {
                        bestIndx = start;
                    } else {
                        if (bestIndx > freeSlots.get(bestIndx)) {
                            if ((freeSlots.get(bestIndx) + sunbeds - bestIndx) > (end + sunbeds - start)) {
                                bestIndx = start;
                            }
                        } else {
                            if ((freeSlots.get(bestIndx) - bestIndx) > (end + sunbeds - start)) {
                                bestIndx = start;
                            }
                        }
                    }
                }
                //  Wenn der Startindex kleiner ist als der Endindex
                if ((end - start) >= guests) {
                    if (bestIndx == -1) {
                        bestIndx = start;
                    } else if ((freeSlots.get(bestIndx) - bestIndx) > (end - start)) {
                        bestIndx = start;
                    }
                }
            }
            // suitable slot
            if (bestIndx != -1) {
                occupiedSlots.put(bestIndx, (bestIndx + guests) % sunbeds);
                if (freeSlots.get(bestIndx) > (bestIndx + guests) % sunbeds) {
                    int newStart = (bestIndx + guests) % sunbeds;
                    freeSlots.put(newStart, freeSlots.get(bestIndx));
                    freeSlots.remove(bestIndx);
                } else {
                    freeSlots.remove(bestIndx);
                }
            }

            System.out.print("free slots: ");
            System.out.println(freeSlots);

            System.out.print("occupied slots: ");
            System.out.println(occupiedSlots);

            System.out.print("Enter the number of coming guests: ");
            guests = input.nextInt();
        }
    }

    public static void leaving() {

        if (occupiedSlots.size() == 0) {
            System.out.println("No guests are in the pool");
            return;
        }

        System.out.print("Enter starting sunbed number of leaving guests: ");
        int leaving_guests = input.nextInt();


        while (leaving_guests != -1) {
            int startPos = leaving_guests;
            int endPos = occupiedSlots.get(leaving_guests);

            // Wenn vor und nach den Sonnenliegen der Gäste freie Plätze vorhanden sind
            if ((freeSlots.containsKey(endPos)) && (freeSlots.containsValue(startPos))) {
                for (Map.Entry<Integer, Integer> sunbed : freeSlots.entrySet()) {
                    if (sunbed.getValue() == startPos) {
                        freeSlots.replace(sunbed.getKey(), freeSlots.get(endPos));
                        freeSlots.remove(endPos);
                    }
                }
            }
            // Wenn nach den Sonnenliegen der Gäste ein leerer Plätze vorhanden sind
            else if (freeSlots.containsKey(endPos)) {
                freeSlots.put(startPos, freeSlots.get(endPos));
                freeSlots.remove(endPos);
            }
            // Wenn vor den Sonnenliegen der Gäste ein leerer Plätze vorhanden sind
            else if (freeSlots.containsValue(startPos)) {
                for (Map.Entry<Integer, Integer> temp_sunbed : freeSlots.entrySet()) {
                    if (temp_sunbed.getValue() == startPos) {
                        freeSlots.replace(temp_sunbed.getKey(), endPos);
                    }
                }
            }
            // Wenn vor oder nach den Sonnenliegen der Gäste keine freien Plätze vorhanden sind
            else {
                freeSlots.put(startPos, endPos);
            }

            occupiedSlots.remove(leaving_guests);


            if (freeSlots.size() > 1){
                if (freeSlots.containsKey(0)){
                    for (Map.Entry<Integer, Integer> sunbed : freeSlots.entrySet()){
                        if (sunbed.getValue() == sunbeds){
                            freeSlots.replace(sunbed.getKey(), freeSlots.get(0));
                            freeSlots.remove(0);
                            break;
                        }
                    }
                }
            }
            System.out.print("free slots: ");
            System.out.println(freeSlots);

            System.out.print("occupied slots: ");
            System.out.println(occupiedSlots);

            System.out.print("Enter starting sunbed number of leaving guests: ");
            leaving_guests = input.nextInt();
        }
    }

    public static void show() {
        int[] list_sunbeds = new int[sunbeds];
        for (int i = 0; i < sunbeds; i++){
            list_sunbeds[i] = i;
        }
        for(Map.Entry<Integer, Integer> slot : occupiedSlots.entrySet()){
            if (slot.getValue() < slot.getKey()){
                for(int pos = slot.getKey(); pos < sunbeds; pos++){
                    list_sunbeds[pos] *= -1;
                }
                for(int pos = 0; pos < slot.getValue(); pos++){
                    list_sunbeds[pos] *= -1;
                }
            }else{
                for (int pos = slot.getKey(); pos < slot.getValue(); pos++){
                    list_sunbeds[pos] *= -1;
                }
            }
        }
        System.out.print("free slots: ");
        System.out.println(freeSlots);

        System.out.print("occupied slots: ");
        System.out.println(occupiedSlots);

        System.out.println(Arrays.toString(list_sunbeds));

    }

    public static void main(String[] args) {

        System.out.print("Enter the number of sunbeds: ");
        sunbeds = input.nextInt();
        if (sunbeds == -1){
            return;
        }

        freeSlots.put(0, sunbeds);


        System.out.println(freeSlots);


        System.out.print("Enter the command(e.g. coming, leaving, show, exit): ");
        String command = input.next();


        while (!(command.equals("exit"))) {
            if (command.equals("coming")) {
                coming();
            }
            if (command.equals("leaving")) {
                leaving();
            }
            if (command.equals("show")) {
                show();
            }
            System.out.print("Enter the command(e.g. coming, leaving, show, exit): ");
            command = input.next();
        }
        input.close();

    }
}
