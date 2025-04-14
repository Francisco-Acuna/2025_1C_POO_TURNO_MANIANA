package ar.org.centro8.curso.java.entidades.relaciones;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public final class Cuenta {

    //atributos
    private final int NUMERO_CUENTA;
    private final String MONEDA;
    private float saldo;

    //métodos
    public void depositar(float monto){
        this.saldo += monto;
    }

    public void debitar(float monto){
        if(this.saldo - monto < 0) System.out.println("Fondos insuficientes");
        else this.saldo -= monto;
    }


}
