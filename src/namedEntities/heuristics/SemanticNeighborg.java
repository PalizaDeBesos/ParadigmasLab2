package namedEntities.heuristics;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;


public class SemanticNeighborg {
    
    static Set<String> keyWords = new HashSet<>(List.of(
        //Locaciones
        "Ciudad", "País", "Región", "Continente", "Estado", "Provincia", "Municipio", "Pueblo", "Villa", "Distrito", "Universidad",
        "Barrio", "Zona", "Territorio", "Isla", "Archipiélago", "República", "Federación", "Comunidad Autónoma", "Departamento", "Nación", "Territorio",
        "Cantón", "Parroquia", "Comuna", "Aldea","Gabinete", "Corte", "Cámara", "Asamblea", "Congreso", "Senado", "Parlamento", "Gobierno", "Ayuntamiento",
        /*Profesiones"Presidente*/ "Gobernador", "Técnico", "Licenciado", "Músico", "Vocero", "Ministro", "Doctor",
        "Ingeniero", "Profesor", "Abogado", "Juez", "Empresario", "Director", "Gerente", "Asesor", "Consultor", "Investigador", "Científico", 
        "Artista","Cabildo", "Iglesia","Escritor", "Periodista", "Actor", "Deportista", "Chef", "Arquitecto", "Contador", "Enfermero", "Paramédico", "Piloto", "Capitán",
        "General", "Almirante", "Sargento", "Soldado", "Policía", "Detective", "Guardia", "Vigilante", "Bombero", "Rescatista", "Paramédico", "Médico", "Enfermero", "Farmacéutico",
        "Psicólogo", "Psiquiatra", "Terapeuta", "Consejero", "Entrenador", "Nutricionista", "Dietista", "Fisioterapeuta", "Kinesiólogo", "Masajista", "Estilista", "Maquillador", "Diseñador",
        "Decorador", "Carpintero", "Albañil", "Electricista", "Plomero", "Mecánico", "Soldador", "Pintor", "Jardinero", "Agricultor", "Ganadero", "Pescador", "Cazador", "Carpintero", "Albañil",
        "Electricista", "Plomero", "Mecánico", "Soldador", "Pintor", "Jardinero", "Agricultor", "Ganadero", "Pescador", "Cazador", "Carpintero", "Albañil", "Electricista", "Plomero", "Mecánico",
        "Soldador", "Pintor", "Jardinero", "Agricultor", "Ganadero", "Pescador", "Cazador", "Carpintero", "Albañil", "Electricista", "Plomero", "Mecánico", "Soldador", "Pintor", "Jardinero", "Agricultor",
        "Ganadero", "Pescador", "Cazador", "Carpintero", "Albañil", "Electricista", "Plomero", "Mecánico", "Soldador", "Pintor", "Jardinero", "Agricultor", "Ganadero", "Pescador", "Cazador", "Carpintero", "Albañil",
        "Obispo","Arzobispo","Cardenal","Papa","Sacerdote","Pastor","Rabino","Imán","Monje","Monja","Fraile","Hermana","Hermano","Misionero","Evangelista","Cura","Vicario","Diácono","Diaconisa","Presbítero","Pastor","Pastora",
        "Rector","Vicario","Diácono","Diaconisa","Presbítero","Pastor","Pastora","Rector","Vicario","Diácono","Diaconisa","Presbítero","Pastor","Pastora","Rector","Vicario","Diácono","Diaconisa","Presbítero","Pastor","Pastora","Rector",
        "arzobispo"
    ));
    static Set<String> Org = new HashSet<> (List.of(
        "Naciones Unidas", "Unión Europea", "OTAN", "OMS", "OIT", "FMI", "Banco Mundial", "OEA", "Unesco", 
        "Interpol", "Cruz Roja", "OMS", "Unicef", "ACNUR", "OPEP", "G7", "G20", "BRICS",
        "Google", "Apple", "Amazon", "Microsoft", "Facebook", "Tesla", "Samsung", "Sony", "IBM", "Intel", "NVIDIA", "Coca-Cola", "Pepsi", 
        "McDonald's", "Nike", "Adidas", "Toyota", "Ford", "General Motors", "Volkswagen", "BMW", "Mercedes-Benz", "Shell", "ExxonMobil", "Chevron", 
        "BP", "Walmart", "Costco", "Target", "Alibaba", "Tencent", "Baidu", "Huawei", "Netflix", "Spotify", "Uber", "Airbnb", "SpaceX", "Boeing", 
        "Lockheed Martin", "Goldman Sachs", "JPMorgan Chase", "Bank of America", "Citibank", "Wells Fargo", "HSBC", "Santander", "BBVA", "ING", "Deutsche Bank", 
        "Siemens", "Philips", "Panasonic", "LG", "Lenovo", "Acer", "Asus"
    ));
    
    static List<String> excludedWords = List.of(
        "de", "del", "El", "el", "La", "la", "Los", "los", "Las", "las","le","Le"
    );
    
    public static List<String> extractCandidates(String text) {
        CapitalizedWordHeuristic cWH = new CapitalizedWordHeuristic();
        List<String> candidates = new ArrayList<>();
        String[] words = text.replaceAll("[\\[\\]<>//]", "").split("\\s+");
        String filteredText ; 
        for (int i = 0; i < words.length; i++) {
            if (!Org.contains(words[i])) {
                if (keyWords.contains(words[i])) {
                    if (i + 1 < words.length && !excludedWords.contains(words[i+1])) {
                        candidates.add(words[i + 1]);   
                    } else if (i + 2 < words.length && !excludedWords.contains(words[i+2])) {
                        candidates.add(words[i + 2]);
                    }
                }
            } else {
                candidates.add(words[i]);
            }
        }
        return candidates;
    }
}
