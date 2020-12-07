package Q3;

public class Aluno extends Thread {
    Mesa mesa;
    boolean estaRemediado;

    public Aluno(Mesa mesa) {
        this.mesa = mesa;
    }

    public synchronized boolean getEstaRemediado() {
        return estaRemediado;
    }

    public synchronized void bebe() {
        estaRemediado = true;
        System.out.println("Aluno " + getId() + " esta remediado");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sai() {
        synchronized (mesa) {
            mesa.levantar(this);
            System.out.println("Aluno " + getId() + " saiu");
        }
    }

    public synchronized boolean podeSair() {
        synchronized (mesa) {
            if (getEstaRemediado() && mesa.getAlunos().size() > 2) {
                return true;
            } else if (getEstaRemediado() && mesa.getAlunos().size() == 2) {
                Aluno a0 = mesa.getAlunos().get(0);
                Aluno a1 = mesa.getAlunos().get(1);
                boolean tempPodeSair = a0.getEstaRemediado() && a1.getEstaRemediado();
                if (!tempPodeSair) {
                    System.out.println("Ninguém pode ser deixado bebendo sozinho!!!");
                }
                return tempPodeSair;
            } else {
                return getEstaRemediado();
            }
        }
    }

    @Override
    public void run() {
        boolean estaSentado = false;
        do {
            estaSentado = mesa.sentar(this);
        } while (!estaSentado);

        while (!podeSair()) {
            bebe();
        }
        sai();
    }
}
