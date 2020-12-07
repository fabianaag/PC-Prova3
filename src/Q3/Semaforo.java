package Q3;

public class Semaforo {
    private boolean signal = false;

    public synchronized void adquirir() {
        this.signal = true;
        this.notify();
    }

    public synchronized void liberar() throws InterruptedException{
        while(!this.signal) wait();
        this.signal = false;
    }
}
