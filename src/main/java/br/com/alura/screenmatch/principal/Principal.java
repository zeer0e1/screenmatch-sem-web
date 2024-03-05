package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.service.ConsumoAPI;

import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private  final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY ="&apikey=ede20d0a" ;
    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = leitura.nextLine();
        // Consultando  a série
		var json  = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

    }
}
