package services;

import config.HibernateConfig;
import model.Adestrador;
import model.Pokedex;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;
import java.io.IOException;
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
            Pokedex pokemon = session.get(Pokedex.class, id);
            if (pokemon != null) {
                pokemon.setNome(newNombre);
                pokemon.setPeso(newPeso);
                pokemon.setMisc(newMisc);
                session.update(pokemon);
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
            session.save(adestrador);
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

    public void eliminarAdestradores() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from Adestrador").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Erro ao eliminar os adestradores: " + e.getMessage());
        }
    }

    public void pokedexToXml() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            List<Pokedex> pokedexes = session.createQuery("from Pokedex", Pokedex.class).getResultList();
            if (!pokedexes.isEmpty()) {
                try (FileWriter fileWriter = new FileWriter("pokedex.xml")) {
                    XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
                    XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(fileWriter);

                    xmlStreamWriter.writeStartDocument();
                    xmlStreamWriter.writeStartElement("Pokedexes");

                    for (Pokedex pokedex : pokedexes) {
                        xmlStreamWriter.writeStartElement("Pokedex");

                        xmlStreamWriter.writeStartElement("id");
                        xmlStreamWriter.writeCharacters(String.valueOf(pokedex.getId()));
                        xmlStreamWriter.writeEndElement();

                        xmlStreamWriter.writeStartElement("nome");
                        xmlStreamWriter.writeCharacters(pokedex.getNome());
                        xmlStreamWriter.writeEndElement();

                        xmlStreamWriter.writeStartElement("peso");
                        xmlStreamWriter.writeCharacters(pokedex.getPeso().toString());
                        xmlStreamWriter.writeEndElement();

                        xmlStreamWriter.writeStartElement("misc");
                        xmlStreamWriter.writeCharacters(pokedex.getMisc());
                        xmlStreamWriter.writeEndElement();

                        xmlStreamWriter.writeEndElement(); // End Pokedex
                    }

                    xmlStreamWriter.writeEndElement(); // End Pokedexes
                    xmlStreamWriter.writeEndDocument();

                    xmlStreamWriter.flush();
                    xmlStreamWriter.close();
                } catch (IOException | XMLStreamException e) {
                    System.out.println("Erro ao escribir a XML: " + e.getMessage());
                }
            } else {
                System.out.println("Non hai pokedexes para convertir a XML.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao convertir a XML: " + e.getMessage());
        }
    }

    public void adestradorToXml() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            List<Adestrador> adestradores = session.createQuery("from Adestrador", Adestrador.class).getResultList();
            if (!adestradores.isEmpty()) {
                try (FileWriter fileWriter = new FileWriter("adestradores.xml")) {
                    XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
                    XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(fileWriter);

                    xmlStreamWriter.writeStartDocument();
                    xmlStreamWriter.writeStartElement("Adestradores");

                    for (Adestrador adestrador : adestradores) {
                        xmlStreamWriter.writeStartElement("Adestrador");

                        xmlStreamWriter.writeStartElement("id");
                        xmlStreamWriter.writeCharacters(String.valueOf(adestrador.getId()));
                        xmlStreamWriter.writeEndElement();

                        xmlStreamWriter.writeStartElement("nome");
                        xmlStreamWriter.writeCharacters(adestrador.getNome());
                        xmlStreamWriter.writeEndElement();

                        xmlStreamWriter.writeStartElement("nacemento");
                        xmlStreamWriter.writeCharacters(adestrador.getNacemento().toString());
                        xmlStreamWriter.writeEndElement();

                        xmlStreamWriter.writeEndElement(); // End Adestrador
                    }

                    xmlStreamWriter.writeEndElement(); // End Adestradores
                    xmlStreamWriter.writeEndDocument();

                    xmlStreamWriter.flush();
                    xmlStreamWriter.close();
                } catch (IOException | XMLStreamException e) {
                    System.out.println("Erro ao escribir a XML: " + e.getMessage());
                }
            } else {
                System.out.println("Non hai adestradores para convertir a XML.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao convertir a XML: " + e.getMessage());
        }
    }
}
