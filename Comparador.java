import java.util.*;

public class Comparador {

    public static void main(String[] args) {
        List<Integer> paginas = Arrays.asList(0,6,5,6,3,3,5,6,0);//entrada de paginas  
        int capacidade = 3;//tamanho da memoria 

        System.out.println("Comparando algoritmos de substituição de páginas...\n");

        // Aging
        long inicio = System.nanoTime();
        App aging = new App(capacidade);
        for (int pagina : paginas) {
            aging.acessoPagina(pagina);
        }
        long fim = System.nanoTime();
        long tempoAging = fim - inicio;
        System.out.println("Aging - Page Faults: " + aging.getPageFaults());
        System.out.printf("Aging - Tempo: %.6f ms\n\n", tempoAging / 1_000_000.0);

        // Clock
        inicio = System.nanoTime();
        Clock clock = new Clock(capacidade);
        for (int pagina : paginas) {
            clock.acessarPagina(pagina);
        }
        fim = System.nanoTime();
        long tempoClock = fim - inicio;
        System.out.println("Clock - Page Faults: " + clock.getPageFaults());
        System.out.printf("Clock - Tempo: %.6f ms\n\n", tempoClock / 1_000_000.0);

        // FIFO
        inicio = System.nanoTime();
        FIFO fifo = new FIFO(capacidade);
        fifo.processarPaginas(paginas);
        fim = System.nanoTime();
        long tempoFIFO = fim - inicio;
        System.out.println("LRU - Page Faults: " + fifo.getPageFaults());
        System.out.printf("FIFO - Tempo: %.6f ms\n\n", tempoFIFO / 1_000_000.0);

        // LRU
        inicio = System.nanoTime();
        LRUCache lru = new LRUCache(capacidade);
        lru.processarPaginas(paginas);
        fim = System.nanoTime();
        long tempoLRU = fim - inicio;
        System.out.println("LRU - Page Faults: " + lru.getPageFaults());
        System.out.printf("LRU - Tempo: %.6f ms\n\n", tempoLRU / 1_000_000.0);

        // Resumo
        System.out.println("Resumo dos tempos de execução:");
        System.out.printf("Aging: %.6f ms\n", tempoAging / 1_000_000.0);
        System.out.printf("Clock: %.6f ms\n", tempoClock / 1_000_000.0);
        System.out.printf("FIFO:  %.6f ms\n", tempoFIFO / 1_000_000.0);
        System.out.printf("LRU:   %.6f ms\n", tempoLRU / 1_000_000.0);
    }
}
