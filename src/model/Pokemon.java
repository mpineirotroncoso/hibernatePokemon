package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pokemon")
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "nacemento")
    private LocalDate nacemento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokedexentry")
    private Pokedex pokedexentry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adestrador")
    private Adestrador adestrador;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNacemento() {
        return nacemento;
    }

    public void setNacemento(LocalDate nacemento) {
        this.nacemento = nacemento;
    }

    public Pokedex getPokedexentry() {
        return pokedexentry;
    }

    public void setPokedexentry(Pokedex pokedexentry) {
        this.pokedexentry = pokedexentry;
    }

    public Adestrador getAdestrador() {
        return adestrador;
    }

    public void setAdestrador(Adestrador adestrador) {
        this.adestrador = adestrador;
    }

}