package Q3;

public class Bebedor extends Thread {

    private Mesa mesaHandler;
    private Semaforo semaforo;
    private boolean estaRemediado = false;

    public Bebedor(Mesa mesa, Semaforo semaforo) {
        mesaHandler = mesa;
        this.semaforo = semaforo;
    }

    public synchronized void bebe() {
        try {
            System.out.println("O aluno bebedor " + getId() + ", decidiu tomar uma");
            Thread.sleep((long) (1000 * Math.random()));
            estaRemediado = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Bebedor não pode deixar alguém bebendo sozinho,
     * Então se bebedor já bebeu, e tem apenas uma companhia que ainda não bebeu.
     * Deve esperar que a sua companhia beba, ou que novas pessoas cheguem a mesa.
     */
    public synchronized boolean sai() {
        semaforo.adquirir();
        boolean podeSair = estaRemediado;
        if (estaRemediado) {
            if (mesaHandler.getBebedores().size() == 2) {
                long bebedorAtualId = getId();
                long outroBebedorId = -1;
                for (long bebedorId : mesaHandler.getBebedores().keySet()) {
                    if (bebedorId != bebedorAtualId) {
                        outroBebedorId = bebedorId;
                        break;
                    }
                }

                if (mesaHandler.getBebedores().get(outroBebedorId).getEstaRemediado()) {
                    mesaHandler.levantar(this);
                } else {
                    System.out.println(String.format("O bebedor %s não pode sair, pois o bebedor %s não pode beber sozinho", getId(), outroBebedorId));
                    bebe();
                    podeSair = false;
                }
            }
        }
        try {
            semaforo.liberar();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return podeSair;
    }

    private synchronized boolean getEstaRemediado() {
        return estaRemediado;
    }

    @Override
    public void run() {
        boolean sentou = false;
        boolean saiu = false;
        boolean bebedorTerminou = false;

        while (!bebedorTerminou) {
            synchronized (mesaHandler) {
                while (!sentou) {
                    sentou = mesaHandler.sentar(this);
                }
                if (mesaHandler.getBebedores().size() > 1) {
                    bebe();
                } else {
                    System.out.println("Bebedor " + getId() + " irá esperar alguém para beber junto");
                    try {
                        Thread.sleep((long) (1000 * Math.random()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                saiu = sai();
                if (saiu) {
                    bebedorTerminou = true;
                }
            }
        }

        System.out.println("Bebedor " + getId() + " terminou de beber");
    }
}
