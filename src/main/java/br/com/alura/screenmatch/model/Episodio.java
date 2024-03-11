package br.com.alura.screenmatch.model;


import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private  String titulo;
    private  Integer numeroEpisodio;
    private double avalicao;
    private LocalDate dataLancamento;

    public Episodio(Integer temporada, DadosEpisodio d) {
        this.temporada = temporada;
        this.titulo = d.titulo();
        this.numeroEpisodio = d.numero();
        try {
            this.avalicao = Double.valueOf(d.avaliacao());
        } catch (NumberFormatException ex){
            this.avalicao = 0;
        }

        try {
          this.dataLancamento = LocalDate.parse(d.dataLancamento());
        }catch (DateTimeParseException ex){
            this.dataLancamento = null;
        }

    }


    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public void setNumeroEpisodio(Integer numeroEpisodio) {
        this.numeroEpisodio = numeroEpisodio;
    }

    public double getAvalicao() {
        return avalicao;
    }

    public void setAvalicao(double avalicao) {
        this.avalicao = avalicao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return
                "temporada= " + temporada +
                ", titulo= " + titulo + '\'' +
                ", numeroEpisodio= " + numeroEpisodio +
                ", avalicao= " + avalicao +
                ", dataLancamento= " + dataLancamento;
    }
}
