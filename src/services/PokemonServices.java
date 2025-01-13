package services;

import config.HibernateConfig;
import model.Adestrador;
import model.Pokedex;
import model.Pokemon;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.xml.stream.*;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
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

    public List<Pokedex> listarPokedex() {
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

    public void eliminarPokedex() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from Pokemon").executeUpdate();
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

    public void addPokemonToAdestrador(String nome, LocalDate nacemento, int pokedexEntry, int adestrador) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Pokemon pokemon = new Pokemon();
            pokemon.setNome(nome);
            pokemon.setNacemento(nacemento);
            pokemon.setPokedexentry(session.get(Pokedex.class, pokedexEntry));
            pokemon.setAdestrador(session.get(Adestrador.class, adestrador));
            session.save(pokemon);
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Erro ao crea-lo pokemon: " + e.getMessage());
        }
    }

    public List<Pokemon> listarPokemon() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("from Pokemon ", Pokemon.class).getResultList();
        } catch (Exception e) {
            System.out.println("Erro ao lista-los adestradores: " + e.getMessage());
            return null;
        }
    }

    public void serializePokedex() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            List<Pokedex> pokedexes = session.createQuery("from Pokedex", Pokedex.class).getResultList();
            if (!pokedexes.isEmpty()) {
                try (FileOutputStream fileOutputStream = new FileOutputStream("pokedex.dat");
                     ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                    objectOutputStream.writeObject(pokedexes);
                } catch (IOException e) {
                    System.out.println("Erro ao escribir a serialización: " + e.getMessage());
                }
            } else {
                System.out.println("Non hai pokedexes para serializar.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao serializar: " + e.getMessage());
        }
    }

    public void adestradorToXML() {
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

    public void importXMLToAdestrador(String path) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from Adestrador").executeUpdate();
            transaction.commit();
            transaction = session.beginTransaction();
            session.createQuery("delete from Pokemon").executeUpdate();
            transaction.commit();
            transaction = session.beginTransaction();
            session.createQuery("delete from Pokedex").executeUpdate();
            transaction.commit();
            transaction = session.beginTransaction();
            // Importar adestradores
            try {
                XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
                XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(path));
                Adestrador adestrador = null;
                while (xmlStreamReader.hasNext()) {
                    int event = xmlStreamReader.next();
                    if (event == XMLStreamConstants.START_ELEMENT) {
                        switch (xmlStreamReader.getLocalName()) {
                            case "Adestrador":
                                adestrador = new Adestrador();
                                break;
                            case "id":
                                adestrador.setId(Integer.parseInt(xmlStreamReader.getElementText()));
                                break;
                            case "nome":
                                adestrador.setNome(xmlStreamReader.getElementText());
                                break;
                            case "nacemento":
                                adestrador.setNacemento(LocalDate.parse(xmlStreamReader.getElementText()));
                                session.save(adestrador);
                                break;
                        }
                    }
                }
            } catch (XMLStreamException | FileNotFoundException e) {
                System.out.println("Erro ao importar adestradores: " + e.getMessage());
            }
            // Importar pokedexes
            try {
                XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
                XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream("pokedex.xml"));
                Pokedex pokedex = null;
                while (xmlStreamReader.hasNext()) {
                    int event = xmlStreamReader.next();
                    if (event == XMLStreamConstants.START_ELEMENT) {
                        switch (xmlStreamReader.getLocalName()) {
                            case "Pokedex":
                                pokedex = new Pokedex();
                                break;
                            case "id":
                                pokedex.setId(Integer.parseInt(xmlStreamReader.getElementText()));
                                break;
                            case "nome":
                                pokedex.setNome(xmlStreamReader.getElementText());
                                break;
                            case "peso":
                                pokedex.setPeso(new BigDecimal(xmlStreamReader.getElementText()));
                                break;
                            case "misc":
                                pokedex.setMisc(xmlStreamReader.getElementText());
                                session.save(pokedex);
                                break;
                        }
                    }
                }
            } catch (XMLStreamException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void importSerializedPokedex() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete from Pokedex").executeUpdate();
            transaction.commit();
            transaction = session.beginTransaction();
            try (FileInputStream fileInputStream = new FileInputStream("pokedex.dat");
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                List<Pokedex> pokedexes = (List<Pokedex>) objectInputStream.readObject();
                for (Pokedex pokedex : pokedexes) {
                    session.save(pokedex);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Erro ao importar a serialización: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Erro ao importar a serialización: " + e.getMessage());
        }
    }
}
