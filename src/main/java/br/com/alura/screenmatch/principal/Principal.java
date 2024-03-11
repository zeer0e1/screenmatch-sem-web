package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=ede20d0a";


    public void exibeMenu() {
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = leitura.nextLine();
        // Consultando  a série
        var json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);


        // Consultando temporada
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= dados.totalTemporadas(); i++) {
            json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" +
                    i + API_KEY);

            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        //temporadas.forEach(System.out::println);


        //temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()).collect(Collectors.toList());
//
//        System.out.println("\nTop 10 ep: ");
//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek( e -> System.out.println("Primeiro filtro(N/A)"))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek( e -> System.out.println("Ordenação " + e))
//                .limit(10)
//                .peek( e -> System.out.println("Limite " + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek( e -> System.out.println("Mapeamento " + e))
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.temporada(), d))
                ).collect(Collectors.toList());


        episodios.forEach(System.out::println);

        System.out.println("Digite o um pedaço do titulo:");
        var trechoTitutlo = leitura.nextLine();

        Optional<Episodio> epBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toLowerCase().contains(trechoTitutlo.toLowerCase()))
                .findFirst();

        if (epBuscado.isPresent()){
            System.out.println("Ep encontrado!");
            System.out.printf("Temporada: " + epBuscado.get().getTemporada());
        }else {
            System.out.println("Ep não encontrado");
        }

        System.out.println(epBuscado);
//
//        System.out.println("A partir de que ano vc deseja ver os episodios? ");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e ->e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach( e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                " Episodio: " + e.getTitulo() +
//                                " Data lançamento: " + formatador.format(e.getDataLancamento())
//                ));
    }
}
