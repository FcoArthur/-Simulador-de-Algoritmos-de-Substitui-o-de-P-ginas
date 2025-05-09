import java.util.*;

public class LRUCache {

    private final int capacidade;
    private final List<Integer> memoria;
    private final Queue<Integer> ordemLRU;
    private int faltas;

    public LRUCache(int capacidade) {
        this.capacidade = capacidade;
        this.memoria = new ArrayList<>(Collections.nCopies(capacidade, null));
        this.ordemLRU = new LinkedList<>();
        this.faltas = 0;
    }

    public void processarPaginas(List<Integer> paginas) {
        for (int pagina : paginas) {
            if (memoria.contains(pagina)) {
                ordemLRU.remove(pagina);
                ordemLRU.offer(pagina);
            } else {
                faltas++;

                if (ordemLRU.size() == capacidade) {
                    int antiga = ordemLRU.poll();
                    int index = memoria.indexOf(antiga);
                    memoria.set(index, pagina);
                } else {
                    int index = memoria.indexOf(null);
                    memoria.set(index, pagina);
                }

                ordemLRU.offer(pagina);
            }

            //System.out.println("Memória: " + memoria);
        }

        //System.out.println("\nTotal de faltas de página: " + faltas);
    }
    public int getPageFaults() {
        return faltas;
    }
    public static void main(String[] args) {
        List<Integer> paginas = Arrays.asList(0, 1, 2, 3, 0, 1, 4, 0, 1, 2, 3, 4);
        int capacidade = 3;

        long inicio = System.nanoTime(); // Início do tempo

        LRUCache cache = new LRUCache(capacidade);
        cache.processarPaginas(paginas);

        long fim = System.nanoTime(); // Fim do tempo

        long duracao = fim - inicio; // Tempo em nanossegundos
        //System.out.println("Tempo de execução: " + duracao + " ns");
        //System.out.printf("Tempo de execução: %.6f ms\n", duracao / 1_000_000.0);
    }
}