import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        String[] options = getCountys();
        String län = (String) JOptionPane.showInputDialog(null, "Välj län", "Välj län", 3,null,options,options[0]);
        String stad = JOptionPane.showInputDialog(null, "Välj en stad");
        String url = toUrl(län.toLowerCase(), stad.toLowerCase());

        // Kod som jag fick av fabian som hämtar en hel webbsidas html kod.
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .build();
        } catch (Exception e){
            System.out.println("Jag vet inte hur man kommer hit. Bra jobbat!");
            System.exit(0);
        }
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> res = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String weather = res.body();

        ArrayList<dayTemp> days = new ArrayList<dayTemp>();
        for (int i = 0; i < 7; i++){
            // Ungefär vart olika värden finns.
            int temperatur = weather.indexOf("temp-wrapper");
            int vilkenDag = weather.indexOf("long visible-medium-up");
            int dagensPrognos = weather.indexOf("icon-weather item visible-medium-up");

            // Ta ut block av html från sidan vilket innehåller värdena som programmet ska ha in.
            String day = res.body().substring(vilkenDag + 30, vilkenDag + 80);
            String max = res.body().substring(temperatur + 82, temperatur + 133);
            String min = res.body().substring(temperatur +200, temperatur + 260);
            String prognos = res.body().substring(dagensPrognos + 80, dagensPrognos + 170);

            /*System.out.println(day); // Debug code för när days.add har ett error.
            System.out.println(max);
            System.out.println(min);
            System.out.println(prognos); */
            try {
                days.add(new dayTemp(day, max, min, prognos));
            } catch (Exception e){
                System.out.println("Staden finns inte i länet");
                System.exit(0);
            }

            // Gör att programmet kan hitta nästa värden.
            weather = weather.replaceFirst("long visible-medium-up", "cool visible-medium-up");
            weather = weather.replaceFirst("temp-wrapper", "used-wrapper");
            weather = weather.replaceFirst("temp-wrapper", "used-wrapper");
            weather = weather.replaceFirst("icon-weather item visible-medium-up", "icon-weather item visible-large-up");
        }

        System.out.println("Vädret i " + stad.toLowerCase() + " är:");
        for (int i = 0; i < days.size(); i++){
            System.out.println(days.get(i).toString());
        }
    }

    // Sätter ihop län och stat till en functional url.
    public static String toUrl (String län, String stad){
        String url = "https://www.klart.se/se/";
        län = län.replace(' ', '-');
        url += län + "/väder-" + stad + "/";

        return url;

        //gör att url ser ut som den jag kopierar om jag tar en webbplats från webbläsarens sökfält.
        /*x§String å = "%C3%A5";
        String ä = "%C3%A4";
        String ö = "%C3%B6";
        län = län.replaceAll("å", å).replaceAll("ä",ä).replaceAll("ö",ö);
        stad = stad.replaceAll("å", å).replaceAll("ä",ä).replaceAll("ö",ö); */
    }

    // Ger tillbaka en lista av alla län i Sverige.
    public static String[] getCountys(){
        String[] allaLän = new String[21];

        allaLän[0] = "Norrbottens län";
        allaLän[1] = "Västerbottens län";
        allaLän[2] = "Jämtlands län";
        allaLän[3] = "Västernorrlands län";
        allaLän[4] = "Gävleborgs län";
        allaLän[5] = "Dalarnas län";
        allaLän[6] = "Värmlands län";
        allaLän[7] = "Örebro län";
        allaLän[8] = "Västmanlands län";
        allaLän[9] = "Uppsala län";
        allaLän[10] = "Stockholms län";
        allaLän[11] = "Södermanlands län";
        allaLän[12] = "Östergötlands län";
        allaLän[13] = "Västra Götalands län";
        allaLän[14] = "Hallands län";
        allaLän[15] = "Jönköpings län";
        allaLän[16] = "Kalmar län";
        allaLän[17] = "Kronobergs län";
        allaLän[18] = "Skåne län";
        allaLän[19] = "Blekinge län";
        allaLän[20] = "Gotlands län";

        return allaLän;
    }
}