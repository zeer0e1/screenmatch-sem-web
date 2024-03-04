package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoAPI;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoAPI = new ConsumoAPI();
		ConverteDados conversor = new ConverteDados();

		// Consultando  a série
		var endereco = "https://www.omdbapi.com/?t=gilmore+girls&apikey=ede20d0a";
		var json  = consumoAPI.obterDados(endereco);
		DadosSerie dados = conversor.obterDados(json,DadosSerie.class);
		System.out.println(dados);

		// Consultando  o Episódio
		json = consumoAPI.obterDados("https://www.omdbapi.com" +
				"/?t=gilmore+girls&season=1&episode=2&&apikey=ede20d0a");
		DadosEpisodio dadosEpisodio = conversor.obterDados(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		// Consultando temporada
		List<DadosTemporada> temporadas = new ArrayList<>();

		for (int i = 1; i<= dados.totalTemporadas();i++){
			json = consumoAPI.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=+" +
					i +"&apikey=ede20d0a");

			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);



	}
}
