import config.HibernateConfig;
import model.Pokedex;
import org.hibernate.Session;
import services.PokemonServices;

import java.math.BigDecimal;
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

        //EXEMPLOS CON ID que non se deben facer
        /*
        gatosServices.actualizarGato(1L, "Paturras", "Siames", 3, "Beixe", new BigDecimal("4.7"), new Date(), true, "Carlos");
        gatosServices.actualizarGato(2L, "Lua", "Persa", 4, "Branca", new BigDecimal("5.3"), new Date(), true, "Maria");

        gatosServices.lerGato(1L);
        gatosServices.lerGato(2L);
        */



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