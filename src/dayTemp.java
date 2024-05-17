public class dayTemp {
    private String day;
    private String maxTemperatur;
    private String minTemperatur;
    private String prognos;

    public dayTemp(String day, String max, String min, String prognos){
        // Formaterar html blocket dag så att det endast innehåller vilken dag det är.
        int index = day.indexOf("dag");
        this.day = day.substring(index - 4,index + 3);
        this.day = this.day.replaceFirst(">", "");
        if (this.day.contains("Idag")){
            this.day = "Idag";
        }

        // Formaterar html blocket max så att det endast innehåller vilken maxtemperatur det är.
        index = max.indexOf("°");
        this.maxTemperatur = max.substring(index - 3, index + 1);
        this.maxTemperatur = this.maxTemperatur.replaceFirst(">", "");
        this.maxTemperatur = this.maxTemperatur.replaceFirst("\"", "");

        // Formaterar html blocket min så att det endast innehåller vad minsta temperaturen är.
        index = min.indexOf("°");
        this.minTemperatur = min.substring(index - 3, index + 1);
        this.minTemperatur = this.minTemperatur.replaceFirst(">", "");
        this.minTemperatur = this.minTemperatur.replaceFirst("\"", "");

        // Formaterar html blocket prognos så att det endast innehåller vad prognosen är.
        index = prognos.indexOf("aria-label");
        int index2 = prognos.indexOf("data-qa-id");
        this.prognos = prognos.substring(index, index2 -3);
        this.prognos = this.prognos.replaceFirst("aria-label=\"", "");
        this.prognos = this.prognos.replaceFirst("\"", "");
    }

    // Hur dayTemp skrivs ut i konsolen.
    @Override
    public String toString() {
        if (day != "Idag"){
            day = "På " + day.toLowerCase();
        }
        prognos = prognos.replaceFirst("Prognos", "prognosen");
        return day + " är " + prognos.toLowerCase() +
                " väder med en max temperatur av " + maxTemperatur + 'C' +
                " och en minsta temperatur av " + minTemperatur + "C.";
    }
}
