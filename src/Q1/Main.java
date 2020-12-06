package Q1;

public class Main {

    public static void main(String[] args) {
        Carro car = new Carro(4);
        car.start();

        for (int i = 0; i < 100; i++) {
            Passageiro p = new Passageiro(car);
            p.start();
        }
    }

}
