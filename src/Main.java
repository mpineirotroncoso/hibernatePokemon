import config.HibernateConfig;
import model.Adestrador;
import model.Pokedex;
import org.hibernate.Session;
import services.PokemonServices;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        printDateTime();
        PostgresVersion();

        PokemonServices pokemonServices = new PokemonServices();

        pokemonServices.crearPokemon("Pikachu", new BigDecimal("6.0"), "Electrico");
        pokemonServices.crearPokemon("Charmander", new BigDecimal("8.0"), "Fuego");
        pokemonServices.crearPokemon("Bulbasaur", new BigDecimal("7.0"), "Planta");

        System.out.println("Listado de todos os pokemon:");
        List<Pokedex> pokemonList = pokemonServices.listarPokemon();
        for (Pokedex pokemon : pokemonList) {
            System.out.println(pokemon);
        }

        pokemonServices.actualizarPokemon(1, "Raichu", new BigDecimal("7.0"), "Electrico");
        pokemonServices.actualizarPokemon(2, "Charmeleon", new BigDecimal("9.0"), "Fuego");

        pokemonList = pokemonServices.listarPokemon();
        for (Pokedex pokemon : pokemonList) {
            System.out.println(pokemon);
        }

        pokemonServices.eliminarPokemons();

        pokemonServices.crearPokemon("Pikachu", new BigDecimal("6.0"), "Electrico");
        pokemonServices.crearPokemon("Charmander", new BigDecimal("8.0"), "Fuego");
        pokemonServices.crearPokemon("Bulbasaur", new BigDecimal("7.0"), "Planta");
        pokemonServices.crearPokemon("Squirtle", new BigDecimal("9.0"), "Agua");
        pokemonServices.crearPokemon("Jigglypuff", new BigDecimal("5.0"), "Normal");
        pokemonServices.crearPokemon("Meowth", new BigDecimal("4.0"), "Normal");
        pokemonServices.crearPokemon("Psyduck", new BigDecimal("6.0"), "Agua");
        pokemonServices.crearPokemon("Machop", new BigDecimal("8.0"), "Lucha");
        pokemonServices.crearPokemon("Geodude", new BigDecimal("7.0"), "Roca");
        pokemonServices.crearPokemon("Gastly", new BigDecimal("5.0"), "Fantasma");

        pokemonServices.crearAdestrador("Ash Ketchum", LocalDate.ofEpochDay(1996));
        pokemonServices.crearAdestrador("Misty", LocalDate.ofEpochDay(1996));

        pokemonServices.listarPokemon();
        pokemonServices.listarAdestrador();
        for (Adestrador adestrador : pokemonServices.listarAdestrador()) {
            System.out.println(adestrador);
        }

        pokemonServices.actualizarAdestrador(1, "Ash Ketchum", LocalDate.ofEpochDay(1998));
        pokemonServices.actualizarAdestrador(2, "Misty", LocalDate.ofEpochDay(1998));


    }
    public  static  void PostgresVersion()
    {
        try(Session session = HibernateConfig.getSessionFactory().openSession())
        {
            Object obj = session.createNativeQuery("SELECT VERSION()").getSingleResult();
            System.out.println(obj);
        }
    }

    public  static  void printDateTime()
    {
        try(Session session = HibernateConfig.getSessionFactory().openSession())
        {
            Object obj = session.createNativeQuery("SELECT NOW()").getSingleResult();
            System.out.println(obj);
        }
    }
}