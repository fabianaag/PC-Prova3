package TESTE;

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

    public synchronized void sai() {
        mesa.levantar(this);
        System.out.println("Aluno " + getId() + " saiu");

    }

    public synchronized boolean podeSair() {
        return mesa.getAlunos().stream().allMatch(aluno -> aluno.getEstaRemediado()) || (getEstaRemediado() && mesa.getAlunos().size() > 2);
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
