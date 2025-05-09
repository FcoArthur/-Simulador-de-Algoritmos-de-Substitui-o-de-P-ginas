import java.util.*;

public class FIFO {
    private final int capacidade;
    private final Set<Integer> memoria;
    private final Queue<Integer> filaFIFO;
    private int faltas;

    public FIFO(int capacidade) {
        this.capacidade = capacidade;
        this.memoria = new HashSet<>();
        this.filaFIFO = new LinkedList<>();
        this.faltas = 0;
    }

    public void processarPaginas(List<Integer> paginas) {
        for (int pagina : paginas) {
            if (!memoria.contains(pagina)) {
                faltas++;

                if (memoria.size() == capacidade) {
                    int removida = filaFIFO.poll();
                    memoria.remove(removida);
                }

                memoria.add(pagina);
                filaFIFO.offer(pagina);
            }

            //System.out.println("Memória: " + filaFIFO);
        }
    }

    public int getPageFaults() {
        return faltas;
    }

    public static void main(String[] args) {
        List<Integer> paginas = Arrays.asList(0, 1, 2, 3, 0, 1, 4, 0, 1, 2, 3, 4);
        int capacidade = 3;

        long inicio = System.nanoTime();

        FIFO fifo = new FIFO(capacidade);
        fifo.processarPaginas(paginas);

        long fim = System.nanoTime();
        long duracao = fim - inicio;

        //System.out.println("\nTotal de faltas de página: " + fifo.getFaltas());
        //System.out.println("Tempo de execução: " + duracao + " ns");
        //System.out.printf("Tempo de execução: %.6f ms\n", duracao / 1_000_000.0);
    }
}
