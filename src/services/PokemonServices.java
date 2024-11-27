package services;

import config.HibernateConfig;
import model.Adestrador;
import model.Pokedex;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class PokemonServices {

    public void crearPokemon(String nome, BigDecimal peso, String misc) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Pokedex pokedex = new Pokedex();
            pokedex.setNome(nome);
            pokedex.setPeso(peso);
            pokedex.setMisc(misc);
            session.save(pokedex);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Erro ao crea-lo pokemon: " + e.getMessage());
        }
    }


    public void actualizarPokemon(int id, String newNombre, BigDecimal newPeso, String newMisc) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Pokedex gato = session.get(Pokedex.class, id);
            if (gato != null) {
                gato.setNome(newNombre);
                gato.setPeso(newPeso);
                gato.setMisc(newMisc);
                session.update(gato);
            } else {
                System.out.println("Pokemon non encontrado para actualizar.");
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Erro ao actualiza-lo pokemon: " + e.getMessage());
        }
    }

    public void eliminarPokemons() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from Pokedex").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Erro ao eliminar os pokemons: " + e.getMessage());
        }
    }

    public Pokedex lerPokemon(Integer id) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.get(Pokedex.class, id);
        } catch (Exception e) {
            System.out.println("Erro ao ler o pokemon: " + e.getMessage());
            return null;
        }
    }

    public List<Pokedex> listarPokemon() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("from Pokedex", Pokedex.class).getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao lista-los pokemon: " + e.getMessage());
            return null;
        }
    }

    public void crearAdestrador(String nome, LocalDate nacemento) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Adestrador adestrador = new Adestrador();
            adestrador.setNome(nome);
            adestrador.setNacemento(nacemento);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Erro ao crea-lo adestradr: " + e.getMessage());
        }
    }

    public List<Adestrador> listarAdestrador() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("from Adestrador", Adestrador.class).getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao lista-los adestradores: " + e.getMessage());
            return null;
        }
    }

    public void actualizarAdestrador(int id, String nome, LocalDate nacemento) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Adestrador adestrador = session.get(Adestrador.class, id);
            if (adestrador != null) {
                adestrador.setNome(nome);
                adestrador.setNacemento(nacemento);
                session.update(adestrador);
            } else {
                System.out.println("Adestrador non encontrado para actualizar.");
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Erro ao actualiza-lo adestrador: " + e.getMessage());
        }
    }
}
