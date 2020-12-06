package Q1;

public class Passageiro extends Thread {
    private Carro carHandle;
    public boolean estaNoCarro;

    public Passageiro(Carro car) {
        carHandle = car;
        estaNoCarro = false;
    }

    @Override
    public void run() {
        while (true) {
            if (!estaNoCarro && carHandle.estaCarregando) {
                carHandle.embarcar(this);
                estaNoCarro = true;
            } else if (estaNoCarro && carHandle.estaDescarregando) {
                carHandle.desembarcar(this);
                estaNoCarro = false;
            }

            // Passageiro espera para próxima viagem
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
